package com.evac.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SensorRequest {
    @NotBlank
    private Set<String> sensorName;
    @NotBlank
    private String position;

    public Set<String> getSensorName() {
        return sensorName;
    }

    public void setSensorName(Set<String> sensorName) {
        this.sensorName = sensorName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
