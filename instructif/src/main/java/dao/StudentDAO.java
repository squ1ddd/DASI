package dao;
import java.util.List;

import javax.persistence.TypedQuery;

import business.model.Student;

/**
 *
 * @author mlaurent1
 */
public class StudentDAO {
    public void create(Student student) {
        JpaUtil.obtenirContextePersistance().persist(student);
    }

    public Student update(Student student) {
        return JpaUtil.obtenirContextePersistance().merge(student);
    }

    public Student findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Student.class, id);
    }
    
    public Student findByEmail(String email) {
        TypedQuery<Student> query = JpaUtil.obtenirContextePersistance()
                .createQuery(
                    "SELECT s FROM Student s WHERE s.email = :mail", 
                    Student.class);
            query.setParameter("mail", email);

            // Get the single result
            Student student = query.getSingleResult();
            return student;
    }

    public List<Student> findAll() {
        String s = "select e from Student e order by e.nom asc";
        TypedQuery<Student> query = JpaUtil.obtenirContextePersistance().createQuery(s, Student.class);
        return query.getResultList();
    }
}
