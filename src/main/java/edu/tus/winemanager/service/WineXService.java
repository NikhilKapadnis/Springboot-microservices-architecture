package edu.tus.winemanager.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class WineXService {
    private final List<String> wines = new ArrayList<>();

    public List<String> getAllWines() {
        return wines;
    }

    public String addWine(String wine) {  // Make sure this method exists!
        wines.add(wine);
        return wine;
    }

    public boolean removeWine(String wine) {
        return wines.remove(wine);
    }
}
