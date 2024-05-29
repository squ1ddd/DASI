/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author mlaurent1
 */
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    // @Column
    // String urlCode;
    @Column
    String description;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date startDateTime;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date endDateTime;
    
    @Column
    String subject;

    @Column
    SessionStatus status;

    @Column
    String summary; // A summary of the session written by the presenter

    @Column
    ComprehenssionLevel comprehenssionLevel;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "presenter_id")
    Presenter presenter;

    public Session() {}

    public Session(
            String description, 
            Date startDateTime, 
            Date endDateTime, 
            String subject, 
            SessionStatus status, 
            String summary, 
            ComprehenssionLevel comprehenssionLevel, 
            Student student, 
            Presenter presenter
    ) {
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.subject = subject;
        this.status = status;
        this.summary = summary;
        this.comprehenssionLevel = comprehenssionLevel;
        this.student = student;
        this.presenter = presenter;
    }   

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ComprehenssionLevel getComprehenssionLevel() {
        return comprehenssionLevel;
    }

    public void setComprehenssionlevel(ComprehenssionLevel comprehenssionLevel) {
        this.comprehenssionLevel = comprehenssionLevel;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
    
    @Override
    public String toString() {
        return String.format(
            "<Session: desc='%s', student='%s', presenter='%s', status='%s'>",
            this.description,
            this.student.toString(),
            this.presenter.toString(),
            this.status.toString()
        );
    }
}
