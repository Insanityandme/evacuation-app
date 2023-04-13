package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "deputies")
public class Deputy {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    private boolean isActive;

    public Deputy() {

    }

    public Deputy(String username) {
        this.username = username;
        isActive = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        if(!isActive){
            isActive = true;
        } else {
            isActive = false;
        }
    }
}
