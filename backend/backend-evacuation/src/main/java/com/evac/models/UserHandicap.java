package com.evac.models;

import com.evac.repository.UserHandicapRepositoryKey;

import javax.persistence.*;

@Entity @IdClass(UserHandicapRepositoryKey.class)
@Table(name = "user_handicap")

public class UserHandicap {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "handicap_id")
    private Long handicapId;

    public UserHandicap(){

    }

    public UserHandicap(Long userId, Long handicapId){
        this.userId = userId;
        this.handicapId = handicapId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHandicapId() {
        return handicapId;
    }

    public void setHandicapId(Long handicapId) {
        this.handicapId = handicapId;
    }
}
