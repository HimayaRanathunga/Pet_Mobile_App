package com.example.myproject;

public class PetDataClass {
    private String petdataName;
    private String petdataType;
    private String petdataBreed;
    private String petdataHeight;
    private String petdataWeigth;
    private String petdataage;
    private String petdataaddress;

    private String petdatacName;
    private String petdataImage;
    private String petdatanote;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPetdatanote() {
        return petdatanote;
    }

    public PetDataClass(String petdataName, String petdataType, String petdataBreed, String petdataHeight, String petdataWeigth, String petdataage, String petdataaddress, String petdatacNote, String petdatacName, String petdataimage) {
        this.petdataName = petdataName;
        this.petdataType = petdataType;
        this.petdataBreed = petdataBreed;
        this.petdataHeight = petdataHeight;
        this.petdataWeigth = petdataWeigth;
        this.petdataage = petdataage;
        this.petdataaddress = petdataaddress;
        this.petdatacName = petdatacName;
        this.petdataImage=petdataimage;
        this.petdatanote=petdatacNote;
    }

    public PetDataClass(){

    }

    public String getPetdataImage() {
        return petdataImage;
    }

    public String getPetdataName() {
        return petdataName;
    }

    public String getPetdataType() {
        return petdataType;
    }

    public String getPetdataBreed() {
        return petdataBreed;
    }

    public String getPetdataHeight() {
        return petdataHeight;
    }

    public String getPetdataWeigth() {
        return petdataWeigth;
    }

    public String getPetdataage() {
        return petdataage;
    }

    public String getPetdataaddress() {
        return petdataaddress;
    }

    public String getPetdatacName() {
        return petdatacName;
    }
}
