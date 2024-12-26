package com.example;

import java.util.concurrent.Executors;

class Main {
    public static void main(String[] args) {
        int[][] matrixA = {
                { 2, 4, 6 },
                { 3, 5, 7 },
                { 1, 7, 9 }
        };

        int[][] matrixB = {
                { 4, 0, 0 },
                { 0, 4, 1 },
                { 9, 2, 1 }
        };

        System.out.println("Matrix A:");
        MatrixCalculation.displayMatrix(matrixA, 3, 3);

        System.out.println("Matrix B:");
        MatrixCalculation.displayMatrix(matrixB, 3, 3);

        int[][] matrixC = MatrixCalculation.multiplyMatrices(matrixA, matrixB, 3, 3, 3,
                Executors.newVirtualThreadPerTaskExecutor());

        System.out.println("Matrix C (multiplication result):");
        MatrixCalculation.displayMatrix(matrixC, 3, 3);
    }
}
