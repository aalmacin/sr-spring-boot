package com.raidrin.spacedrepetition.website.domain.study.rating.calculator;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;

import java.util.ArrayList;

// TODO improve the code
public class RatingCalculatorServiceImpl implements RatingCalculatorService {
    private static final int SET_COUNT = 5;

    @Override
    public int calculateRating(ArrayList<Rating> ratings) {
        int ratingsCount = getRatingsCount(ratings);

        ArrayList<ArrayList<Rating>> splitRatings = splitRatings(ratings, ratingsCount);

        double firstTotal = getFirstTotal(splitRatings);

        ArrayList<Double> totalAvgs = getTotalAvgs(splitRatings);

        double otherTotals = getOtherTotals(totalAvgs);

        return getWeightedAverage(firstTotal, otherTotals);
    }

    private int getRatingsCount(ArrayList<Rating> ratings) {
        return (int) Math.ceil((float) ratings.size() / SET_COUNT);
    }

    private ArrayList<ArrayList<Rating>> splitRatings(ArrayList<Rating> ratings, int ratingsCount) {
        ArrayList<ArrayList<Rating>> splitRatings = new ArrayList<>();

        for (int i = 0; i < ratingsCount; i++) {
            ArrayList<Rating> ratingList = new ArrayList<>();
            for (int j = 0; j < SET_COUNT ; j++){
                final int ratingIndex = (i * SET_COUNT) + j;
                if(ratingIndex < ratings.size()) ratingList.add(ratings.get(ratingIndex));
            }
            splitRatings.add(ratingList);
        }
        return splitRatings;
    }

    private double getFirstTotal(ArrayList<ArrayList<Rating>> splitRatings) {
        if(splitRatings.size() > 0) {
            final ArrayList<Rating> firstSet = splitRatings.remove(0);
            return firstSet.stream()
                    .mapToDouble(Rating::getValue)
                    .sum() / firstSet.size();
        } else {
            return 1;
        }
    }

    private ArrayList<Double> getTotalAvgs(ArrayList<ArrayList<Rating>> splitRatings) {
        ArrayList<Double> totalAvgs = new ArrayList<>();
        for (ArrayList<Rating> ratingBuffer : splitRatings) {
            double totalAvg = ratingBuffer.stream().mapToDouble(Rating::getValue).sum() / ratingBuffer.size();
            totalAvgs.add(totalAvg);
        }
        return totalAvgs;
    }

    private double getOtherTotals(ArrayList<Double> totalAvgs) {
        return (totalAvgs.size() > 0) ? totalAvgs.stream().mapToDouble(Double::doubleValue).sum() / totalAvgs.size() : 1;
    }

    private int getWeightedAverage(double firstTotal, double otherTotals) {
        return (int) (Math.round( ((firstTotal * 70) + (otherTotals * 30)) / (100)));
    }
}
