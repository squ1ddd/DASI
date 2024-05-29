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
public class PresenterTypeOther extends Presenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String activitySector;

    public String getActivitySector() {
        return activitySector;
    }

    public void setActivitySector(String activitySector) {
        this.activitySector = activitySector;
    }
    
    
    
    public PresenterTypeOther(){}
    
    public PresenterTypeOther(
        String firstName,
        String surName,
        String email,
        String password,
        String phoneNumber,
        Integer skillLevelHighest, 
        Integer skillLevelLowest,
        String activitySector
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
        this.activitySector = activitySector;
    }
}
