package com.loading.neo4j.datainteract;

import lombok.Getter;
import lombok.Setter;

//分析结果报告
@Getter
@Setter
public class AnalysisReport {
    public AnalysisReport() {
    }

    double inteScore;//判决完整性得分
    double consiScore;//一致性得分
    double accScore;//准确性得分

    int lawScore;//法条适用性得分
    int JudgeScore;//审判偏离打分



}
