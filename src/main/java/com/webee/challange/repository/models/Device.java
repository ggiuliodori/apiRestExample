package com.webee.challange.repository.models;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String macAddress;

    private Date timestamp;


    public Device() {
    }

    public Device(Long id, @NonNull String macAddress, Date timestamp) {
        this.id = id;
        this.macAddress = macAddress;
        this.timestamp = timestamp;
    }
}
