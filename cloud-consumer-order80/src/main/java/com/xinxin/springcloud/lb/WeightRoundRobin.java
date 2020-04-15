package com.xinxin.springcloud.lb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 平衡加权轮训算法
 * ip:A,B,C
 * 静态 weight:5,1,1
 * 动态 currentWeight:初始为0,0,0
 * currentWeight += weight     max(currentWeight)   resultIp   max(currentWeight) -= sum(weight)  returnIp
 * 5,1,1                       5                    A          -2,1,1(得到新的currentWeight)                             A
 * 3,2,2                       3                    A          -4,2,2
 * 1,3,3                       3                    B          1,-4,3
 * 6,-3,4                      6                    A          -1,-3,4
 * 4,-2,5                      5                    C          4,-2,-2
 * 9,-1,-1                     9                    A          2,-1,-1
 * 7,0,0                       7                    A          0,0,0
 * 5,1,1                       5                    A          -2,1,1
 */
public class WeightRoundRobin {

    private static Map<String,Weight> weightMap = new LinkedHashMap<>();

    private static String getServer(){
        int totalWeight = 0;
        for (Integer weight:ServerIpsRound.WEIGHT_MAP.values()){
//            totalWeight = totalWeight + weight;
            totalWeight += weight;
        }

        //准备数据源
        if (weightMap.isEmpty()){
            for (String ip:ServerIpsRound.WEIGHT_MAP.keySet()){
                Integer weight = ServerIpsRound.WEIGHT_MAP.get(ip);
                weightMap.put(ip,new Weight(ip,weight,0));
            }
        }
        //currentWeight += weight
        for (Weight weight:weightMap.values()){
            weight.setCurrentWeight(weight.getCurrentWeight()+weight.getWeight());
        }

        //resultIpObject
        Weight maxCurrentWeight = null;
        for (Weight weight:weightMap.values()){
            if (maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()){
                maxCurrentWeight = weight;
            }
        }

        //max(currentWeight) -= sum(weight)
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeight);
        return maxCurrentWeight.getIp();
    }

    public static void main(String[] args) {
        for (int i = 0;i<12;i++){
            System.out.println(getServer());
        }
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Weight{
    private String ip;
    private Integer weight;
    private Integer currentWeight;
}

class ServerIpsRound{
    public static final Map<String,Integer> WEIGHT_MAP = new HashMap<>();
    static {
        WEIGHT_MAP.put("A",5);
        WEIGHT_MAP.put("B",1);
        WEIGHT_MAP.put("C",1);
    }
}