package com.evac.payload.request;

import javax.validation.constraints.NotBlank;

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
