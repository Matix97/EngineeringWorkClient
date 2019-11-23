package com.example.exchangetoys.DTOs.UserServiceData;

import com.example.exchangetoys.DTOs.ToyServiceData.Rental;
import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;

import java.util.ArrayList;

public class Adult {
    private String name;
    private String surname;
    private String password;
    private String phoneNumber;
    private String emailAddress;
    //address                   //na start musimy wybrać gdzie mieszkamy
    private double latitude;
    private double longitude;

    private ArrayList<Child> childList;
    private ArrayList<Integer> yourAdvert;//integer to keyOfToy
    private ArrayList<Integer> yourRentedToys;
    private ArrayList<SuggestedToy> childSuggestions;


    public Adult(String name, String surname, String password, ArrayList<Child> childList, ArrayList<Integer> yourAdvert, ArrayList<Integer> yourRentedToys, ArrayList<SuggestedToy> childSuggestions, String phoneNumber, String emailAddress, double latitude, double longitude, ArrayList<Rental> historyRents) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.childList = childList;
        this.yourAdvert = yourAdvert;
        this.yourRentedToys = yourRentedToys;
        this.childSuggestions = childSuggestions;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.historyRents = historyRents;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private ArrayList<Rental> historyRents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<Child> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<Child> childList) {
        this.childList = childList;
    }

    public ArrayList<Integer> getYourAdvert() {
        return yourAdvert;
    }

    public void setYourAdvert(ArrayList<Integer> yourAdvert) {
        this.yourAdvert = yourAdvert;
    }

    public ArrayList<Integer> getYourRentedToys() {
        return yourRentedToys;
    }

    public void setYourRentedToys(ArrayList<Integer> yourRentedToys) {
        this.yourRentedToys = yourRentedToys;
    }

    public ArrayList<SuggestedToy> getChildSuggestions() {
        return childSuggestions;
    }

    public void setChildSuggestions(ArrayList<SuggestedToy> childSuggestions) {
        this.childSuggestions = childSuggestions;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Rental> getHistoryRents() {
        return historyRents;
    }

    public void setHistoryRents(ArrayList<Rental> historyRents) {
        this.historyRents = historyRents;
    }
}
