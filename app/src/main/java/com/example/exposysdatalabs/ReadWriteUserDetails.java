package com.example.exposysdatalabs;

public class ReadWriteUserDetails {

    public String name;
    public String email;
    public String mobileNumber;
    public String domain;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }

    public String password;

    //Constructor
    public ReadWriteUserDetails(){

    }
    public ReadWriteUserDetails(String username, String mail, String mobile, String domain, String password) {

        this.name = username;
        this.email = mail;
        this.mobileNumber = mobile;
        this.domain="";
        this.password=password;

    }
}
