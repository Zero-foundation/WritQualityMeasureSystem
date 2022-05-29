package com.loading.neo4j.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GetLevelUtil {
    private double[] list1 = new double[59945];
    private double[] list2 = new double[59945];
    private double[] list3 = new double[59945];
    private double[] list4 = new double[59945];
    private double[] list5 = new double[59945];
    private double[] list6 = new double[59945];
    private double[] list7 = new double[59945];

    public GetLevelUtil() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/csv/sortedScore.csv")));
            List<List<Double>> d_Matrix = new ArrayList<>();
            String str;
            while ((str = br.readLine()) != null) {
                List<Double> row = new ArrayList<>();
                for (String d_str : str.split(",")) {
                    double num = Double.parseDouble(d_str);
                    row.add(num);
                }
                d_Matrix.add(row);
            }
            for (int i = 0; i < d_Matrix.get(0).size(); i++) {
                list1[i] = d_Matrix.get(0).get(i);
            }
            for (int i = 0; i < d_Matrix.get(0).size(); i++) {
                list2[i] = d_Matrix.get(1).get(i);
            }
            for (int i = 0; i < d_Matrix.get(0).size(); i++) {
                list3[i] = d_Matrix.get(2).get(i);
            }
            for (int i = 0; i < d_Matrix.get(0).size(); i++) {
                list4[i] = d_Matrix.get(3).get(i);
            }
            for (int i = 0; i < d_Matrix.get(0).size(); i++) {
                list5[i] = d_Matrix.get(4).get(i);
            }
            for (int i = 0; i < d_Matrix.get(0).size(); i++) {
                list6[i] = d_Matrix.get(5).get(i);
            }
            for (int i = 0; i < d_Matrix.get(0).size(); i++) {
                list7[i] = d_Matrix.get(6).get(i);
            }

//
//            list1=(Double[])d_Matrix.get(0).toArray();
//            list2=(Double[])d_Matrix.get(1).toArray();
//            list3=(Double[])d_Matrix.get(2).toArray();
//            list4=(Double[])d_Matrix.get(3).toArray();
//            list5=(Double[])d_Matrix.get(4).toArray();
//            list6=(Double[])d_Matrix.get(5).toArray();
//            list7=(Double[])d_Matrix.get(6).toArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLevel(double target, int type) {
        double[] list = new double[0];
        switch (type) {
            case 1:
                list = list1;
                break;
            case 2:
                list = list2;
                break;
            case 3:
                list = list3;
                break;
            case 4:
                list = list4;
                break;
            case 5:
                list = list5;
                break;
            case 6:
                list = list6;
                break;
            case 7:
                list = list7;
                break;
            default:
                return 0;
        }
        if (target == 100.0) {
            return list.length;
        }
        int l = 0, r = list.length;
        int mid = 0;
        while (l < r) {
            mid = (l + r) / 2;
            if (target > list[mid]) {
                if (mid == list.length - 1) return list.length;
                if (target < list[mid + 1]) return mid;
                else l = mid + 1;
            } else r = mid - 1;
        }
        return mid;
    }


}
