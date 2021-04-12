package com.webee.challange.repository.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String macAddress;

    private Date timestamp;


    public Device() {
    }

    public Device(Long id, String macAddress, Date timestamp) {
        this.id = id;
        this.macAddress = macAddress;
        this.timestamp = timestamp;
    }
}
