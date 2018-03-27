package com.ynov.aro.messagemanager;

/**
 * Created by alexa on 26/03/2018.
 */

public class Entree {
    private String id;
    private String nom;
    private String espece;
    private String sexe;
    private String description;

    public Entree(String id, String nom, String espece, String sexe, String description){
        this.nom = nom;
        this.espece = espece;
        this.sexe = sexe;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}