/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.webappp.vue;

import fr.insalyon.dasi.test.webappp.models.Serialisation;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.test.webappp.models.TestUtilisateur;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author qsaillard
 */

public class ProfilUtilisateurSerialisation extends Serialisation {
    @Override
    public void serialize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonResponse = new JsonObject();
        if (request.getAttribute("utilisateur") != null) {
            TestUtilisateur utilisateur = (TestUtilisateur) request.getAttribute("utilisateur");
            jsonResponse.addProperty("connexion", true);

            JsonObject jsonUtilisateur = new JsonObject();
            jsonUtilisateur.addProperty("id", utilisateur.getId());
            jsonUtilisateur.addProperty("nom", utilisateur.getNom());
            jsonUtilisateur.addProperty("prenom", utilisateur.getPrenom());
            jsonUtilisateur.addProperty("mail", utilisateur.getMail());

            jsonResponse.add("utilisateur", jsonUtilisateur);
        }
        else{
            jsonResponse.addProperty("connexion", false);
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }
}