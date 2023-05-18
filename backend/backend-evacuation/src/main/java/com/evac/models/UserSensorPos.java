package com.evac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@IdClass(UserSensorPosRepostiroyKey.class)
@Table(name = "userSensorPos")
public class UserSensorPos {

    @Id
    @Column
    private String username;

    @Column(length = 20)
    private String floorName;

    @Column(length = 5)
    private String zoneName;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensorSetPos_position", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SensorSetPos sensorSetPos;

    @Id
    @Column
    private LocalDateTime localDateTime;

}
