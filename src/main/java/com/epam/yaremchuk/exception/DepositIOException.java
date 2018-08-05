package com.epam.yaremchuk.exception;

import java.io.IOException;

public class DepositIOException extends IOException {

    public DepositIOException() {
        super();
    }

    public DepositIOException(String message) {
        super(message);
    }

    public DepositIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositIOException(Throwable cause) {
        super(cause);
    }

}
