package com.mycompany.contact.dto;

public class Contact {
    private String name;
    private String phone;

    // Default constructor (needed for JSON deserialization)
    public Contact() {}

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
