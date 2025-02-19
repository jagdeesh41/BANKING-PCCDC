package com.learn.exception;

public class ResourceAlreadyFoundException extends RuntimeException{
    public ResourceAlreadyFoundException(String resourceName, String fieldName, String fieldValue)
    {
        super(String.format("%s found with the given input data %s: '%s' ",resourceName,fieldName,fieldValue));
    }
}
