package com.raidrin.spacedrepetition.website.domain.study.rating.calculator;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;

import java.util.ArrayList;
import java.util.List;

public interface RatingCalculatorService {
    int calculateRating(List<Rating> ratings);
}
