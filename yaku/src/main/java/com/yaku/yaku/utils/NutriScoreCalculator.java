package com.yaku.yaku.utils;

import java.util.Map;

public interface NutriScoreCalculator {
    int calculateNutriScore(String productData);

    // Ajouter la méthode pour récupérer la classe et la couleur de l'aliment
    Map<String, String> getFoodClassAndColor(int score);
}
