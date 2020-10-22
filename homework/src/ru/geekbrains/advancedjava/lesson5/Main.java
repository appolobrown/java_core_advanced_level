package ru.geekbrains.advancedjava.lesson5;

import java.util.Arrays;

public class Main {
    private static final int SIZE = 10000000;
    private static final int H = SIZE / 2;

    public static void main(String[] args) {
        float[] array = initArray();
        float[] array2 = initArray();
        float[] firstHalf = new float[H];
        float[] seccondHalf = new float[H];

        long beforeSync = System.currentTimeMillis();
        countSync(array);
        long afterSyn = System.currentTimeMillis();
        System.out.println(afterSyn - beforeSync);

        try {
            long before = System.currentTimeMillis();
            countAsync(array2, firstHalf, seccondHalf);
            long after = System.currentTimeMillis();
            System.out.println(after - before);
            System.out.println(Arrays.equals(array, array2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static float[] initArray() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);
        return arr;
    }

    private static void countSync(float[] floats) {
        for (int i = 0; i < floats.length; i++) {
            floats[i] = countWithFormula(i, floats[i]);
        }
    }

    private static void countAsync(float[] array, float[] firstHalf, float[] seccondHalf) throws InterruptedException {
        System.arraycopy(array, 0, firstHalf, 0, H);
        System.arraycopy(array, H, seccondHalf, 0, H);
        Thread first = new Thread(new AsyncCounter(firstHalf, false));
        Thread second = new Thread(new AsyncCounter(seccondHalf, true));
        first.start();
        second.start();
        first.join();
        second.join();
        System.arraycopy(firstHalf, 0, array, 0, H);
        System.arraycopy(seccondHalf, 0, array, H, H);
    }

    private static float countWithFormula(int index, float element) {
        return (float) (element
                * Math.sin(0.2f + index / 5f)
                * Math.cos(0.2f + index / 5f)
                * Math.cos(0.4f + index / 2f));
    }

    private static class AsyncCounter implements Runnable {

        float[] floats;
        boolean appendSize;

        AsyncCounter(float[] floats, boolean appendSize) {
            this.floats = floats;
            this.appendSize = appendSize;
        }

        @Override
        public void run() {
            int indexWithSizeAppend = appendSize ? floats.length : 0;
            for (int i = 0; i < floats.length; i++, indexWithSizeAppend++) {
                floats[i] = countWithFormula(indexWithSizeAppend, floats[i]);
            }
        }
    }
}
