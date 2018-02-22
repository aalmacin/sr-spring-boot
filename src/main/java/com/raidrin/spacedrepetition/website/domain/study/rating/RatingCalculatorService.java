package com.raidrin.spacedrepetition.website.domain.study.rating;

import java.util.ArrayList;

public interface RatingCalculatorService {
    int calculateRating(ArrayList<Rating> ratings);
}
