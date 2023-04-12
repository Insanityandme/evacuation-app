package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table ( name = "evacleader_priority",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "evacleader_id")
            })

public class EvacLeaderPriority {

    public EvacLeaderPriority(){}

    public EvacLeaderPriority(Long evacleaderId, Long priority){
        this.evacleader_id = evacleaderId;
        this.priority = priority;
    }

    @Id
    @NotNull
    private Long evacleader_id; //Name of the variable must be the same as the column name in the table

    @NotNull
    private Long priority;

    public Long getId() {
        return evacleader_id;
    }

    public void setId(Long id) {
        this.evacleader_id = id;
    }

    public Long getpriority(){
        return priority;
    }

    public void setpriority(Long priority){
        this.priority = priority;
    }

}
