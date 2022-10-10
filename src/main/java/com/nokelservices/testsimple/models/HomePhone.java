package com.nokelservices.testsimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


// This is a non-entity class, used only for list representation
// of the call list of home phones.

@JsonPropertyOrder({ "userName", "homePhoneNumber"})
public class HomePhone {

    public HomePhone(UserName userName, String phoneNum){
        this.userName = userName;
        this.homePhoneNumber = phoneNum;
    }

    @JsonProperty("name")
    private UserName userName;

    @JsonProperty("phone")
    private String homePhoneNumber;

    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }
}
