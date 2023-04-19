package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table( name = "notifications",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Notification {

    public Notification() {}

    public Notification(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 20)
    private String name;

    @NotBlank
    @Column(length = 100)
    private String message;


    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

