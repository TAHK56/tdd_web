package com.geeksforless.tdd_web.bean;

import com.geeksforless.tdd_web.math.FunctionCalculator;
import com.geeksforless.tdd_web.math.Point;
import com.geeksforless.tdd_web.type.FunctionOperation;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@SessionScoped
public class FunctionCalculatorBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    private final transient FunctionCalculator calculator = new FunctionCalculator();
    private double firstCoefficient;
    private double secondCoefficient;
    private double freeCoefficient;
    private  double begin;
    private  double end;
    private  double step;
    private double numberCalculate;

    private double result;
    private transient Map<Integer, Point> point;

    private transient Point[] points;

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public Map<Integer, Point> getPoint() {
        return point;
    }

    public void setPoint(Map<Integer, Point> point) {
        this.point = point;
    }

    private String calculationType;

    private final Set<SelectItem> operationList;

    public FunctionCalculatorBean() {
        numberCalculate = 0;
        point = Map.of();
        firstCoefficient = calculator.getCoefficient().first();
        secondCoefficient = calculator.getCoefficient().second();
        freeCoefficient = calculator.getCoefficient().free();
        begin = calculator.getBeginSegmentX();
        end = calculator.getEndSegmentX();
        step = calculator.getStep();

        fillFieldsCalculator();
        operationList = Stream.of(FunctionOperation.values())
                .map(FunctionOperation::name)
                .map(String::toLowerCase)
                .map(SelectItem::new)
                .collect(Collectors.toSet());
    }

    public void performCalculation() {
        calculator.setCoefficient(firstCoefficient, secondCoefficient, freeCoefficient);
        switch (getCalculationType()) {
            case "calculate" -> setResult(calculator.calculateFunction(numberCalculate));
            case "calculate_points_in_segment_by_step" ->
                    setResult(calculator.calculatePointsInSegmentByStep(begin, end,step));
            case "get_max_value" ->  setResult(calculator.getMaxValue());
            case "get_min_value" ->  setResult(calculator.getMinValue());
            case "get_average_value" -> setResult(calculator.getAverageValue());
            case "get_total_value" -> setResult(calculator.getTotalValue());
            case "find_max_point_with_index" -> setPoint(calculator.findMaxPointWithIndex());
            case "find_min_point_with_index" -> setPoint(calculator.findMinPointWithIndex());
            case "create_points_in_given_segment_with_given_step" ->
                    setPoints(calculator.createPointsInGivenSegmentWithGivenStep(begin, end, step));
            default -> setResult(0);
        }

    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    public Set<SelectItem> getOperationList() {
        return operationList;
    }

    public double getNumberCalculate() {
        return numberCalculate;
    }

    public void setNumberCalculate(double numberCalculate) {
        this.numberCalculate = numberCalculate;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }


    public double getFirstCoefficient() {
        return firstCoefficient;
    }

    public void setFirstCoefficient(double firstCoefficient) {
        this.firstCoefficient = firstCoefficient;
    }

    public double getSecondCoefficient() {
        return secondCoefficient;
    }

    public void setSecondCoefficient(double secondCoefficient) {
        this.secondCoefficient = secondCoefficient;
    }

    public double getFreeCoefficient() {
        return freeCoefficient;
    }

    public void setFreeCoefficient(double freeCoefficient) {
        this.freeCoefficient = freeCoefficient;
    }

    public double getBegin() {
        return begin;
    }

    public double getEnd() {
        return end;
    }

    public double getStep() {
        return step;
    }

    public void setBegin(double begin) {
        this.begin = begin;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public void setStep(double step) {
        this.step = step;
    }

    private void fillFieldsCalculator() {
        calculator.setStep(step);
        calculator.setBeginSegmentX(begin);
        calculator.setEndSegmentX(end);
        calculator.setCoefficient(firstCoefficient, secondCoefficient, freeCoefficient);
    }

}

