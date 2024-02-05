package com.example.example;

import android.content.Context;

import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

public class ColorMapper {
    private final Map<String, Integer> colorMap;

    /**
     * Constructor for the ColorMapper class.
     * @param context The context of the application.
     */
    public ColorMapper(Context context) {
        colorMap = new HashMap<>();
        colorMap.put("Red", ContextCompat.getColor(context, R.color.assignment_red));
        colorMap.put("Blue", ContextCompat.getColor(context, R.color.assignment_blue));
        colorMap.put("Purple", ContextCompat.getColor(context, R.color.assignment_purple));
        colorMap.put("Green", ContextCompat.getColor(context, R.color.assignment_green));
        colorMap.put("Pink", ContextCompat.getColor(context, R.color.assignment_pink));
        colorMap.put("Brown", ContextCompat.getColor(context, R.color.assignment_brown));
    }

    /**
     * @param color The color to get the resource ID for.
     * @return The resource ID for the color.
     */
    public int getColorResourceId(String color) {
        return colorMap.getOrDefault(color, R.color.assignment_red); // Default color
    }
}
