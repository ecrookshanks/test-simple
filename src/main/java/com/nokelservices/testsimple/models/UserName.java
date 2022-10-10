package com.nokelservices.testsimple.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class UserName {

    private @Id @GeneratedValue Long  id;

    @Column
    private String first;
    @Column
    private String middle;
    @Column
    private String last;

    @OneToOne(mappedBy = "userName")
    @JsonIgnoreProperties("userName")
    private Contact contact;

    public UserName(String first, String middle, String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    public UserName(){}

    //
    // accessors
    //

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @JsonIgnore
    public Contact getContact(){
        return this.contact;
    }

    public void setContact(Contact contact){
        this.contact = contact;
    }
}
