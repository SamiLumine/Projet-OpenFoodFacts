package com.yaku.yaku.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenFoodFactsServiceImpl implements OpenFoodFactsService {

    private static final String API_URL = "https://fr.openfoodfacts.org/api/v0/produit/";

    private final OkHttpClient httpClient;

    public OpenFoodFactsServiceImpl() {
        this.httpClient = new OkHttpClient();
    }

    @Override
    public String fetchProductData(String barcode) {
        System.out.println("Received barcode: " + barcode);
        String url = API_URL + barcode + ".json";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
