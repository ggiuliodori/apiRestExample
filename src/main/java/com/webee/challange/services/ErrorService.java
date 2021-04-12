package com.webee.challange.services;

import lombok.Data;

@Data
public class ErrorService extends Exception {

    public ErrorService (String message) {
        super(message);
    }
}
