/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.service;

import java.util.Date;
import java.util.List;

import business.model.ComprehenssionLevel;
import business.model.Person;
import business.model.Presenter;
import business.model.PresenterTypeStudent;
import business.model.PresenterTypeTeacher;
import business.model.Session;
import business.model.SessionStatus;
import business.model.Student;
import dao.JpaUtil;
import dao.PresenterDAO;
import dao.SessionDAO;
import dao.StudentDAO;
import business.model.Establishment;
import com.google.maps.model.LatLng;
import dao.EstablishmentDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mlaurent1
 */
public class Service {

    // This method doesn't create it's own persistance context, as it's
    // meant to be used in other services. You NEED to open one up yourself.
    private Establishment getOrFetchEstablishment(String establishmentCode) {
        EducNetApi api = new EducNetApi();
        EstablishmentDAO establishementDAO = new EstablishmentDAO();

        Establishment establishment = establishementDAO.findByCode(establishmentCode);
        if (establishment == null) {

            List<String> result = null;
            List<String> resultCollege;
            List<String> resultLycee;
            try {
                // Tries for both colleges and lycées
                resultCollege = api.getInformationCollege(establishmentCode);
                resultLycee = api.getInformationLycee(establishmentCode);
                if (resultCollege != null) {
                    result = resultCollege;
                } else if (resultLycee != null) {
                    result = resultLycee;
                }
            } catch (IOException ex) {
                result = null;
            }
            if (result != null) {
                String uai = result.get(0);
                String nom = result.get(1);
                String secteur = result.get(2);
                String codeCommune = result.get(3);
                String nomCommune = result.get(4);
                String codeDepartement = result.get(5);
                String nomDepartement = result.get(6);
                String academie = result.get(7);
                String ips = result.get(8);
                System.out.println("Fetched establishement " + uai + ": " + nom + " à " + nomCommune + ", " + nomDepartement);
                LatLng coordsEtablissement = GeoNetApi.getLatLng(uai + " " + nom + ", " + nomCommune + ", " + nomDepartement);

                establishment = new Establishment(
                        establishmentCode,
                        nom,
                        ips,
                        nomCommune,
                        codeDepartement,
                        coordsEtablissement.lat,
                        coordsEtablissement.lng
                );

                establishementDAO.create(establishment);
            } else {
                System.out.println("No establishment found for code " + establishmentCode);
            }
        }
        return establishment;
    }

    public Boolean registerStudent(Student student, String establishmentCode) {
        StudentDAO studentDAO = new StudentDAO();
        Message message = new Message();
        Boolean registrationSuccess = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Establishment establishment = this.getOrFetchEstablishment(establishmentCode);
            if (establishment == null) {
                throw new Exception("Couldn't get establishment");
            }
            student.setEstablishment(establishment);

            studentDAO.create(student);

            JpaUtil.validerTransaction(); // essayer de valider la transaction
            // envoyer le mail de confirmation
            message.envoyerMail(
                    "contact.instruct.if",
                    student.getEmail(),
                    "Bienvenue sur le réseau INSTRUCT'IF!",
                    "Bonjour " + student.getFirstName() + ", nous te confirmons ton inscription sur le réseau INSTRUCT'IF.\n"
                    + "Si tu as besoin d'un soutien pour tes leçons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant."
            );
            System.out.println("Student registered: " + student); // ça a marché ☺
            registrationSuccess = true;
        } catch (Exception ex) { // ça n'a pas marché
            ex.printStackTrace();
            JpaUtil.annulerTransaction(); // ne pas oublier d'annuler la transaction !
            message.envoyerMail(
                    "contact.instruct.if",
                    student.getEmail(),
                    "Echec de l'inscription sur le réseau INSTRUCT'IF!",
                    "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué...\n"
                    + "Merci de recommencer ultérieurement."
            );

        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();
        }

