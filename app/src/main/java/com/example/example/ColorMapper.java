package com.example.example;

import android.content.Context;

import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

public class ColorMapper {
    private final Map<String, Integer> colorMap;

    public ColorMapper(Context context) {
        colorMap = new HashMap<>();
        colorMap.put("Red", ContextCompat.getColor(context, R.color.assignment_red));
        colorMap.put("Blue", ContextCompat.getColor(context, R.color.assignment_blue));
        colorMap.put("Purple", ContextCompat.getColor(context, R.color.assignment_purple));
        colorMap.put("Green", ContextCompat.getColor(context, R.color.assignment_green));
        colorMap.put("Pink", ContextCompat.getColor(context, R.color.assignment_pink));
        colorMap.put("Brown", ContextCompat.getColor(context, R.color.assignment_brown));
    }

    public int getColorResourceId(String color) {
        return colorMap.getOrDefault(color, R.color.assignment_red); // Default color
    }
}
