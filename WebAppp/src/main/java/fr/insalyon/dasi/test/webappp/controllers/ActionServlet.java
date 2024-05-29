/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.webappp.models;

import business.model.Presenter;
import business.model.Student;
import fr.insalyon.dasi.test.webappp.controllers.AuthentifierUtilisateurAction;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author qsaillard
 */
public abstract class Action {
    public abstract Object execute(HttpServletRequest request, String login, String password);
    
    public void authentification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AuthentifierUtilisateurAction authService = new AuthentifierUtilisateurAction();
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        Object user = authService.execute(request, login, password);
        if (user != null) {
            if (user instanceof Student) {
                // Logique spécifique pour les employés
                request.setAttribute("user", (Student) user);
            } else if (user instanceof Presenter) {
                // Logique spécifique pour les intervenants
                request.setAttribute("user", (Presenter) user);
            }
        } else {
            // Gérer l'authentification échouée
            request.setAttribute("error", "Invalid login or password");
        }
    }
    
    
}
