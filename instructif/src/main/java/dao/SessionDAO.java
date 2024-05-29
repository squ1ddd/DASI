/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import business.model.Establishment;
import java.util.List;

import javax.persistence.TypedQuery;

import business.model.Person;
import business.model.Presenter;
import business.model.Session;
import business.model.Student;

/**
 *
 * @author mlaurent1
 */
public class SessionDAO {

    public void create(Session session) {
        JpaUtil.obtenirContextePersistance().persist(session);
    }

    public void update(Session session) {
        JpaUtil.obtenirContextePersistance().merge(session);
    }

    public Session findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Session.class, id);
    }

    // Garanteed to be ordered by most recent end dates first, nulls first
    public List<Session> findAllForStudent(Student student, Boolean finishedOnly) {
        String queryString = "SELECT s FROM Session s "
                + "WHERE s.student = :student ";
        if (finishedOnly) {
            queryString += "AND s.endDateTime != null ";
        }
        queryString += "ORDER BY s.endDateTime DESC";

        TypedQuery<Session> query = JpaUtil.obtenirContextePersistance()
                .createQuery(
                        queryString,
                        Session.class
                );
        query.setParameter("student", student);
        return query.getResultList();
    }

    // Garanteed to be ordered by most recent end dates first, nulls first
    public List<Session> findAllForPresenter(Presenter presenter, Boolean finishedOnly) {
        String queryString = "SELECT s FROM Session s "
                + "WHERE s.presenter = :presenter ";
        if (finishedOnly) {
            queryString += "AND s.endDateTime != null ";
        }
        queryString += "ORDER BY s.endDateTime DESC";

        TypedQuery<Session> query = JpaUtil.obtenirContextePersistance()
                .createQuery(
                        queryString,
                        Session.class
                );
        query.setParameter("presenter", presenter);
        return query.getResultList();
    }

    // Garanteed to be ordered by most recent end dates first, nulls first
    public List<Session> findAllForEstablishment(Establishment establishment, Boolean finishedOnly) {
        String queryString = "SELECT s FROM Session s "
                + "WHERE s.student.establishment = :establishment ";
        if (finishedOnly) {
            queryString += "AND s.endDateTime != null ";
        }
        queryString += "ORDER BY s.endDateTime DESC";

        TypedQuery<Session> query = JpaUtil.obtenirContextePersistance()
                .createQuery(
                        queryString,
                        Session.class
                );
        query.setParameter("establishment", establishment);
        return query.getResultList();
    }

    public Session findWaitingByStudent(Student student) {
        TypedQuery<Session> query = JpaUtil.obtenirContextePersistance()
                .createQuery(
                        "SELECT s FROM Session s WHERE s.student = :student "
                        + "AND s.endDateTime IS NULL",
                        Session.class
                );
        query.setParameter("student", student);
        return query.getSingleResult();
    }

    public Session findWaitingByPresenter(Presenter presenter) {
        TypedQuery<Session> query = JpaUtil.obtenirContextePersistance()
                .createQuery(
                        "SELECT s FROM Session s WHERE s.presenter = :presenter "
                        + "AND s.endDateTime IS NULL",
                        Session.class
                );
        query.setParameter("presenter", presenter);
        return query.getSingleResult();
    }
}
