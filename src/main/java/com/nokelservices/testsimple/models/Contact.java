package com.nokelservices.testsimple.models;

//import com.challenge.contacts.contactApi.LoadInitialData;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonPropertyOrder({ "id", "userName", "userAddress", "phones", "email" })
public class Contact {

    private static final Logger log = LoggerFactory.getLogger(Contact.class);

    private @Id @GeneratedValue Long  id;

    @OneToOne
    @JoinColumn(name = "username_id")
    @JsonIgnoreProperties("contact")
    private UserName userName;

    @OneToOne
    @JoinColumn(name = "useraddress_id")
    @JsonIgnoreProperties("contact")
    private UserAddress userAddress;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contact", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("contact")
    private List<Phone> phones = new ArrayList<>();

    @Column
    private String email;

    public Contact(){}

    public Contact(UserName name, UserAddress address, List<Phone> phones, String email){
        this.userName = name;
        this.userAddress = address;
        this.setPhones(phones);
        this.email = email;
    }

    //
    // accessors
    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    @JsonProperty("address")
    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    @JsonProperty("phone")
    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;

        // This is key to establising the bi-directional linking.
        for(Phone p: this.phones){
            p.setContact(this);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
