/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.webappp.controllers;

/**
 *
 * @author qsaillard
 */
import fr.insalyon.dasi.test.webappp.models.Action;
import fr.insalyon.dasi.test.webappp.models.TestUtilisateur;
import javax.servlet.http.HttpServletRequest;

public class AuthentifierUtilisateurAction extends Action {
    @Override
    public void execute(HttpServletRequest request) {
        System.out.println("AuthentifierUtilisateurAction executé.");
        String mail = "ada.lovelace@insa-lyon.fr";
        TestUtilisateur utilisateur = new TestUtilisateur(1024, "Lovelace", "Ada", mail);
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if(login.equals(mail)){
            request.setAttribute("utilisateur", utilisateur);
        }
        else {
            request.setAttribute("utilisateur", null);
        }
        System.out.println("AuthentifierUtilisateurAction executed: " + utilisateur.getMail());
    }
}
