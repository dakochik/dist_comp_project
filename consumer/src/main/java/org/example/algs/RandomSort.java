package org.example.algs;

import java.util.Optional;

public class RandomSort {

    public static long restriction = 1_000_000;

    public static Optional<double[]> RSort(double[] i) {
        int counter = 0;
        while (!isSorted(i) && counter < restriction) {
            shuffle(i);
            counter++;
        }
        if (counter >= restriction && !isSorted(i)){
            return Optional.empty();
        }
        else {
            return Optional.of(i);
        }
    }

    private static void shuffle(double[] i) {
        for (int x = 0; x < i.length; ++x) {
            int index1 = (int) (Math.random() * i.length),
                    index2 = (int) (Math.random() * i.length);
            double a = i[index1];
            i[index1] = i[index2];
            i[index2] = a;
        }
    }

    private static boolean isSorted(double[] i) {
        for (int x = 0; x < i.length - 1; ++x) {
            if (i[x] > i[x + 1]) {
                return false;
            }
        }
        return true;
    }
}
