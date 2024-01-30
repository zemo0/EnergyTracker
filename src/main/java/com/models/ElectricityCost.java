package com.models;

public record ElectricityCost() {
    public static final double DAILY_RATE = 0.1588;
    public static final double NIGHTLY_RATE = 0.0828;
    public static ElectricityCost defaultTariff() {
        return new ElectricityCost();
    }
    public double dailyRate() {
        return DAILY_RATE;
    }
    public double nightlyRate() {
        return NIGHTLY_RATE;
    }
}
