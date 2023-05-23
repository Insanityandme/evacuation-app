package com.evac.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SensorRequest {
    @NotBlank
    private Set<String> sensorName;
    @NotBlank
    private String position;

    @NotBlank
    private String floorName;

    @NotBlank
    private String zoneName;

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

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }
}
