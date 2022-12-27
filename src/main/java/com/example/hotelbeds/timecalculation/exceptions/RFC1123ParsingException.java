package com.example.hotelbeds.timecalculation.exceptions;

public class RFC1123ParsingException extends Exception{
    public RFC1123ParsingException(String timestamp1, String timestamp2) {
        super("Strings couldn't be parsed to RFC1123 date format  :: " + timestamp1 + "," + timestamp2);
    }
}
