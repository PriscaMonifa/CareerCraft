package com.evaluation.config;


import com.evaluation.exceptions.ResourceNotFoundException;
import com.evaluation.utility.ResponseUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private ResponseUtility responseUtility;

    //ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseUtility> handleResourceNotFoundException(ResourceNotFoundException e){
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }

    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult=e.getBindingResult();
        List<FieldError> errors= bindingResult.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for(FieldError error:errors){
            map.put(error.getField(),error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(map);
    }


    // IOException
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseUtility> handleIOException(IOException e){
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtility);
    }

}
