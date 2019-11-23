package com.example.exchangetoys.DTOs.ToyServiceData;

import java.io.Serializable;
import java.util.ArrayList;

public class Toy implements Serializable {
    //na pewno trzeba jakoś wiele obrazów zapipsywać i przkazywac??? spytaj
    private int toyID;
    private String emailAddress;//key to toy's owner
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

    public Toy(int toyID, String emailAddress, String name, String description, int ageCategory, ArrayList<String> specialFeature, double averageResult, ArrayList<String> tags, boolean ifDidactic, boolean ifVintage, String toysFactoryName, int qualityOfMade) {
        this.toyID = toyID;
        this.emailAddress = emailAddress;
        this.name = name;
        this.description = description;
        this.ageCategory = ageCategory;
        this.specialFeature = specialFeature;
        this.averageResult = averageResult;
        this.tags = tags;
        this.ifDidactic = ifDidactic;
        this.ifVintage = ifVintage;
        this.toysFactoryName = toysFactoryName;
        this.qualityOfMade = qualityOfMade;
    }

    public int getToyID() {
        return toyID;
    }

    public void setToyID(int toyID) {
        this.toyID = toyID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Toy() {
    }

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
