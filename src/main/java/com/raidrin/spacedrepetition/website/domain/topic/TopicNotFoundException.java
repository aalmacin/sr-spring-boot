package com.raidrin.spacedrepetition.website.domain.topic;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException() {
    }

    public TopicNotFoundException(String message) {
        super(message);
    }

    public TopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
