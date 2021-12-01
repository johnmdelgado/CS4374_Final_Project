package com.cs4374.finalproject.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException(String message){
        super(message);
    }
}
