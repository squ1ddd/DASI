/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.webappp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author qsaillard
 */
public abstract class Serialisation {
    
    private Gson gson;

    public Serialisation() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void serialize(Object object, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (object instanceof TestUtilisateur) {
            serializeTestUtilisateur((TestUtilisateur) object, response);
        } else {
            serializeUnknown(object, response);
        }
    }

    private void serializeTestUtilisateur(TestUtilisateur utilisateur, HttpServletResponse response) throws IOException {
        String json = gson.toJson(utilisateur);
        response.getWriter().write(json);
    }

    private void serializeUnknown(Object object, HttpServletResponse response) throws IOException {
        String json = gson.toJson(object);
        response.getWriter().write(json);
    }
}
