package controllers;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class User {

    public int id;
    public String name;
    public String password;
    public String email;
    public String gender;
    public String country;
    public String skills;
    public String interests;
    public boolean active;

    public User(int id, String name,String password,
                String email, String gender,
                String country,String skills,String interests,
                boolean active) {
        this.id = id;
        this.name = name;
        this.password=password;
        this.email = email;
        this.gender = gender;
        this.country = country;
        this.skills=skills;
        this.interests=interests;
        this.active=active;
    }

    // Add getters and setters as needed
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getPassword() {
        return password;
    }

    public String getInterests() {
        return interests;
    }

    public String getSkills() {
        return skills;
    }
}
