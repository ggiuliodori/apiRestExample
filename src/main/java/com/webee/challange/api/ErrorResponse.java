package com.webee.challange.api;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorResponse {

    private Date timestamp;
    private HttpStatus status;
    private Integer code;
    private String error;
    private String message;
    private String path;

}
