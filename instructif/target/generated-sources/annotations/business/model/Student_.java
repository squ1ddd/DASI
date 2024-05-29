package business.model;

import business.model.Establishment;
import business.model.Session;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-05-29T15:09:09")
@StaticMetamodel(Student.class)
public class Student_ extends Person_ {

    public static volatile SingularAttribute<Student, Establishment> establishment;
    public static volatile ListAttribute<Student, Session> sessionsAttended;
    public static volatile SingularAttribute<Student, Long> id;
    public static volatile SingularAttribute<Student, Integer> skillLevel;
    public static volatile SingularAttribute<Student, Date> birthDate;

}