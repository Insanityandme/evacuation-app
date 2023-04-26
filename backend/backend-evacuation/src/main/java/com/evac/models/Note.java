package com.evac.models;

import java.util.Map;

public class Note {
    private String subject;
    private String content;
    private Map<String, String> data;


    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Map<String, String> getData() {
        return data;
    }

}
