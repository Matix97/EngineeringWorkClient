package com.example.exchangetoys.DTOs.ToyServiceData;

import java.io.Serializable;
import java.util.ArrayList;

public class ExtendedToy implements Serializable {
    //na pewno trzeba jakoś wiele obrazów zapipsywać i przkazywac??? spytaj
    private String name;
    private String description;
    private int ageCategory;//mapowanie 0-3= 1,4-7=2,8-12=3,13-15=4, 15-100=5
    private ArrayList<String> specialFeature;
    private double averageResult;
    private ArrayList<String> tags;//list of tags witch could categorize toys
    private boolean ifDidactic;//true if yes
    private boolean ifVintage;//true if yes
    private String toysFactoryName;
    private int qualityOfMade;//1-10

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }

    public ArrayList<String> getSpecialFeature() {
        return specialFeature;
    }

    public void setSpecialFeature(ArrayList<String> specialFeature) {
        this.specialFeature = specialFeature;
    }

    public double getAverageResult() {
        return averageResult;
    }

    public void setAverageResult(double averageResult) {
        this.averageResult = averageResult;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean isIfDidactic() {
        return ifDidactic;
    }

    public void setIfDidactic(boolean ifDidactic) {
        this.ifDidactic = ifDidactic;
    }

    public boolean isIfVintage() {
        return ifVintage;
    }

    public void setIfVintage(boolean ifVintage) {
        this.ifVintage = ifVintage;
    }

    public String getToysFactoryName() {
        return toysFactoryName;
    }

    public void setToysFactoryName(String toysFactoryName) {
        this.toysFactoryName = toysFactoryName;
    }

    public int getQualityOfMade() {
        return qualityOfMade;
    }

    public void setQualityOfMade(int qualityOfMade) {
        this.qualityOfMade = qualityOfMade;
    }
}
