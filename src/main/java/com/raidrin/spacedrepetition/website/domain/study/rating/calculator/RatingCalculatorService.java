package com.raidrin.spacedrepetition.website.domain.study.rating.calculator;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;

import java.util.ArrayList;

public interface RatingCalculatorService {
    int calculateRating(ArrayList<Rating> ratings);
}
