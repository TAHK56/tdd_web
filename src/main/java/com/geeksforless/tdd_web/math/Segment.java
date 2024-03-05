package com.geeksforless.tdd_web.math;

public record Segment(double begin, double end) {
    public double calculatePointsInSegmentByStep(double step) {
        return  (end - begin) / step + 1;
    }
}
