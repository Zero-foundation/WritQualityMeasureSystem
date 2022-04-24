package com.loading.neo4j.QualityMeasure;

import java.util.HashMap;
import java.util.Map;

public class ObjectMeasure {
    int object_score;
    Map<String,String> object_score_list;
    Map<String,String> map_content;
    ObjectMeasure(Map<String,String> map){
        object_score = 0;
        object_score_list = new HashMap<String, String>();
        map_content = map;
    }
    public void cal_integrality(){
        //完整性

    }
    public void cal_acc(){
        //准确性
        cal_trial();
        cal_person();
        cal_constituent();
    }
    public void cal_consistency(){
        //一致性
    }
    private void cal_trial(){
        //审理经过
    }
    private void cal_constituent(){
        //构成事项

    }
    private void cal_person(){
        //参诉人信息
    }







}
