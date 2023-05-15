package com.evac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "sensorset",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "sensorName")
        }
)
public class SensorSet {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensorSetPos_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SensorSetPos sensorSetPos;

    @Id
    @NotBlank
    private String sensorName;


    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public SensorSetPos getSensorSetPos() {
        return sensorSetPos;
    }

    public void setSensorSetPos(SensorSetPos sensorSetPos) {
        this.sensorSetPos = sensorSetPos;
    }
}
