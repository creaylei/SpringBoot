package com.springboot.globalexception;

import lombok.Data;

@Data
public class ResponseDto<T> {

    private int code;
    private String message;
    private T data;

    public ResponseDto() {}

    public ResponseDto(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static <T> ResponseDto success(T data) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(0);
        responseDto.setData(data);
        return responseDto;
    }

    public static <T> ResponseDto fail(String msg) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(msg);
        return responseDto;
    }
}
