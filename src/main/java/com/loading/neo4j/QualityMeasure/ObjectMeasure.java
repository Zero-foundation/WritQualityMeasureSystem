package com.loading.neo4j.QualityMeasure;

import com.loading.neo4j.datainteract.Writ;

import java.util.HashMap;
import java.util.Map;

public class ObjectMeasure {
    int objectScore;
    int inteScore;//判决完整性得分
    int consiScore;//一致性得分
    int accScore;//准确性得分

    Writ writ;
    ObjectMeasure(Writ writ){
        objectScore = 100;
        this.writ = writ;
    }


    public void cal_integrality(){
        //判决内容完整性

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
