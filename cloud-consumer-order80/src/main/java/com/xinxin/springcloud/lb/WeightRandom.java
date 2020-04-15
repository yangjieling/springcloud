package com.xinxin.springcloud.lb;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 加权随机算法
 */
public class WeightRandom {

    public static String getServer() {
        int totalWeight = 0;
        for (Integer weight : ServerIpsRandom.WEIGHT_MAP.values()) {
            totalWeight += weight;
        }

        int offset = new Random().nextInt(totalWeight);

        for (String ip : ServerIpsRandom.WEIGHT_MAP.keySet()) {
            if (offset < ServerIpsRandom.WEIGHT_MAP.get(ip)) {
                return ip;
            }
            offset -= ServerIpsRandom.WEIGHT_MAP.get(ip);
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++){
            System.out.println(getServer());
        }
    }
}

class ServerIpsRandom {
    public static final Map<String, Integer> WEIGHT_MAP = new HashMap<>();

    static {
        WEIGHT_MAP.put("A", 5);
        WEIGHT_MAP.put("B", 1);
        WEIGHT_MAP.put("C", 1);
    }
}