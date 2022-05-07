package com.loading.neo4j.datainteract;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


//分析结果报告
@Getter
@Setter
public class AnalysisReport {
    public AnalysisReport() {
        this.ObjectAdvice=new ArrayList<>();
    }
    List<String> ObjectAdvice;
    double inteScore;//判决完整性得分
    double consiScore;//一致性得分
    double accScore;//准确性得分
    double trialScore;//审判流程得分
    double criminalScore;//被告人信息得分
    double lawScore;//法条适用性得分
    double JudgeScore;//审判偏离打分

    public void addAdvices(List<String> list){
        this.ObjectAdvice.addAll(list);
    }



}
