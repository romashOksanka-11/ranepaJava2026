package ru.ranepa.exception;

// Класс для ошибок "Ресурс не найден"
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
