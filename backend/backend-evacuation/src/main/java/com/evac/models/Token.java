package com.evac.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Token",
uniqueConstraints ={
        @UniqueConstraint(columnNames = "token"),
        @UniqueConstraint(columnNames = "user_id")
})
public class Token {

    @Id
    private String token;

    private Long id;

    public Token(){

    }

    public Token(String token, Long id){
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
