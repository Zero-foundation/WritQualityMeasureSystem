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

    public AnalysisReport score(Writ ws){
        AnalysisReport analysisReport=new AnalysisReport();
        ObjectMeasure objectMeasure=new ObjectMeasure(ws);
        objectMeasure.cal_integrality();
        analysisReport.setAccScore(objectMeasure.cal_criminal());
        return analysisReport;
    }
}
