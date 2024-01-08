package edu.daidp.shoppingwebapp.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse<T> {


    private int statusCode;
    //General error message about nature of error
    private String message;

    private T data;

    //Specific errors in API request processing
    private List<String> details;

    public ApplicationResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}