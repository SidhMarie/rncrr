package rncrr.llt.model.utils;

import rncrr.llt.model.bean.Points;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sidh on 24.03.2015.
 */
public class DspUtil {

    public static int[] rate2 = {2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,
            65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,
            33554432,67108864,134217728,268435456,536870912,1073741824};

    public static List<Double> fillList(List<Double> inList){
        int size = inList.size();
        for (int aDegree2 : rate2) {
            if (size < aDegree2) {
                for (int k = size; k < aDegree2; k++) {
                    inList.add(0.0);
                }
                return inList;
            }
        }
        return inList;
    }


    public static List<Double> getXPoints(List<Points> pointsList){
        return pointsList.stream().map(Points::getX).collect(Collectors.toList());
    }

    public static List<Double> getYPoints(List<Points> pointsList){
        return pointsList.stream().map(Points::getY).collect(Collectors.toList());
    }

}
