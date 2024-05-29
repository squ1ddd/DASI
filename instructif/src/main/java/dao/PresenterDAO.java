package dao;
import javax.persistence.TypedQuery;

import business.model.Presenter;
import business.model.PresenterTypeOther;
import business.model.PresenterTypeStudent;
import business.model.PresenterTypeTeacher;

/**
 *
 * @author mlaurent1
 */
public class PresenterDAO {
    public void createTypeStudent(PresenterTypeStudent presenter) {
        JpaUtil.obtenirContextePersistance().persist(presenter);
    }

    public void createTypeTeacher(PresenterTypeTeacher presenter) {
        JpaUtil.obtenirContextePersistance().persist(presenter);
    }

    public void createTypeOther(PresenterTypeOther presenter) {
        JpaUtil.obtenirContextePersistance().persist(presenter);
    }

    public Presenter update(Presenter presenter) {
        return JpaUtil.obtenirContextePersistance().merge(presenter);
    }

    // Finds an available presenter
    public Presenter findAvailable(Integer skillLevel) {
      TypedQuery<Presenter> query = JpaUtil.obtenirContextePersistance().createQuery(
            "SELECT p FROM Presenter p " +
            "LEFT JOIN p.sessionsAttended sa " +
            "WHERE NOT EXISTS (" +
            "  SELECT s FROM Session s" +
            "  WHERE s.presenter = p AND s.endDateTime IS NULL" +
            ") " +
            "AND p.skillLevelHighest >= :skillLevel AND p.skillLevelLowest <= :skillLevel " +
            "GROUP BY p " +
            "ORDER BY COUNT(sa)",
            Presenter.class
        );
        query.setParameter("skillLevel", skillLevel);
        query.setMaxResults(1);
        return query.getResultList().isEmpty() ? null : query.getResultList().get(0);
    }
}
