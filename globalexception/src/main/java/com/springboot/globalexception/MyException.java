package com.springboot.globalexception;

import lombok.Data;

@Data
public class MyException extends RuntimeException{

    private int code;

    public MyException() {
        super();
    }

    public MyException(int code, String message) {
        super(message);
        this.code = code;
    }
}
