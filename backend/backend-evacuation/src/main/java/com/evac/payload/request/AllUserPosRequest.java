package com.evac.payload.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@JsonSerialize
public class AllUserPosRequest {
    @NotBlank
    private String username;

    private String position;

    @NotBlank
    private LocalDateTime localDateTime;


    private String floorName;


    private String zoneName;

    public AllUserPosRequest(String username, String sensorSetPos, LocalDateTime localDateTime, String floorName, String zoneName) {
        this.username = username;
        this.position = sensorSetPos;
        this.localDateTime = localDateTime;
        this.floorName = floorName;
        this.zoneName = zoneName;
    }
    public AllUserPosRequest() {

    }

    public AllUserPosRequest(String username, LocalDateTime localDateTime) {
        this.username = username;
        this.localDateTime = localDateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}