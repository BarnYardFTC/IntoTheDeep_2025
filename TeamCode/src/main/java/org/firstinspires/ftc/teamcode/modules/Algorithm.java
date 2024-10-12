package org.firstinspires.ftc.teamcode.modules;
/*
    In this class all of the bit more complex calculations
    and algorithms of the project are executed.
 */
public class Algorithm {


    public static int closestTo(double[] arr, double num){
        /*
        Find the index of the number out of an array 'arr' which is the closest in it's value
        to a specified number 'num'.
        Assumptions:
        1. arr.length >= 2

        @param arr: A given array of type double.
        @param num: A given number of type double.
        @return: the number which is closest to 'num' in it's value out of 'arr'.
        Example:
        arr: [1,2,3,4]
        num: 2.3
        return: 1
         */
        int closest_i = 0;
        double closest = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i]-num) < Math.abs(closest-num)){
                closest = arr[i];
                closest_i = i;
            }
        }
        return closest_i;
    }
}