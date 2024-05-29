/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author mlaurent1
 */
@Entity 
public class Student extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    @Column
    Integer skillLevel;
    
    @Temporal(TemporalType.DATE)
    Date birthDate;

    @ManyToOne
    @JoinColumn(name = "establishment_code")
    Establishment establishment;

    @OneToMany(mappedBy = "student")
    List<Session> sessionsAttended;

    public Student() {}
    
    public Student(
            String firstName,
            String surName,
            String email,
            String password,
            String phoneNumber,
            Integer skillLevel,
            Date birthDate
    ) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.skillLevel = skillLevel;
        this.birthDate = birthDate;
    }
    
    @Override
    public String toString() {
        return String.format("<Student: %s %s (%s), in %d>", firstName, surName, email, skillLevel);
    }

    public Integer getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public List<Session> getSessionsAttended() {
        return sessionsAttended;
    }
    
    
}
