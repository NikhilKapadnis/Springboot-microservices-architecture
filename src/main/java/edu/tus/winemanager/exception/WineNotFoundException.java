package edu.tus.winemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WineNotFoundException extends RuntimeException
{
    /**
     * Constructs a new runtime exception with the specified detail message.
     */
    public WineNotFoundException(String message)
    {
        super(message);
    }
}


