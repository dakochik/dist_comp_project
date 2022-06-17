package org.example.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class SomeAlgs {
    public static double[] BubbleSort(int len, double[] arr){
        for (int i = 0; i<len; i++){
            for (int j = 0; j<len-i; j++){
                if (arr[i] > arr[j]){
                    double tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return arr;
    }

    public static double[] Reverse(int len, double[] arr){
        double[] res = new double[len];
        for (int i = 0; i< len; i++){
            res[i] = arr[len-i-1];
        }
        return res;
    }

    //todo rethink about KMP
    // Function to implement the KMP algorithm
    public static Optional<Integer> KMP(String text, String pattern)
    {
        // base case 1: pattern is null or empty
        if (pattern == null || pattern.length() == 0)
        {
            return Optional.empty();
        }

        // base case 2: text is NULL, or text's length is less than that of pattern's
        if (text == null || pattern.length() > text.length())
        {
            return Optional.empty();
        }

        char[] chars = pattern.toCharArray();

        // next[i] stores the index of the next best partial match
        int[] next = new int[pattern.length() + 1];
        for (int i = 1; i < pattern.length(); i++)
        {
            int j = next[i + 1];

            while (j > 0 && chars[j] != chars[i]) {
                j = next[j];
            }

            if (j > 0 || chars[j] == chars[i]) {
                next[i + 1] = j + 1;
            }
        }

        for (int i = 0, j = 0; i < text.length(); i++)
        {
            if (j < pattern.length() && text.charAt(i) == pattern.charAt(j))
            {
                if (++j == pattern.length()) {
                    //The pattern occurs with shift (i - j + 1)
                    return Optional.of(i - j + 1);
                }
            }
            else if (j > 0)
            {
                j = next[j];
                i--;    // since `i` will be incremented in the next iteration
            }
        }

        return Optional.empty();
    }

    public static Optional<double[]> strToDArr (String s){
        String[] sarr = s.split(";");
        ArrayList<Double> darr = new ArrayList<>();
        for (String t : sarr){
            double tmp = 0;
            try{
            tmp = Double.parseDouble(t);}
            catch (NumberFormatException e){
                continue;
            }
            darr.add(tmp);
        }
        if (darr.isEmpty()) {
            return Optional.empty();
        }
        double[] res = new double[darr.size()];
        for (int i = 0; i<darr.size();i++){
            res[i] = darr.get(i);
        }
        return Optional.of(res);
    }
}

