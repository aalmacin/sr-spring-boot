package com.raidrin.spacedrepetition.website.study;

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
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_EASY);
        for (int i=0; i < 5; i++) ratings1.add(Rating.EASY);
        for (int i=0; i < 5; i++) ratings1.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings1.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings1.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings1.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings1.add(Rating.HARD);
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings1.add(Rating.VERY_HARD);

        ArrayList<Rating> ratings2 = new ArrayList<>();
        for (int i=0; i < 2; i++) ratings2.add(Rating.VERY_HARD);

        ArrayList<Rating> ratings3 = new ArrayList<>();
        for (int i=0; i < 11; i++) ratings3.add(Rating.MEDIUM);

        ArrayList<Rating> ratings4 = new ArrayList<>();
        for (int i=0; i < 31; i++) ratings4.add(Rating.EASY);

        ArrayList<Rating> ratings5 = new ArrayList<>();
        for (int i=0; i < 5; i++) ratings5.add(Rating.EASY);
        for (int i=0; i < 5; i++) ratings5.add(Rating.EASY);
        for (int i=0; i < 5; i++) ratings5.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings5.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.EASY);
        for (int i=0; i < 5; i++) ratings5.add(Rating.EASY);
        for (int i=0; i < 5; i++) ratings5.add(Rating.EASY);
        for (int i=0; i < 5; i++) ratings5.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings5.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings5.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings5.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings5.add(Rating.HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings5.add(Rating.VERY_HARD);

        return Arrays.asList(
                new Object[][] {
                        { ratings1, 4},
                        { ratings2, 1},
                        { ratings3, 3},
                        { ratings4, 4},
                        { ratings5, 3}
                }
        );
    }

    @Test
    public void calculateRating() throws Exception {
        RatingCalculator ratingCalculator = new RatingCalculatorImpl();

        int calculationResult = ratingCalculator.calculateRating(ratings);

        assertThat(calculationResult, is(equalTo(result)));
    }
}