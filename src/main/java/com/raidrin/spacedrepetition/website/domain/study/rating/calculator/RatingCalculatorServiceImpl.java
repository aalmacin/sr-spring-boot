package com.raidrin.spacedrepetition.website.domain.study.rating.calculator;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;

import java.util.ArrayList;
import java.util.List;

// TODO improve the code
public class RatingCalculatorServiceImpl implements RatingCalculatorService {
    private static final int SET_COUNT = 5;

    @Override
    public int calculateRating(List<Rating> ratings) {
        int ratingsCount = getRatingsCount(ratings);

        List<List<Rating>> splitRatings = groupRatings(ratings, ratingsCount);

        double firstTotal = getFirstTotal(splitRatings);

        List<Double> totalAvgs = getTotalAvgs(splitRatings);

        double otherTotals = getRemainingTotals(totalAvgs);

        return getWeightedAverage(firstTotal, otherTotals);
    }

    private int getRatingsCount(List<Rating> ratings) {
        return (int) Math.ceil((float) ratings.size() / SET_COUNT);
    }

    private List<List<Rating>> groupRatings(List<Rating> ratings, int ratingsCount) {
        List<List<Rating>> splitRatings = new ArrayList<>();

        for (int i = 0; i < ratingsCount; i++) {
            List<Rating> ratingList = new ArrayList<>();
            for (int j = 0; j < SET_COUNT ; j++){
                final int ratingIndex = (i * SET_COUNT) + j;
                if(ratingIndex < ratings.size()) ratingList.add(ratings.get(ratingIndex));
            }
            splitRatings.add(ratingList);
        }
        return splitRatings;
    }

    private double getFirstTotal(List<List<Rating>> splitRatings) {
        if(splitRatings.size() > 0) {
            final List<Rating> firstSet = splitRatings.remove(0);
            return firstSet.stream()
                    .mapToDouble(Rating::getValue)
                    .sum() / firstSet.size();
        } else {
            return 1;
        }
    }

    private List<Double> getTotalAvgs(List<List<Rating>> splitRatings) {
        List<Double> totalAvgs = new ArrayList<>();
        for (List<Rating> tempRatings : splitRatings) {
            final double totalSum = tempRatings.stream().mapToDouble(Rating::getValue).sum();
            double totalAvg = totalSum / tempRatings.size();
            totalAvgs.add(totalAvg);
        }
        return totalAvgs;
    }

    private double getRemainingTotals(List<Double> totalAvgs) {
        final double getRemainingTotalSum = totalAvgs.stream().mapToDouble(Double::doubleValue).sum();
        return (totalAvgs.size() > 0) ? getRemainingTotalSum / totalAvgs.size() : 1;
    }

    private int getWeightedAverage(double firstTotal, double otherTotals) {
        return (int) (Math.round( ((firstTotal * 70) + (otherTotals * 30)) / (100)));
    }
}
