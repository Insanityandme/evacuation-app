package com.evac.payload.request;

import javax.validation.constraints.NotBlank;
/**
 * this is a class used for storing information
 * in a @RequestBody in NotifyController. It has
 * getters which are used to extract information from
 * the @RequestBody.
 */
public class MessageRequest {

    public MessageRequest() {
    }
    @NotBlank
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
