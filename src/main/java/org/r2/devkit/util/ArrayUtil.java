package org.r2.devkit.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ruan4261
 */
public final class ArrayUtil {

    private ArrayUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] concat(Class<T> clazz, T[] a1, T[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        T[] arr = (T[]) Array.newInstance(clazz, a1.length + a2.length);
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static int[] concat(int[] a1, int[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        int[] arr = new int[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static char[] concat(char[] a1, char[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        char[] arr = new char[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static byte[] concat(byte[] a1, byte[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        byte[] arr = new byte[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static boolean[] concat(boolean[] a1, boolean[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        boolean[] arr = new boolean[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static float[] concat(float[] a1, float[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        float[] arr = new float[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static double[] concat(double[] a1, double[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        double[] arr = new double[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static short[] concat(short[] a1, short[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        short[] arr = new short[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    public static long[] concat(long[] a1, long[] a2) {
        Assert.notNull(a1);
        Assert.notNull(a2);
        long[] arr = new long[a1.length + a2.length];
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a1.length, a2.length);
        return arr;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] delRepeat(Class<T> clazz, T[] a) {
        Assert.notNull(a);
        Set<T> set = new HashSet<>(Arrays.asList(a));
        return set.toArray((T[]) Array.newInstance(clazz, 0));
    }
}
