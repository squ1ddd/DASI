/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author mlaurent1
 */
@Entity
public class Establishment {
    @Id
    @Column
    String code;
    
    @Column
    String name;
    
    @Column
    String IPS;
    
    @Column
    String city;
    
    @Column
    String departmentCode;
    
    @Column
    double latitude;
    
    @Column
    double longitude;
    
    @OneToMany(mappedBy = "establishment")
    List<Student> students;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIPS() {
        return IPS;
    }

    public void setIPS(String IPS) {
        this.IPS = IPS;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Establishment() {};
    
    public Establishment(String code, String name, String IPS, String city, String departmentCode, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.IPS = IPS;
        this.city = city;
        this.departmentCode = departmentCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Establishment<" + "code=" + code + ", name=" + name + ", IPS=" + IPS + ", city=" + city + ", departmentCode=" + departmentCode + ", latitude=" + latitude + ", longitude=" + longitude + '>';
    }
}
