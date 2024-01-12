package edu.daidp.shoppingwebapp.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

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

    public ApplicationResponse(int statusCode, String message, T data, List<String> details) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.details = details;
    }

    @Override
    public String toString() {
        return "ApplicationResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", details=" + details +
                '}';
    }
}