package com.raidrin.spacedrepetition.website.domain.study.rating;

public class InvalidRatingException extends RuntimeException {
    public InvalidRatingException() {
    }

    public InvalidRatingException(String message) {
        super(message);
    }

    public InvalidRatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
