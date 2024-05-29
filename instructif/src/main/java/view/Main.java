/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;

import business.model.ComprehenssionLevel;
import business.model.Session;
import business.model.Student;
import business.service.Service;
import business.service.Saisie;
import dao.JpaUtil;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mlaurent1
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);

        JpaUtil.creerFabriquePersistance();
        Service service = new Service();

        // Creer les données de test
        service.initSampleData();

        Student student = new Student(
                "Agent",
                "N°6",
                "rokugo@kisaragi.corp",
                "Al1ceIsBest1e",
                "123456789",
                6,
                new Date(2000, 1, 1)
        );

        if (service.registerStudent(student, "0691664J")) {
            System.out.println("Student registered");
        } else {
            System.out.println("Student not registered");
            return;
        }

        Student matthieu = service.authenticate("rokugo@kisaragi.corp", "Al1ceIsBest1e");
        Session mysession = service.requestSession(matthieu, "ma desc", "mon subject");

        System.out.print("Student authed: ");
        System.out.println(matthieu.toString());

        System.out.print("Student's establishment: ");
        System.out.println(matthieu.getEstablishment().toString());
        List<Session> establishmentSessions = service.getSessions(matthieu.getEstablishment(), false);
        System.out.println("Other required statics for the establishment: ");
        System.out.println("Session count: " + String.valueOf(establishmentSessions.size()));
        System.out.println("Average session duration: " + String.valueOf(service.getAverageSessionDuration(matthieu.getEstablishment())) + "sec");

        if (mysession == null) {
            System.out.println("Session not created");
            return;
        } else {
            System.out.println("Session created");
            System.out.println(mysession.toString());
        }
        
        Integer order = Saisie.lireInteger("Enter 1 for presenter first or 2 for student first: ", Arrays.asList(1, 2));

        if (order == 1) {
            mysession = service.presenterJoinSession(mysession.getPresenter());
            if (mysession != null) {
                System.out.println("Presenter joined");
            } else {
                System.out.println("Presenter not joined");
                return;
            }
            System.out.println("Current status of session: " + mysession.getStatus().toString());

            Saisie.lireChaine("Press Enter to simulate the student joining...");
            mysession = service.studentJoinSession(matthieu);
            if (mysession != null) {
                System.out.println("Student joined");
            } else {
                System.out.println("Student not joined");
                return;
            }
            System.out.println("Current status of session: " + mysession.getStatus().toString());
        } else if (order == 2) {
            mysession = service.studentJoinSession(matthieu);
            if (mysession != null) {
                System.out.println("Student joined");
            } else {
                System.out.println("Student not joined");
                return;
            }
            System.out.println("Current status of session: " + mysession.getStatus().toString());

            Saisie.lireChaine("Press Enter to simulate the presenter joining...");
            mysession = service.presenterJoinSession(mysession.getPresenter());
            if (mysession != null) {
                System.out.println("Presenter joined");
            } else {
                System.out.println("Presenter not joined");
                return;
            }
            System.out.println("Current status of session: " + mysession.getStatus().toString());
        }

        // Check the the service returns nulls firsts
        // System.out.println(service.getSessions(matthieu, false).get(0).toString());
        Saisie.lireChaine("Press Enter to simulate the end of the session...");
        service.endSession(mysession, "Reviewed how to deal with nullpo", ComprehenssionLevel.IM_STILL_LOST);

        System.out.print("Final state: ");
        System.out.println(service.getSessions(matthieu, false).get(0).toString());

        JpaUtil.fermerFabriquePersistance();
    }
}
