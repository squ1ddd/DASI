package fr.insalyon.dasi.test.webappp.controllers;

import dao.SessionDAO;
import fr.insalyon.dasi.test.webappp.vue.ProfilUtilisateurSerialisation;
import fr.insalyon.dasi.test.webappp.vue.SessionListSerialisation;
import business.model.ComprehenssionLevel;
import business.model.Presenter;
import business.model.Session;
import business.model.Student;
import business.service.Service;
import com.google.gson.Gson;
import dao.PresenterDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    private Service service;
    private PresenterDAO presDAO;
    private Gson gson;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String todo = request.getParameter("todo");
        if ("connecter".equals(todo)) {
            System.out.println("[TEST] Appel de l’ActionServlet");

            AuthentifierUtilisateurAction action = new AuthentifierUtilisateurAction();
            action.authentification(request, response);

            ProfilUtilisateurSerialisation serialization = new ProfilUtilisateurSerialisation();
            serialization.serialize(request, response);
        } else if ("getSessions".equals(todo)) {
            System.out.println("[TEST] Appel de l’ActionServlet pour récupérer les séances");

            //SessionDAO sessionDAO = new SessionDAO();
            //List<Session> sessions = sessionDAO.findAllSessions();
            
            //request.setAttribute("sessions", sessions);
            SessionListSerialisation serialization = new SessionListSerialisation();
            serialization.serialize(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'todo' parameter.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "registerStudent":
                registerStudent(request, response);
                break;
            case "authenticate":
                authenticate(request, response);
                break;
            case "requestSession":
                requestSession(request, response);
                break;
            case "studentJoinSession":
                studentJoinSession(request, response);
                break;
            case "presenterJoinSession":
                //presenterJoinSession(request, response);
                break;
            case "endSession":
                //endSession(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid action");
        }
    }

    private void registerStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        int skillLevel = Integer.parseInt(request.getParameter("skillLevel"));
        String establishmentCode = request.getParameter("establishmentCode");

        Student student = new Student(firstName, lastName, email, password, phoneNumber, skillLevel, new java.util.Date());

        Boolean success = service.registerStudent(student, establishmentCode);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(success));
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Student student = service.authenticate(email, password);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(student));
    }

    private void requestSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String description = request.getParameter("description");
        String subject = request.getParameter("subject");

        Student student = service.authenticate(email, request.getParameter("password"));  // Assuming authentication is needed here
        if (student == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed");
            return;
        }

        Session session = service.requestSession(student, description, subject);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(session));
    }

    private void studentJoinSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");

        Student student = service.authenticate(email, request.getParameter("password"));  // Assuming authentication is needed here
        if (student == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed");
            return;
        }

        Session session = service.studentJoinSession(student);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(session));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "getCommonSubjects":
                getCommonSubjects(response);
                break;
            case "getSessions":
                getSessions(request, response);
                break;
            case "getAverageSessionDuration":
                //getAverageSessionDuration(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid action");
        }
    }

    private void getCommonSubjects(HttpServletResponse response) throws IOException {
        String[] subjects = service.getCommonSubjects();

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(subjects));
    }

    private void getSessions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userType = request.getParameter("userType");
        Boolean finishedOnly = Boolean.parseBoolean(request.getParameter("finishedOnly"));
        List<Session> sessions = null;

        if ("presenter".equals(userType)) {
            Presenter presenter = presDAO.findAvailable(1);  // Assuming authentication is needed here
            if (presenter == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed");
                return;
            }
            sessions = service.getSessions(presenter, finishedOnly);
        } else if ("student".equals(userType)) {
            String studentEmail = request.getParameter("email");
            Student student = service.authenticate(studentEmail, request.getParameter("password"));  // Assuming authentication is needed here
            if (student == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed");
                return;
            }
            sessions = service.getSessions(student, finishedOnly);
        } else if ("establishment".equals(userType)) {
           /* String establishmentCode = request.getParameter("establishmentCode");
            Establishment establishment = service.getOrFetchEstablishment(establishmentCode);
            sessions = service.getSessions(establishment, finishedOnly);*/
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid user type");
            return;
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(sessions));
    }


    @Override
    public String getServletInfo() {
        return "Main servlet controller";
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        service = new Service();
        gson = new Gson();
        presDAO = new PresenterDAO();
        JpaUtil.creerFabriquePersistance();
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy();
    }
}
