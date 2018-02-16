package com.raidrin.spacedrepetition.website.study;

public enum Rating {
    VERY_HARD (1),
    HARD (2),
    MEDIUM (3),
    EASY (4),
    VERY_EASY (5);

    private final int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
