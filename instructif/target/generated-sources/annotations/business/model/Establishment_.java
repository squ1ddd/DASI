package business.model;

import business.model.Student;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-05-29T15:09:09")
@StaticMetamodel(Establishment.class)
public class Establishment_ { 

    public static volatile SingularAttribute<Establishment, String> code;
    public static volatile SingularAttribute<Establishment, String> city;
    public static volatile SingularAttribute<Establishment, String> departmentCode;
    public static volatile SingularAttribute<Establishment, Double> latitude;
    public static volatile SingularAttribute<Establishment, String> name;
    public static volatile ListAttribute<Establishment, Student> students;
    public static volatile SingularAttribute<Establishment, String> IPS;
    public static volatile SingularAttribute<Establishment, Double> longitude;

}