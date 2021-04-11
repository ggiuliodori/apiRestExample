package com.webee.challange.repository.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String macAddress;

    private Timestamp timestamp;


    public Device() {
    }

    public Device(Long id, String macAddress, Timestamp timestamp) {
        this.id = id;
        this.macAddress = macAddress;
        this.timestamp = timestamp;
    }
}
