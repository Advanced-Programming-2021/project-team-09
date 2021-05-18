package model.card;

import java.util.ArrayList;

public class FeatureWrapper {
    public ArrayList<CardFeatures> features = new ArrayList<>();

    public void addFeature(CardFeatures newFeature) {
        if (!doesHaveFeature(newFeature)) features.add(newFeature);
    }

    public void removeFeature(CardFeatures removingFeature) {
        if (doesHaveFeature(removingFeature)) features.remove(removingFeature);
    }

    public boolean doesHaveFeature(CardFeatures checkingFeature) {
        for (CardFeatures feature : features) {
            if (checkingFeature.equals(feature)) return true;
        }
        return false;
    }
}
