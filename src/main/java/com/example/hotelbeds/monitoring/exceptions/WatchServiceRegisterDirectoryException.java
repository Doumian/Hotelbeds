package com.example.hotelbeds.monitoring.exceptions;

public class WatchServiceRegisterDirectoryException extends Exception{
    public WatchServiceRegisterDirectoryException(String message) {
        super("WatchService couldn't register Directory  :: " + message);
    }
}
