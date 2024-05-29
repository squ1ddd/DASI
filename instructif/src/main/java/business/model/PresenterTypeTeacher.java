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
public class PresenterTypeTeacher extends Presenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    @Column
    String institutionType;

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public PresenterTypeTeacher() {}

    public PresenterTypeTeacher(
            String firstName,
            String surName,
            String email,
            String password,
            String phoneNumber,
            Integer skillLevelHighest,
            Integer skillLevelLowest,
            String institutionType
    ) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.skillLevelHighest = skillLevelHighest;
        this.skillLevelLowest = skillLevelLowest;
        this.institutionType = institutionType;
    }
}
