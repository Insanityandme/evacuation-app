package com.evac.models;

import java.io.Serializable;

//This class should be used just to have two columns as primary key for the user_handicap table
public class UserHandicapRepositoryKey implements Serializable {
    private Long userId;
    private Long handicapId;


}
