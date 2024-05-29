/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author mlaurent1
 */
@Entity
public class PresenterTypeStudent extends Presenter{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    @Column
    String university;
    @Column
    String major;

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    
    // Constructor with parameters
    public PresenterTypeStudent(
        String firstName,
        String surName,
        String email,
        String password,
        String phoneNumber,
        Integer skillLevelHighest, 
        Integer skillLevelLowest,
        String university,
        String major
    ) {
        super(
            firstName, 
            surName, 
            email, 
            password,
            phoneNumber,
            skillLevelHighest, 
            skillLevelLowest
        );
        this.university = university;
        this.major = major;
    }
    
    public PresenterTypeStudent() {}

}
