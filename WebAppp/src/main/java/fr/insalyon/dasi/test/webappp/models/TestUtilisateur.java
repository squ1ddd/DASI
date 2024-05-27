/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.webappp.models;

/**
 *
 * @author qsaillard
 */
public class TestUtilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String mail;

    public TestUtilisateur(int id, String nom, String prenom, String mail) {
        this.nom = nom;
        this.id = id;
        this.prenom = prenom;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }
    
    
}
