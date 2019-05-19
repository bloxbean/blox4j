package com.bloxbean.bloxql.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String msg) {
        super(msg);
    }

    public ConnectionException(Exception e) {
        super(e);
    }
}
