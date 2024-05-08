package com.janawat.customer.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse {
    private int status;
    private String message;
    private Object[] data;

    public static ApiResponse formatValidationErrorResponse(BindingResult result) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.add(error.getField() + " " + error.getDefaultMessage());
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Validation error(s):", errors.toArray());
    }

    public static ApiResponse formatSuccessResponse(Object[] data){
        return  new ApiResponse(HttpStatus.CREATED.value(),"Added success",data);
    }
}
