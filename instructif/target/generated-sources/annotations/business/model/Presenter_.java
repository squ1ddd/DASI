package business.model;

import business.model.Session;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-05-29T15:09:09")
@StaticMetamodel(Presenter.class)
public abstract class Presenter_ extends Person_ {

    public static volatile SingularAttribute<Presenter, Integer> skillLevelLowest;
    public static volatile SingularAttribute<Presenter, Integer> skillLevelHighest;
    public static volatile ListAttribute<Presenter, Session> sessionsAttended;
    public static volatile SingularAttribute<Presenter, Long> id;

}