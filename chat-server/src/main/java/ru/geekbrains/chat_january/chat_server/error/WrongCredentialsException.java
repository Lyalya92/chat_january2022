package ru.geekbrains.chat_january.chat_server.error;

public class WrongCredentialsException extends RuntimeException {

    public WrongCredentialsException() {
    }

    public WrongCredentialsException(String message) {
        super(message);
    }

}
