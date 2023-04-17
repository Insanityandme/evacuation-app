package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "user_notifications",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "name", "message"})
        })


public class UserNotification {

    public UserNotification(){}

    public UserNotification(Long userId, String name, String message){
        this.userId = userId;
        this.message = message;
        this.name = name;
    }

    @Id
    @NotNull
    private Long userId; //Name of the variable must be the same as the column name in the table


    @NotBlank
    @Column(length = 20)
    private String name;

    @Column(length = 100)
    private String message;


    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}