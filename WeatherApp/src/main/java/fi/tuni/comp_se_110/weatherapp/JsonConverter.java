/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.comp_se_110.weatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Aksel
 */
public class JsonConverter {

    private static Gson gson = new GsonBuilder().create();

    public static final <T> T getFromJSON(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static final <T> String toJSON(T clazz) {
        return gson.toJson(clazz);
    }
}