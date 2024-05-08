package com.janawat.customer.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse {
    private int status;
    private String message;
    private Object[] data;

    public static ApiResponse errorResponse(HttpStatus status,String message, Object[] data) {

        return new ApiResponse(status.value(),message,data);
    }

    public static ApiResponse successResponse(HttpStatus status,String message,Object[] data){
        return  new ApiResponse(status.value(), message, data);
    }
}
