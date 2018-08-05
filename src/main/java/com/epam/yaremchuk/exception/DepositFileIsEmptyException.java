package com.epam.yaremchuk.exception;

import java.io.IOException;

public class DepositFileIsEmptyException extends IOException {

    public DepositFileIsEmptyException() {
        super();
    }

    public DepositFileIsEmptyException(String message) {
        super(message);
    }

    public DepositFileIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositFileIsEmptyException(Throwable cause) {
        super(cause);
    }

}
