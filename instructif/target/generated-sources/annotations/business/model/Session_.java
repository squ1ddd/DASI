package business.model;

import business.model.ComprehenssionLevel;
import business.model.Presenter;
import business.model.SessionStatus;
import business.model.Student;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-05-29T15:09:09")
@StaticMetamodel(Session.class)
public class Session_ { 

    public static volatile SingularAttribute<Session, String> summary;
    public static volatile SingularAttribute<Session, Date> startDateTime;
    public static volatile SingularAttribute<Session, Presenter> presenter;
    public static volatile SingularAttribute<Session, Student> student;
    public static volatile SingularAttribute<Session, String> subject;
    public static volatile SingularAttribute<Session, ComprehenssionLevel> comprehenssionLevel;
    public static volatile SingularAttribute<Session, String> description;
    public static volatile SingularAttribute<Session, Long> id;
    public static volatile SingularAttribute<Session, Date> endDateTime;
    public static volatile SingularAttribute<Session, SessionStatus> status;

}