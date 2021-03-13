package com.example.mycalisthenicarea.Model;

public class FoodItem {

    private String codeBar;
    private String nom;
    private String marque;
    private String poids;
    //private String listIngredients;
    private String imageURL;

    public FoodItem(String codeBar, String nom, String marque, String poids, String imageURL) {
        this.nom = nom;
        this.marque = marque;
        this.poids = poids;
        this.imageURL=imageURL;
        this.codeBar=codeBar;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCodeBar() {
        return codeBar;
    }

    public void setCodeBar(String codeBar) {
        this.codeBar = codeBar;
    }
}
