package ru.ulstu;

import java.util.Arrays;
import java.util.Random;

public class App {
    private static final int SIZE = 10;
    private static final int FROM_INDEX = 0;
    private static final int TO_INDEX = SIZE;
    public static void main(String[] args) {
        long seed = 1457521330571L;
        long ta;
        long tb;
        System.currentTimeMillis();
        Random random = new Random(seed);
        Integer[] array1 = createRandomIntegerArray(SIZE, random);
        Integer[] array2 = array1.clone();
        Integer[] array3 = array1.clone();

        //// java.util.Arrays.sort
        ta = System.currentTimeMillis();
        Arrays.sort(array1, FROM_INDEX, TO_INDEX);
        tb = System.currentTimeMillis();

        System.out.println("java.util.Arrays.sort() in " + (tb - ta) + " ms. Sorted: " + isSorted(array1, FROM_INDEX, TO_INDEX));

        //// java.util.Arrays.parallelSort
        ta = System.currentTimeMillis();
        Arrays.parallelSort(array2, FROM_INDEX, TO_INDEX);
        tb = System.currentTimeMillis();

        System.out.println("java.util.Arrays.parallelSort() " + (tb - ta) + " ms. Sorted: " + isSorted(array2, FROM_INDEX, TO_INDEX));

        //// BottomUpMergesort.sort
        ta = System.currentTimeMillis();
        ParallelMergesort.sort(array3, 3);
        tb = System.currentTimeMillis();

        System.out.println("ParallelMergesort.sort() " + (tb - ta) + " ms. Sorted: " + isSorted(array3, FROM_INDEX, TO_INDEX));

        System.out.println("Arrays identical: " + (Arrays.equals(array1, array2) && Arrays.equals(array1, array3)));
    }

    public static <T extends Comparable<? super T>> boolean isSorted(T[] array, int fromIndex, int toIndex) {
        for (int i = fromIndex; i < toIndex - 1; ++i)
            if (array[i].compareTo(array[i + 1]) > 0) return false;
        return true;
    }

    static <T> boolean arraysIdentical(T[] arr1, T[] arr2) {
        if (arr1.length != arr2.length) return false;

        for (int i = 0; i < arr1.length; ++i)
            if (arr1[i] != arr2[i]) return false;

        return true;
    }

    static Integer[] createRandomIntegerArray(int size, Random random) {
        Integer[] ret = new Integer[size];

        for (int i = 0; i < size; ++i) ret[i] = random.nextInt(size);

        return ret;
    }
}