        return registrationSuccess;
    }

    public Student authenticate(String mail, String password) {
        StudentDAO studentDAO = new StudentDAO();
        Student authenticatedStudent = null;
        try {
            JpaUtil.creerContextePersistance();
            Student s = studentDAO.findByEmail(mail);
            if (s.getPassword().equals(password)) {
                authenticatedStudent = s;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();
        }
        return authenticatedStudent;
    }

    private String getSkillLevelPrettyName(Integer skillLevel) {
        switch (skillLevel) {
            case 1:
                return "1ère";
            case 2:
                return "2nd";
            default:
                return skillLevel.toString() + "ème";
        }
    }

    public Session requestSession(
            Student student,
            String description,
            String subject
    ) {
        PresenterDAO presenterDAO = new PresenterDAO();
        SessionDAO sessionDAO = new SessionDAO();
        Message message = new Message();
        Session session = null;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            // On trouve un presenter de dispo
            Presenter presenter = presenterDAO.findAvailable(student.getSkillLevel());
            if (presenter == null) {
                throw new Exception("No presenter available");
            }

            // On créer une session
            session = new Session(
                    description,
                    //new Date(),
                    null,
                    null,
                    subject,
                    SessionStatus.WAITING_BOTH,
                    null,
                    null,
                    student,
                    presenter
            );
            sessionDAO.create(session);

            message.envoyerNotification(
                    presenter.getPhoneNumber(),
                    "Bonjour " + presenter.getFirstName() + ", merci de prendre en charge la demande de soutien en « " + session.getSubject()
                    + // TODO mettre la date en format heure
                    " » demandée à " + new Date().toString() + " par " + student.getFirstName() + " en classe de "
                    + getSkillLevelPrettyName(student.getSkillLevel()) + "."
            );

            // Enfin, on valide le tout
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return session;
    }

    public Session studentJoinSession(Student student) {
        SessionDAO sessionDAO = new SessionDAO();
        Session session = null;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            session = sessionDAO.findWaitingByStudent(student);
            if (session == null) {
                throw new Exception("No session available");
            }
            SessionStatus oldStatus = session.getStatus();
            if (oldStatus == SessionStatus.WAITING_BOTH) {
                session.setStatus(SessionStatus.WAITING_PRESENTER);
            } else {
                session.setStatus(SessionStatus.STARTED);
                session.setStartDateTime(new Date());
            }

            sessionDAO.update(session);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return session;
    }

    public Session presenterJoinSession(Presenter presenter) {
        SessionDAO sessionDAO = new SessionDAO();
        Session session = null;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            session = sessionDAO.findWaitingByPresenter(presenter);
            if (session == null) {
                throw new Exception("No session available");
            }
            SessionStatus oldStatus = session.getStatus();
            System.out.println("oldStatus: " + oldStatus);
            if (oldStatus == SessionStatus.WAITING_BOTH) {
                session.setStatus(SessionStatus.WAITING_STUDENT);
            } else {
                session.setStatus(SessionStatus.STARTED);
                session.setStartDateTime(new Date());
            }

            sessionDAO.update(session);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return session;
    }

    public void endSession(Session session, String summary, ComprehenssionLevel comprehensionLevel) {
        SessionDAO sessionDAO = new SessionDAO();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            session.setStatus(SessionStatus.ENDED);
            session.setEndDateTime(new Date());
            session.setSummary(summary);
            session.setComprehenssionlevel(comprehensionLevel);

            sessionDAO.update(session);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    // Garanteed to be ordered by most recent end dates first, nulls first
    // Overloaded to support both presenter and student, as well as establishment
    public List<Session> getSessions(Presenter presenter, Boolean finishedOnly) {
        SessionDAO sessionDAO = new SessionDAO();
        List<Session> sessions = null;
        try {
            JpaUtil.creerContextePersistance();
            sessions = sessionDAO.findAllForPresenter(presenter, finishedOnly);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sessions;
    }

    public List<Session> getSessions(Student student, Boolean finishedOnly) {
        SessionDAO sessionDAO = new SessionDAO();
        List<Session> sessions = null;
        try {
            JpaUtil.creerContextePersistance();
            sessions = sessionDAO.findAllForStudent(student, finishedOnly);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sessions;
    }

    public List<Session> getSessions(Establishment establishment, Boolean finishedOnly) {
        SessionDAO sessionDAO = new SessionDAO();
        List<Session> sessions = null;
        try {
            JpaUtil.creerContextePersistance();
            sessions = sessionDAO.findAllForEstablishment(establishment, finishedOnly);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sessions;
    }

    // Returns 0 if the establishment has no sessions.
    // The duration is in seconds
    public long getAverageSessionDuration(Establishment establishment) {
        SessionDAO sessionDAO = new SessionDAO();
        List<Session> sessions = null;
        try {
            JpaUtil.creerContextePersistance();
            sessions = sessionDAO.findAllForEstablishment(establishment, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        // TODO: Might be doable in the JSQL Query, but it gave me wrong values
        // so i just did it software side.
        long averageSeconds = 0;
        if (sessions != null && !sessions.isEmpty()) {
            long totalSeconds = 0;
            for (Session session : sessions) {
                long durationMillis = session.getEndDateTime().getTime() - session.getStartDateTime().getTime();
                long durationSeconds = durationMillis / 1000; // Convert milliseconds to seconds
                totalSeconds += durationSeconds;
            }
            averageSeconds = sessions.isEmpty() ? 0 : totalSeconds / sessions.size();
        }
        return averageSeconds;
    }
    
    // This returns common values for the subject field
    // This can be used by the view to suggest values for the subject field
    // for example with autocomplete or a dropdown
    // Do note that subject should still be allowed to take any value if the
    // student wants one when making his request.
    public String[] getCommonSubjects() {
        return new String[] {
            "History", 
            "Geography", 
            "Mathematics", 
            "English", 
            "French", 
            "Spanish",
            "Economics",
            "Computer Science"
        };
    }
    
    // Creates sample data
    public void initSampleData() {
        PresenterDAO presenterDAO = new PresenterDAO();
        SessionDAO sessionDAO = new SessionDAO();
        StudentDAO studentDAO = new StudentDAO();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            // Create students
            Student student1 = new Student(
                    "Alice",
                    "Kisaragi",
                    "alice@kirisagi.com",
                    "Al1ceIsBest1e",
                    "123456789",
                    1,
                    new Date(2000, 1, 1)
            );

            Student student2 = new Student("John",
                    "Titor",
                    "john.titor2036@beta.worldline",
                    "asuzuha",
                    "0606060606",
                    6,
                    new Date(2000, 1, 1)
            );

            Student student3 = new Student("Okabe",
                    "Rintarou",
                    "houhouin.kyouma@sciencelab.com",
                    "steinsgate",
                    "0101010101",
                    1,
                    new Date(2000, 1, 1)
            );

            studentDAO.create(student1);
            studentDAO.create(student2);
            studentDAO.create(student3);

            PresenterTypeTeacher presenter1 = new PresenterTypeTeacher(
                    "Mathieu",
                    "Maranzana",
                    "mathieu.maranzana@insa-lyon.fr",
                    "LeCompiloSePlieEn4",
                    "01 02 03 04 05",
                    6,
                    1,
                    "Ecole d'ingénieur");
            PresenterTypeStudent presenter2 = new PresenterTypeStudent(
                    "Kiril",
                    "Makarov",
                    "kiril.marakov@insa-lyon.fr",
                    "ViveLaTSI",
                    "06 07 08 09 10",
                    3,
                    1,
                    "INSA Lyon",
                    "Informatique Python");
            presenterDAO.createTypeTeacher(presenter1);
            presenterDAO.createTypeStudent(presenter2);

            Session session1 = new Session(
                    "Description de la session 1",
                    new Date(2021, 12, 31, 22, 00),
                    new Date(2021, 12, 31, 23, 59),
                    "Le compilo",
                    SessionStatus.ENDED,
                    "L'élève a bien compris que le compilo se plie en 4",
                    ComprehenssionLevel.IM_GETTING_BETTER,
                    student1,
                    presenter1);

            Session session2 = new Session(
                    "Description de la session 2",
                    new Date(2022, 12, 31, 22, 00),
                    new Date(2022, 12, 31, 23, 59),
                    "L'optimizeur",
                    SessionStatus.ENDED,
                    "On a vu que l'optimizeur est un outil très puissant pour optimiser le code",
                    ComprehenssionLevel.I_UNDERSTOOD_EVERYTHING,
                    student2,
                    presenter1);

            sessionDAO.create(session1);
            sessionDAO.create(session2);

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

}
