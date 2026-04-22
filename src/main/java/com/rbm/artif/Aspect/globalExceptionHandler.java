package com.rbm.artif.Aspect;

import com.rbm.artif.Exception.ArtifException;
import com.rbm.artif.Exception.ErrorResponse;
import com.rbm.artif.Exception.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalExceptionHandler{
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorResponse> userExist(){
        ErrorResponse error=new ErrorResponse("USER_EMAIL_EXIST_ALREADY");
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArtifException.class)
    public ResponseEntity<ErrorResponse> wrongUser(){
        ErrorResponse error=new ErrorResponse("INVALID_CREDENTIALS");
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
