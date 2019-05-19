package com.bloxbean.bloxql.exception;

public class DataConversionException extends RuntimeException {

    public DataConversionException(String msg) {
        super(msg);
    }

    public DataConversionException(Exception e) {
        super(e);
    }
}
