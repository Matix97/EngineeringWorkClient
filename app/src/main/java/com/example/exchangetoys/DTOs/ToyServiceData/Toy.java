package com.example.exchangetoys.DTOs.ToyServiceData;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Toy implements Serializable {
    //na pewno trzeba jakoś wiele obrazów zapipsywać i przkazywac??? spytaj
    private int toyID;
    //dodaj id właścicilea
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

}
