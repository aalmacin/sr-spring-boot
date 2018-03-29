package com.raidrin.spacedrepetition.website.domain.study.rating;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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

    public static Rating getByValue(int value) {
        final Optional<Rating> ratingOptional = Arrays.stream(values()).filter(rating -> rating.getValue() == value)
                .findFirst();
        if(ratingOptional.isPresent()) return ratingOptional.get();
        else throw new InvalidRatingException("Invalid Rating value is passed");
    }

    public int getValue() {
        return value;
    }
}
