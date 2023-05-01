package com.javarush.exception;

import com.javarush.exception.handler.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LogManager.getLogger();

    //For UI Pages
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(UserNotFoundException exception){
        LOGGER.info("Exception handler is working here...");
        return "error404";
    }

    //For APIs
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
