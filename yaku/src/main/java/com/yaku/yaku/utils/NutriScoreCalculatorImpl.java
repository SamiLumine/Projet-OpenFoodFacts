package com.yaku.yaku.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yaku.yaku.model.NutritionScore;
import com.yaku.yaku.model.Rule;
import com.yaku.yaku.repository.NutritionScoreRepository;
import com.yaku.yaku.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NutriScoreCalculatorImpl implements NutriScoreCalculator {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private NutritionScoreRepository nutritionScoreRepository;

    @Override
    public int calculateNutriScore(String productData) {
        JsonObject jsonObject = JsonParser.parseString(productData).getAsJsonObject();

        if (!jsonObject.has("product")) {
            System.out.println("Error: No 'product' key in JSON data.");
            return 0;
        }

        JsonObject product = jsonObject.getAsJsonObject("product");

        if (!product.has("nutriments")) {
            System.out.println("Error: No 'nutriments' key in product data.");
            return 0;
        }

        JsonObject nutriments = product.getAsJsonObject("nutriments");

        // Retrieve values safely, using default values if missing
        double energy = nutriments.has("energy_100g") ? nutriments.get("energy_100g").getAsDouble() : 0;
        double sugars = nutriments.has("sugars_100g") ? nutriments.get("sugars_100g").getAsDouble() : 0;
        double fibers = nutriments.has("fiber_100g") ? nutriments.get("fiber_100g").getAsDouble() : 0;
        double proteins = nutriments.has("proteins_100g") ? nutriments.get("proteins_100g").getAsDouble() : 0;
        double salt = nutriments.has("salt_100g") ? nutriments.get("salt_100g").getAsDouble() : 0;
        double fat = nutriments.has("saturated-fat_100g") ? nutriments.get("saturated-fat_100g").getAsDouble() : 0;

        // Debugging output
        System.out.println("Nutri-Score Calculation:");
        System.out.println("Energy: " + energy);
        System.out.println("Sugars: " + sugars);
        System.out.println("Fibers: " + fibers);
        System.out.println("Proteins: " + proteins);
        System.out.println("Salt: " + salt);
        System.out.println("Saturated Fat: " + fat);

        // Calculate Nutri-Score
        int scoreNegatif = calculateNegativeComponent(energy, fat, sugars, salt);
        int scorePositif = calculatePositiveComponent(fibers, proteins);

        return scoreNegatif - scorePositif;
    }


    private int calculateNegativeComponent(double energy, double saturatedFat, double sugars, double salt) {
        int e_score = 0, fat_score = 0, sugars_score = 0, salt_score = 0;
        salt *= 1000;  // Conversion de g à mg pour le sel

        // Récupérer la règle pour l'énergie
        List<Rule> energyRules = ruleRepository.findByNameOrderByMinBoundAsc("energy_100g");
        for (Rule rule : energyRules) {
            if (energy > rule.getMinBound()) {
                e_score = rule.getPoints();  // On affecte le score de la règle
            }
        }

        // Récupérer la règle pour les graisses saturées
        List<Rule> fatRules = ruleRepository.findByNameOrderByMinBoundAsc("saturated-fat_100g");
        for (Rule rule : fatRules) {
            if (saturatedFat > rule.getMinBound()) {
                fat_score = rule.getPoints();  // On affecte le score de la règle
            }
        }

        // Récupérer la règle pour les sucres
        List<Rule> sugarRules = ruleRepository.findByNameOrderByMinBoundAsc("sugars_100g");
        for (Rule rule : sugarRules) {
            if (sugars > rule.getMinBound()) {
                sugars_score = rule.getPoints();  // On affecte le score de la règle
            }
        }

        // Récupérer la règle pour le sel
        List<Rule> saltRules = ruleRepository.findByNameOrderByMinBoundAsc("salt_100g");
        for (Rule rule : saltRules) {
            if (salt > rule.getMinBound()) {
                System.out.println("boucle for salt : " + rule.getMinBound());
                salt_score = rule.getPoints();  // On affecte le score de la règle
            }
        }

        // Retourner la somme des scores
        return e_score + fat_score + sugars_score + salt_score;
    }

    // Calcul de la composante P (positive)
    private int calculatePositiveComponent(double fibers, double proteins) {
        int fiber_score = 0, prot_score = 0;

        // Récupérer et appliquer les règles pour les fibres
        List<Rule> fiberRules = ruleRepository.findByNameOrderByMinBoundAsc("fiber_100g");
        for (Rule rule : fiberRules) {
            if (fibers > rule.getMinBound()) {
                fiber_score = rule.getPoints();
            }
        }

        // Récupérer et appliquer les règles pour les protéines
        List<Rule> proteinRules = ruleRepository.findByNameOrderByMinBoundAsc("proteins_100g");
        for (Rule rule : proteinRules) {
            if (proteins > rule.getMinBound()) {
                prot_score = rule.getPoints();
            }
        }

        return fiber_score + prot_score;
    }

    public Map<String, String> getFoodClassAndColor(int score) {
        List<NutritionScore> scores = nutritionScoreRepository.findAll();
        for (NutritionScore nutritionScore : scores) {
            if (score >= nutritionScore.getLowerBound() && score <= nutritionScore.getUpperBound()) {
                Map<String, String> result = new HashMap<>();
                result.put("class", nutritionScore.getClasse());
                result.put("color", nutritionScore.getColor());
                return result;
            }
        }
        Map<String, String> defaultResult = new HashMap<>();
        defaultResult.put("class", "No classification");
        defaultResult.put("color", "black");  // Couleur par défaut
        return defaultResult;
    }

}
