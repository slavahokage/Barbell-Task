package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Input elements of array from space delimiter");
        Scanner in = new Scanner(System.in);
        String[] data = in.nextLine().split(" ");

        int[] barbellComponents = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            barbellComponents[i] = Integer.parseInt(data[i]);
        }

        BarbellService barbellService = new BarbellService(barbellComponents);
        System.out.println("Max allowable weight = " + barbellService.getTheMaximumAllowableWeight());
    }
}
