package com.loading.neo4j.service;

import com.loading.neo4j.QualityMeasure.ObjectMeasure;
import com.loading.neo4j.QualityMeasure.subjectMeasure;
import com.loading.neo4j.datainteract.AnalysisReport;
import com.loading.neo4j.datainteract.Writ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;

@Service
public class ScoreService {
    @Autowired
    subjectMeasure subjectM;
    public AnalysisReport score(Writ ws){
        AnalysisReport analysisReport=new AnalysisReport();
        ObjectMeasure objectMeasure=new ObjectMeasure(ws);
        objectMeasure.cal_all();
        analysisReport.setAccScore(objectMeasure.getAcc_score());
        analysisReport.setConsiScore(objectMeasure.getConsistency_score());
        analysisReport.setInteScore(objectMeasure.getIntegrality_score());
        analysisReport.setCriminalScore(objectMeasure.getCriminal_score());
        analysisReport.setTrialScore(objectMeasure.getTrial_score());
        analysisReport.addAdvices(objectMeasure.getAdvice());
        //TODO: 主观质量评估
        subjectM.subjectM(ws);
        analysisReport.setJudgeScore(subjectM.JudgmentRight());
        analysisReport.setLawScore(subjectM.lawRight());
        return analysisReport;
    }
}
