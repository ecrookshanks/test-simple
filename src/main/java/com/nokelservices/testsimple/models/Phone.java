package com.nokelservices.testsimple.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Phone {

    public static final String PHONE_MOBILE = "mobile";
    public static final String PHONE_WORK = "work";
    public static final String PHONE_HOME = "home";


    private @Id @GeneratedValue Long  id;

    @Column
    private String number;
    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name="contact_id")
    @JsonIgnoreProperties("phones")
    private Contact contact;

    public Phone(String number, String type){
        this.number = number;
        this.type = type;
    }

    public Phone(){}

    //
    // Accessors
    //

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Contact getContact(){
        return this.contact;
    }

    public void setContact(Contact contact){
        this.contact = contact;
    }
}
