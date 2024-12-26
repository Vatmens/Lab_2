package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class MatrixCalculation {
    public static void displayMatrix(int[][] matrix, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(matrix[row][col] + "\t");
            }
            System.out.println();
        }
    }

    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2, int rows, int common, int cols,
                                           ExecutorService executor) {

        int[][] resultMatrix = new int[rows][cols];
        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger colIndex = new AtomicInteger(0);

        int totalElements = rows * cols;
        AtomicInteger processedElements = new AtomicInteger(0);

        for (int task = 0; task < totalElements; task++) {
            executor.submit(() -> {
                int currentRow, currentCol;

                synchronized (rowIndex) {
                    currentRow = rowIndex.get();
                    currentCol = colIndex.getAndIncrement();

                    if (currentCol == cols) {
                        colIndex.set(0);
                        currentCol = colIndex.getAndIncrement();
                        currentRow = rowIndex.incrementAndGet();
                    }
                }

                if (currentRow < rows && currentCol < cols) {
                    int sum = 0;
                    for (int i = 0; i < common; i++) {
                        sum += matrix1[currentRow][i] * matrix2[i][currentCol];
                    }
                    resultMatrix[currentRow][currentCol] = sum;

                    if (processedElements.incrementAndGet() == totalElements) {
                        executor.shutdown();
                    }
                }
            });
        }

        while (!executor.isTerminated()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return resultMatrix;
    }
}
