/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author mlaurent1
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract public class Presenter extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    // This is highest / lowest as in number order, so if a presenter
    // can teach from 6ème to 1ère, skillLevelHighest will be 6
    // and skillLevelLowest will be 1.
    @Column
    Integer skillLevelHighest;
    @Column
    Integer skillLevelLowest;

    @OneToMany(mappedBy = "presenter")
    List<Session> sessionsAttended;
    
    @Override
    public String toString() {
        return String.format(
                "<Presenter: %s %s, teaches to levels from %s to %s>",
                firstName,
                surName,
                skillLevelLowest,
                skillLevelHighest
        );
    }
    
    public Presenter(
            String firstName,
            String surName,
            String email,
            String password,
            String phoneNumber,
            Integer skillLevelHighest,
            Integer skillLevelLowest
    ) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.skillLevelHighest = skillLevelHighest;
        this.skillLevelLowest = skillLevelLowest;
    }
    
    public Presenter(){}

    
    // Getter / Setters
    public Boolean getIsInSession() {
        Boolean out = false;
        for (Session session : this.sessionsAttended) {
            if (session.getEndDateTime() == null) {
                out = true;
            }
        }
        return out;
    }


    public Integer getSkillLevelHighest() {
        return skillLevelHighest;
    }

    public void setSkillLevelHighest(Integer skillLevelHighest) {
        this.skillLevelHighest = skillLevelHighest;
    }

    public Integer getSkillLevelLowest() {
        return skillLevelLowest;
    }

    public void setSkillLevelLowest(Integer skillLevelLowest) {
        this.skillLevelLowest = skillLevelLowest;
    }

    public List<Session> getSessionsAttended() {
        return this.sessionsAttended;
    }
    
    
}
