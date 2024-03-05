package com.geeksforless.tdd_web.math;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Map;

public class FunctionCalculator {
    private static final double BOUND_X = 1.4;
    private  Coefficient coefficient;

    private double beginSegmentX;
    private double endSegmentX;
    private double step;

    public FunctionCalculator() {
        beginSegmentX = 0;
        endSegmentX = 2.0;
        step = 0.002;
        coefficient = new Coefficient(2.7, -0.3, 4);
    }

    public double getBeginSegmentX() {
        return beginSegmentX;
    }

    public void setBeginSegmentX(double beginSegmentX) {
        this.beginSegmentX = beginSegmentX;
    }

    public double getEndSegmentX() {
        return endSegmentX;
    }

    public void setEndSegmentX(double endSegmentX) {
        this.endSegmentX = endSegmentX;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }



    public Coefficient getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double first, double second, double free) {
        this.coefficient = new Coefficient(first, second, free);
    }



    public double calculateFunction(double x) {
        double result = coefficient.first() / x + Math.hypot(x, 1);
        if (x > BOUND_X) {
            result = (coefficient.first() + coefficient.second() * x) / Math.hypot(x, 1);
        } else if (x < BOUND_X) {
            result = coefficient.first() * Math.pow(x, 2) + coefficient.second() * x + coefficient.free();
        }
        return result;
    }

    public double calculatePointsInSegmentByStep(double begin, double end, double step) {
        return new Segment(begin, end).calculatePointsInSegmentByStep(step);
    }

    public Point[] createPointsInGivenSegmentWithGivenStep(double begin, double end, double step) {
        int size = (int) new Segment(begin, end).calculatePointsInSegmentByStep(step);
        Point[] points = new Point[size];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(begin, calculateFunction(begin));
            begin += step;
        }
        return points;
    }

    public double getMaxValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(beginSegmentX, endSegmentX, step))
                .getMax();
    }

    public double getMinValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(beginSegmentX, endSegmentX, step))
                .getMin();
    }

    public double getTotalValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(beginSegmentX, endSegmentX, step))
                .getSum();
    }

    public double getAverageValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(beginSegmentX, endSegmentX, step))
                .getAverage();
    }

    public Map<Integer, Point> findMaxPointWithIndex() {
        var points = createPointsInGivenSegmentWithGivenStep(beginSegmentX, endSegmentX, step);
        for (int i = 0; i < points.length; i++) {
            if (getMaxValue() == points[i].y()) {
                return Map.of(i, points[i]);
            }
        }
        return Map.of();
    }

    public Map<Integer, Point> findMinPointWithIndex() {
        var points = createPointsInGivenSegmentWithGivenStep(beginSegmentX, endSegmentX, step);
        for (int i = 0; i < points.length; i++) {
            if (getMinValue() == points[i].y()) {
                return Map.of(i, points[i]);
            }
        }
        return Map.of();
    }

    private DoubleSummaryStatistics findAllNeededInformation(Point[] points) {
        return Arrays.stream(points)
                .mapToDouble(Point::y)
                .summaryStatistics();
    }
}
