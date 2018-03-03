package com.raidrin.spacedrepetition.website.domain.study;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.study.rating.calculator.RatingCalculatorService;
import com.raidrin.spacedrepetition.website.domain.study.rating.calculator.RatingCalculatorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class RatingCalculatorImplTest {
    private final ArrayList<Rating> ratings;
    private final int result;

    public RatingCalculatorImplTest(ArrayList<Rating> ratings, int result) {
        this.ratings = ratings;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        ArrayList<Rating> ratings1 = new ArrayList<>();
        ratings1.add(Rating.VERY_EASY);
        ratings1.add(Rating.EASY);
        ratings1.add(Rating.EASY);
        ratings1.add(Rating.EASY);
        ratings1.add(Rating.VERY_EASY);

        ratings1.add(Rating.VERY_EASY);
        ratings1.add(Rating.MEDIUM);
        ratings1.add(Rating.VERY_EASY);
        ratings1.add(Rating.MEDIUM);
        ratings1.add(Rating.EASY);

        ratings1.add(Rating.VERY_HARD);
        ratings1.add(Rating.VERY_EASY);
        ratings1.add(Rating.MEDIUM);
        ratings1.add(Rating.HARD);
        ratings1.add(Rating.VERY_EASY);

        ratings1.add(Rating.HARD);
        ratings1.add(Rating.VERY_HARD);
        ratings1.add(Rating.VERY_HARD);

        ArrayList<Rating> ratings2 = new ArrayList<>();
        ratings2.add(Rating.VERY_EASY);
        ratings2.add(Rating.EASY);
        ratings2.add(Rating.EASY);

        ArrayList<Rating> ratings3 = new ArrayList<>();
        ratings3.add(Rating.MEDIUM);
        ratings3.add(Rating.VERY_HARD);
        ratings3.add(Rating.MEDIUM);
        ratings3.add(Rating.EASY);
        ratings3.add(Rating.VERY_EASY);

        ratings3.add(Rating.MEDIUM);
        ratings3.add(Rating.HARD);
        ratings3.add(Rating.VERY_HARD);
        ratings3.add(Rating.VERY_EASY);

        ArrayList<Rating> ratings4 = new ArrayList<>();
        ratings4.add(Rating.VERY_HARD);
        ratings4.add(Rating.VERY_HARD);
        ratings4.add(Rating.VERY_HARD);
        ratings4.add(Rating.VERY_HARD);
        ratings4.add(Rating.VERY_HARD);

        ratings4.add(Rating.VERY_EASY);
        ratings4.add(Rating.HARD);
        ratings4.add(Rating.MEDIUM);
        ratings4.add(Rating.EASY);
        ratings4.add(Rating.HARD);

        ratings4.add(Rating.VERY_EASY);
        ratings4.add(Rating.VERY_EASY);
        ratings4.add(Rating.VERY_EASY);
        ratings4.add(Rating.VERY_EASY);
        ratings4.add(Rating.VERY_EASY);


        ArrayList<Rating> ratings5 = new ArrayList<>();

        return Arrays.asList(
                new Object[][] {
                        { ratings1, 4},
                        { ratings2, 3},
                        { ratings3, 3},
                        { ratings4, 2},
                        { ratings5, 1}
                }
        );
    }

    @Test
    public void calculateRating() throws Exception {
        RatingCalculatorService ratingCalculator = new RatingCalculatorServiceImpl();

        int calculationResult = ratingCalculator.calculateRating(ratings);

        assertThat(calculationResult, is(equalTo(result)));
    }
}