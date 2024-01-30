package com.models;

import java.util.HashMap;
import java.util.Map;

public class Bill {
    private static Map<Months, Appliance> troskovi = new HashMap<>();

    public static void dodajTrošak(Months month, Appliance appliance){
        troskovi.put(month, appliance);
    }
}
