package com.example.springboottest.util;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.*;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.controller
 * @Author: lujie
 * @Date: 2021/12/28 17:00
 * @Description:
 */
public class ZipUtil {
    // 压缩
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1" );
    }

    // 解压缩
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes("ISO-8859-1" ));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
        return out.toString();
    }

//    static {
//        System.loadLibrary("Login");
//    }

    // 测试方法
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        Cal_sta cal = new Cal_sta();
        WeightedObservedPoints obs = new WeightedObservedPoints();
        obs.add(1, 57.33);
        obs.add(2, 21.03);
        obs.add(3, 58.40);
        obs.add(4, 56.40);
        obs.add(5, 48.80);
        obs.add(6, 38.63);
        obs.add(7, 48.80);
        obs.add(8, 58.40);
        obs.add(9, 49.93);
        obs.add(10, 50.43);
        obs.add(11, 53.70);
        obs.add(12, 40.83);
        obs.add(13, 55.60);
        obs.add(14, 57.17);
        obs.add(15, 54.97);
        obs.add(16, 58.47);
        obs.add(17, 51.93);
        obs.add(18, 55.27);
        obs.add(19, 44.43);
        obs.add(20, 43.47);
        obs.add(21, 44.43);
        obs.add(22, 30.43);
        obs.add(23, 58.97);
        obs.add(24, 46.30);
        obs.add(25, 51.03);
        obs.add(26, 21.23);
        obs.add(27, 58.53);
        obs.add(28, 59.50);
        obs.add(29, 44.53);
        obs.add(30, 36.83);
        obs.add(31, 56.37);
        obs.add(32, 51.93);
        obs.add(22, 36.47);
        double[] parameters = GaussianCurveFitter.create().fit(obs.toList());
//        System.out.println("--------------样本曲线系数--------------" );
//        for (double i : parameters) {
//
//            System.out.println(i);
//
//        }
//        System.out.println("---------------------------------------" );
        double[] a = {57.33,
                21.03,
                58.40,
                56.40,
                48.80,
                38.63,
                48.80,
                58.40,
                49.93,
                50.43,
                53.70,
                40.83,
                55.60,
                57.17,
                54.97,
                58.47,
                51.93,
                55.27,
                44.43,
                43.47,
                44.43,
                30.43,
                58.97,
                46.30,
                51.03,
                21.23,
                58.53,
                59.50,
                44.53,
                36.83,
                56.37,
                51.93,
                36.47
        };
        double sum=0D;
        List<Double> data = new ArrayList<>();
        for (double v : a) {
            sum+=v;
            data.add(v);
        }
        System.out.println("------------------------t10-------------------------");
        Mean mean = new Mean();
        System.out.println("T10平均值：" + mean.evaluate(a));

        StandardDeviation sd = new StandardDeviation();
        System.out.println("T10标准差:" + sd.evaluate(a));

        NormalDistribution normalDistributioin = new NormalDistribution(0,100);
        double v1 = normalDistributioin.cumulativeProbability(sum);
        System.out.println("显著性："+v1);
        sum=0D;

        Collections.sort(data);
        System.out.println("T10最大值：" + data.get(data.size() - 1));
        System.out.println("T10中位数：" + median(data));

//        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
//        double probability = normalDistribution.probability(-3, 3);
//        System.out.println("T10分布测试：" + probability);

        System.out.println("T10最小值：" + data.get(0));


        System.out.println(" ");
        System.out.println("------------------------t14-------------------------");
        double[] t14 = {
                46.77,
                27.13,
                65.47,
                56.50,
                69.40,
                67.13,
                38.70,
                61.27,
                65.77,
                58.23,
                60.10,
                38.67,
                48.23,
                55.67,
                55.77,
                56.20,
                61.17,
                81.40,
                57.47,
                54.77,
                61.53,
                89.50,
                70.93,
                79.40,
                51.57,
                30.30,
                70.63,
                44.30,
                75.13,
                58.07,
                84.27,
                70.47,
                42.23
        };

        List<Double> dataT14 = new ArrayList<>();
        for (double v : t14) {
            sum+=v;
            dataT14.add(v);
        }
        double evaluate = sd.evaluate(t14);
        System.out.println("T14平均值：" + mean.evaluate(t14));
        double v2 = normalDistributioin.cumulativeProbability(23.1);
        System.out.println("显著性："+v2);

        System.out.println("T14标准差:" + sd.evaluate(t14));
        Collections.sort(dataT14);
        System.out.println("T14最大值：" + dataT14.get(dataT14.size() - 1));
        System.out.println("T14中位数：" + median(dataT14));
        System.out.println("T14最小值：" + dataT14.get(0));


        System.out.println(" ");
        System.out.println("------------------------t28-------------------------");
        double[] t28 = {
                49.10,
                27.63,
                67.67,
                57.27,
                75.83,
                65.87,
                43.53,
                61.97,
                60.03,
                67.77,
                66.40,
                33.53,
                53.57,
                62.90,
                75.70,
                68.77,
                71.70,
                72.70,
                58.97,
                48.83,
                61.90,
                79.87,
                65.23,
                79.47,
                68.77,
                30.17,
                66.63,
                51.80,
                59.73,
                53.73,
                82.20,
                76.80,
                44.10
        };
        List<Double> dataT28 = new ArrayList<>();
        for (double v : t28) {
            dataT28.add(v);
        }
        System.out.println("T28平均值：" + mean.evaluate(t28));

        System.out.println("T28标准差:" + sd.evaluate(t28));

        Collections.sort(dataT28);
        System.out.println("T28最大值：" + dataT28.get(dataT28.size() - 1));
        System.out.println("T28中位数：" + median(dataT28));

        System.out.println("T28最小值：" + dataT28.get(0));
    }

    private static double median(List<Double> total) {
        double j = 0;
        //集合排序
        Collections.sort(total);
        int size = total.size();
        if (size % 2 == 1) {
            j = total.get((size - 1) / 2);
        } else {
            //加0.0是为了把int转成double类型，否则除以2会算错
            j = (total.get(size / 2 - 1) + total.get(size / 2) + 0.0) / 2;
        }
        return j;
    }

    public static double[][] sum(double[] x, double[] y) {
        double[][] final23 = new double[2][x.length];
        for (int i = 0; i < final23[i].length; i++) {

            final23[1][i] = x[i];
            final23[2][i] = y[i];
        }
        return final23;
    }

    public static void rank(double[] a, double[] r) {

        if (r.length < a.length) {
            throw new IllegalArgumentException("length of rank array cannot be " +
                    "less than the number of objects" );
        }

        for (int i = 0; i < a.length; i++) {

            r[i] = 0;

        }

        for (int i = 1; i < a.length; i++) {

            for (int j = 0; j < i; j++) {

                if (a[j] <= a[i]) {

                    r[i]++;

                } else {

                    r[j]++;

                }

            }

        }

    }
}
