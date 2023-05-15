package com.evac.models;

import javax.persistence.*;

@Entity
@Table(name = "sensorSetPos")
public class SensorSetPos {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "position")
    private String position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorSetPos() {


    }
}
