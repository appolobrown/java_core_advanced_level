package ru.geekbrains.advancedjava.lesson2;

public class Main {

    public static final String INPUT_STRING = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
    public static final int MATRIX_SIZE = 4;

    public static void main(String[] args) {

        try {
            String[][] result = getArrayFromString(INPUT_STRING);
            System.out.println(getHalfSumOfMatrixElements(result));
        } catch (InvalidMatrixSizeException | NanElementException e) {
            e.printStackTrace();
        }

    }

    private static String[][] getArrayFromString(String string) throws InvalidMatrixSizeException {
        String[][] result = new String[MATRIX_SIZE][MATRIX_SIZE];
        String[] lines = string.split("\n");
        if (lines.length != 4)
            throw new InvalidMatrixSizeException("matrix has " + lines.length + " rows. Expect " + MATRIX_SIZE);
        for (int i = 0; i < lines.length; i++) {
            String[] elements = lines[i].split(" ");
            if (elements.length != 4)
                throw new InvalidMatrixSizeException("matrix has " + elements.length + " coloumns. Expect " + MATRIX_SIZE);
            for (int j = 0; j < elements.length; j++) {
                result[i][j] = elements[j];
            }
        }
        return result;
    }

    private static int getHalfSumOfMatrixElements(String[][] matrix) throws NanElementException {
        int sum = 0;
        for (String[] strings : matrix) {
            for (String string : strings) {
                try {
                    sum += Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    throw new NanElementException(string + " is not a number");
                }
            }
        }

        return sum / 2;
    }

    private static class InvalidMatrixSizeException extends Exception {
        public InvalidMatrixSizeException(String message) {
            super(message);
        }
    }

    private static class NanElementException extends Exception {
        public NanElementException(String message) {
            super(message);
        }
    }
}
