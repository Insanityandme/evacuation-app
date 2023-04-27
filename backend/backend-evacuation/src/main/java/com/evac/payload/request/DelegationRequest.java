package com.evac.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class DelegationRequest {

    private Set<String> zone;
    @NotBlank
    @Size(min = 6, max = 40)
    private String floorname;

    public Set<String> getZone() {
        return this.zone;
    }

    public void setZone(Set<String> role) {
        this.zone = zone;
    }

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }
}
