package com.yaku.yaku.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yaku.yaku.model.CartItem;
import com.yaku.yaku.model.CartItemId;
import com.yaku.yaku.model.Product;
import com.yaku.yaku.service.CartItemService;
import com.yaku.yaku.service.CartService;
import com.yaku.yaku.service.ProductService;
import com.yaku.yaku.utils.ProductWithQuantity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yaku.yaku.service.OpenFoodFactsService;
import com.yaku.yaku.utils.NutriScoreCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final OpenFoodFactsService openFoodFactsService;
    private final NutriScoreCalculator nutriScoreCalculator;
    private final CartService cartService;
    private final ProductService productService;

    private final CartItemService cartItemService;
    private Product currentProduct;

    @Autowired
    public MainController(OpenFoodFactsService openFoodFactsService, NutriScoreCalculator nutriScoreCalculator, CartService cartService, ProductService productService, CartItemService cartItemService) {
        this.openFoodFactsService = openFoodFactsService;
        this.nutriScoreCalculator = nutriScoreCalculator;
        this.cartService = cartService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Affiche index.html
    }

    @GetMapping("/main")
    public String showMainPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        System.out.println("EMAIL : " + email + "\n");
        if (email == null) {
            return "redirect:/"; // Redirige vers index si l'utilisateur n'est pas identifié
        }

        model.addAttribute("userEmail", email);

        // Affichage du panier
        List<Product> cartProducts = cartService.getProductsInCart(email);
        List<ProductWithQuantity> cartProductsWithQuantity = new ArrayList<>();

        for (Product product : cartProducts) {
            Integer quantity = cartItemService.findQuantityByEmailAndBarcode(email, product.getBarcode())
                    .orElse(0); // Valeur par défaut si la quantité est absente
            cartProductsWithQuantity.add(new ProductWithQuantity(product, quantity));
        }

        model.addAttribute("cartProductsWithQuantity", cartProductsWithQuantity);
        session.setAttribute("cartProductsWithQuantity", cartProductsWithQuantity);

        return "main"; // Affiche main.html
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam int quantity, HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/"; // Redirige vers index si l'email n'est plus en session
        }

        if (currentProduct == null) {
            model.addAttribute("error", "Aucun produit sélectionné !");
            return "redirect:/main"; // Retourne à main.html avec un message d'erreur
        }

        // Ajouter au panier
        cartService.addProductToCart(email, currentProduct.getBarcode());
        CartItem cartItem = new CartItem(new CartItemId(email, currentProduct.getBarcode()), quantity);
        cartItemService.saveCartItem(cartItem);

        return "redirect:/main"; // Redirige vers main après l'ajout
    }

    @PostMapping("/main")
    public String getProductData(@RequestParam String barcode, HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/"; // Redirige vers index si l'email n'est plus en session
        }
        List<ProductWithQuantity> cartProductsWithQuantity = (List<ProductWithQuantity>) session.getAttribute("cartProductsWithQuantity");
        model.addAttribute("cartProductsWithQuantity", cartProductsWithQuantity);

        session.setAttribute("userEmail", email);
        model.addAttribute("userEmail", email);

        System.out.println("Barcode reçu: " + barcode);
        String productData = openFoodFactsService.fetchProductData(barcode);

        if (productData != null) {
            System.out.println("NON - NULL");

            JsonObject jsonObject = JsonParser.parseString(productData).getAsJsonObject();
            JsonObject product = jsonObject.getAsJsonObject("product");

            // Extraction des valeurs nutritives
            double energy = product.has("nutriments") && product.getAsJsonObject("nutriments").has("energy_100g")
                    ? product.getAsJsonObject("nutriments").get("energy_100g").getAsDouble() : 0;
            double sugars = product.has("nutriments") && product.getAsJsonObject("nutriments").has("sugars_100g")
                    ? product.getAsJsonObject("nutriments").get("sugars_100g").getAsDouble() : 0;
            double fibers = product.has("nutriments") && product.getAsJsonObject("nutriments").has("fiber_100g")
                    ? product.getAsJsonObject("nutriments").get("fiber_100g").getAsDouble() : 0;
            double proteins = product.has("nutriments") && product.getAsJsonObject("nutriments").has("proteins_100g")
                    ? product.getAsJsonObject("nutriments").get("proteins_100g").getAsDouble() : 0;
            double salt = product.has("nutriments") && product.getAsJsonObject("nutriments").has("salt_100g")
                    ? product.getAsJsonObject("nutriments").get("salt_100g").getAsDouble() : 0;
            double saturatedFat = product.has("nutriments") && product.getAsJsonObject("nutriments").has("saturated-fat_100g")
                    ? product.getAsJsonObject("nutriments").get("saturated-fat_100g").getAsDouble() : 0;
            String imageUrl = product.has("image_url") ? product.get("image_url").getAsString() : "";

            model.addAttribute("energy", energy);
            model.addAttribute("sugars", sugars);
            model.addAttribute("fibers", fibers);
            model.addAttribute("proteins", proteins);
            model.addAttribute("salt", salt);
            model.addAttribute("saturatedFat", saturatedFat);
            model.addAttribute("imageUrl", imageUrl);

            // Création d'un nouvel objet Product
            currentProduct = new Product();
            currentProduct.setBarcode(barcode);
            currentProduct.setName(product.has("product_name") ? product.get("product_name").getAsString() : "Inconnu");


            // Calcul du Nutri-Score
            int nutriScore = nutriScoreCalculator.calculateNutriScore(productData);
            Map<String, String> foodData = nutriScoreCalculator.getFoodClassAndColor(nutriScore);
            currentProduct.setNutritionScore(nutriScore);
            model.addAttribute("foodClass", foodData.get("class"));
            model.addAttribute("foodColor", foodData.get("color"));
            model.addAttribute("nutriScore", nutriScore);
            model.addAttribute("foodClassAndColor", foodData);

            productService.addProduct(currentProduct);
        } else {
            model.addAttribute("error", "Impossible de récupérer les informations du produit.");
        }

        return "main"; // Retourne main.html au lieu de index.html
    }


    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam String barcode, HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");

        // Appeler des services pour supprimer l'élément du panier
        cartItemService.deleteByEmailAndBarcode(email, barcode);

        cartService.removeProductFromCart(email, barcode);

        // Réactualiser le panier après suppression
        List<ProductWithQuantity> cartProductsWithQuantity = (List<ProductWithQuantity>) session.getAttribute("cartProductsWithQuantity");
        // Vérifier si la liste n'est non nulle et supprimer l'élément correspondant au barcode
        if (cartProductsWithQuantity != null) {
            cartProductsWithQuantity.removeIf(item -> item.getProduct().getBarcode().equals(barcode));
        }
        session.setAttribute("cartProductsWithQuantity", cartProductsWithQuantity);
        model.addAttribute("cartProductsWithQuantity", cartProductsWithQuantity);

        return "redirect:/main"; // Rediriger vers la page du panier après la suppression
    }

}
