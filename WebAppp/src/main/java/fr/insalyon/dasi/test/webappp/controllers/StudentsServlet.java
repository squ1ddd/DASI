/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.webappp.controllers;


import com.webappp.business.model.Student;
import com.webappp.business.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import dao.StudentDAO;

@WebServlet("/students")
public class StudentsServlet extends HttpServlet {
    
    private StudentDAO studentsD;

    @Override
    public void init() throws ServletException {
        studentsD = new StudentDAO(); // Initialize your service here
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = studentsD.findAll(); // Fetch all students
        Gson gson = new Gson();
        String json = gson.toJson(students);
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}