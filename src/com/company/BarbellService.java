package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Service to find the maximum allowable weight
 * that weightlifters can lift with the allowable
 * number of components.
 *
 * Service use bitmask to check all possible combinations of array indexs to find suitable weight on sides
 *
 */
public class BarbellService {

    /** source array of components */
    private int[] barbellComponents;

    /** weight on one side of the barbell */
    private int weightOnOneSide;

    /** pool of suitable component options */
    private List<String> binaryValuesPull = new ArrayList<>();

    /** flag when we can't place components of barbell */
    private boolean noPossibilityToPlaceAtAll = false;

    public BarbellService(int[] barbellComponents) {
        this.barbellComponents = barbellComponents;
    }

    /**
     * The method searches for max possible width
     */
    public int getTheMaximumAllowableWeight() {

        if (barbellComponents.length < 2){
            return 0;
        }

        int possibleCombinations = findAllPossibleCombinationsOfComponents();
        getMaximumWeightOnOneSide();
        findProbableWeight(possibleCombinations);
        while (!noPossibilityToPlaceAtAll) {
            findProbableWeight(possibleCombinations);

            if (checkIfCanPlaceThisWeightOnBothSides()) {
                return weightOnOneSide;
            }

            decreaseWeightOnOneSide();
        }

        return weightOnOneSide;
    }

    /**
     * The method searches for max available weight from one side of barbell
     */
    private void getMaximumWeightOnOneSide() {
        weightOnOneSide = IntStream.of(barbellComponents).sum() / 2;
    }

    /**
     * The method searches for all possible combinations of array index.
     * Use formula 2^n-1. n - quantity of array index.
     */
    private int findAllPossibleCombinationsOfComponents() {
        return (int) Math.pow(2, barbellComponents.length) - 1;
    }

    /**
     * Decrease weight
     */
    private void decreaseWeightOnOneSide() {
        weightOnOneSide--;

        if (weightOnOneSide <= 0) {
            noPossibilityToPlaceAtAll = true;
        }

        binaryValuesPull.clear();
    }

    /**
     * The method searches for a probable weight
     * of components for the transmitted combinations
     */
    private void findProbableWeight(int combinations) {
        int one = 0;
        for (int i = 0; i < combinations; i++) {
            checkIfTheCombinationMatchesToWeight(Integer.toBinaryString(++one));
        }
    }

    /**
     * The method collect bitmasks that match to barbellComponents indexs and
     * give needed weight
     */
    private void checkIfTheCombinationMatchesToWeight(String binaryNumber) {
        int sum = 0;

        int binaryNumberTest = Integer.parseInt(binaryNumber,2);

        for (int i = 0, j = barbellComponents.length - 1; i < binaryNumber.length(); i++, j--) {
            int bitShift = 1<<i;
            if ((binaryNumberTest & bitShift) == bitShift) {
                sum = sum + barbellComponents[j];
            }
        }

        if (sum == weightOnOneSide) {
            binaryValuesPull.add(binaryNumber);
        }
    }

    /**
     * The method check if we can place given weight from different sides
     */
    private boolean checkIfCanPlaceThisWeightOnBothSides() {

        if (binaryValuesPull.size() < 2) {
            return false;
        }

        for (int i = 0; i < binaryValuesPull.size(); i++) {
            int binaryValue = Integer.parseInt(binaryValuesPull.get(i), 2);
            for (int j = i + 1; j < binaryValuesPull.size(); j++) {
                int anotherBinaryValue = Integer.parseInt(binaryValuesPull.get(j), 2);
                if ((binaryValue & anotherBinaryValue) == 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
