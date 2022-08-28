package com.example.springboottest.util;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.util
 * @Author: lujie
 * @Date: 2022/7/28 10:52
 * @Description:
 */
public class Fraction implements Comparable {
    // 分子

    private int numerator;

// 分母

    private int denominator;

// 默认构造函数

    public Fraction() {

    }

// 该构造函数对分子和分母进行初始化

    public Fraction(int n, int d) {

        setFraction(n, d);

    }

// 该构造函数支持将双精度数转换为相应的分数

    public Fraction(double d) {

        convertToFraction(d);

    }

// 该函数可以将双精度数转换为相应的分数

    private void convertToFraction(double d) {

        int decimalCount = 1;

// 求双精度数的字符长度

        int dLen = String.valueOf(d).length();

// 不断的将双精度数累乘10，直至转换为整数为止

        for (int i = 0; i < dLen; i++) {

            d = d * 10;

            decimalCount *= 10;

        }

// 分子为最终的整数乘积结果

        numerator = (int) d;

// 分母为10的累乘结果

        denominator = decimalCount;

// 约分

        reduct();

    }

// 分子的设置函数，并且约分

    public void setNumerator(int n) {

        numerator = n;

        reduct();

    }

// 分子的读取函数

    public int getNumerator() {

        return numerator;

    }

// 分母的设置函数，并且约分

    public void setDenominator(int d) {

// 检查分母是否为0

        if (d == 0) {
            denominator = 1; //建议采用异常
        } else {
            denominator = d;
        }

        reduct();

    }

// 分母的读取函数

    public int getDenominator() {

        return denominator;

    }

// 分数的设置函数，并且约分

    public void setFraction(int n, int d) {

        setNumerator(n);

        setDenominator(d);

        reduct();

    }

// 格式化字符信息输出

    @Override
    public String toString() {

// 如果为负分数，则将负号提前显示，分子和分母保留为正数，否则分子和分母皆取正数

        if (numerator * denominator < 0) {
            return "-" + Math.abs(numerator) + "/" + Math.abs(denominator);
        } else {
            return Math.abs(numerator) + "/" + Math.abs(denominator);
        }

    }

// 利用对应小数值是否相同来判断分数是否相同

    public boolean equals(Fraction f) {

        if (numerator == f.numerator && denominator == f.denominator) {
            return true;
        } else {
            return false;
        }

    }

// 利用对应小数值的大小来判断分数的大小

    public boolean isGreater(Fraction f) {

        if ((double) numerator / denominator > (double) f.numerator
                / f.denominator) {
            return true;
        } else {
            return false;
        }

    }

// 约分函数

    private void reduct() {

// 求分子和分母的最小值

        int minValue = Math.min(Math.abs(numerator), Math.abs(denominator));

// 将小于分子和分母的最小值的数值，从大到小去除分子和分母，如果能够同时被整除，则以此数值约分并退出

        for (int i = minValue; i >= 1; i--) {

            if (numerator % i == 0 && denominator % i == 0) {

                numerator = numerator / i;

                denominator = denominator / i;

                break;

            }

        }

    }

// 返回分数相加的结果，并且约分

    public Fraction add(Fraction f) {

        Fraction fraction = new Fraction();

        fraction.numerator = numerator * f.denominator + f.numerator

                * denominator;

        fraction.denominator = denominator * f.denominator;

        fraction.reduct();

        return fraction;

    }

// 返回分数相减的结果，并且约分

    public Fraction minus(Fraction f) {

        Fraction fraction = new Fraction();

        fraction.numerator = numerator * f.denominator - f.numerator

                * denominator;

        fraction.denominator = denominator * f.denominator;

        fraction.reduct();

        return fraction;

    }

// 返回分数相乘的结果，并且约分

    public Fraction multiply(Fraction f) {

        Fraction fraction = new Fraction();

        fraction.numerator = numerator * f.numerator;

        fraction.denominator = denominator * f.denominator;

        fraction.reduct();

        return fraction;

    }

// 返回分数相除的结果，并且约分

    public Fraction divide(Fraction f) {

        Fraction fraction = new Fraction();

        fraction.numerator = numerator * f.denominator;

        fraction.denominator = denominator * f.numerator;

        fraction.reduct();

        return fraction;

    }

// 支持两个分数类变量的比较，以实现分数集合的查找功能

    @Override
    public int compareTo(Object o) {

        Fraction f = (Fraction) o;

// 利用对应小数的大小来比较分数的大小

        if ((double) numerator / denominator > (double) f.numerator

                / f.denominator)

            return 1;

        else if ((double) numerator / denominator < (double) f.numerator

                / f.denominator) {
            return -1;
        } else {
            return 0;
        }

    }

// 根据对应小数是否相同来判断分数是否相同

    public boolean equals(Object obj) {

        Fraction f = (Fraction) obj;

        if (Math.abs((double) numerator / denominator - (double) f.numerator

                / f.denominator) < 0.00001)

            return true;

        return false;

    }

// 相同数值的分数返回相同的哈希码

    public int hashcode() {

        String str = String.valueOf((double) numerator / denominator);

        return str.hashCode();

    }

}