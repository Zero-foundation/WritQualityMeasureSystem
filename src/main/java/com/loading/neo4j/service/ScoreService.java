package com.loading.neo4j.service;

import com.loading.neo4j.QualityMeasure.ObjectMeasure;
import com.loading.neo4j.QualityMeasure.subjectMeasure;
import com.loading.neo4j.datainteract.AnalysisReport;
import com.loading.neo4j.datainteract.Writ;
import com.loading.neo4j.readUtils.readFromDocx;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class ScoreService {
    @Autowired
    subjectMeasure subjectM;
    @Autowired
    readFromDocx rfd;
    @Autowired
    ObjectMeasure objectM;

    public AnalysisReport score(String fileName) throws Exception {


        Writ ws;
        try {
            ws = rfd.extract(fileName);
        } catch (IOException e) {
            throw new Exception("文书解析失败，请检查内容");
        }
        //调用python方法获取Writ对象

        AnalysisReport analysisReport=new AnalysisReport();
        objectM.setWenshu(ws);
        objectM.cal_all();
        analysisReport.setAccScore(objectM.getAcc_score());
        analysisReport.setConsiScore(objectM.getConsistency_score());
        analysisReport.setInteScore(objectM.getIntegrality_score());
        analysisReport.setCriminalScore(objectM.getCriminal_score());
        analysisReport.setTrialScore(objectM.getTrial_score());
        analysisReport.addAdvices(objectM.getAdvice());
        analysisReport.setNumConsiScore(objectM.getNum_consistency());
        analysisReport.setSignConsiScore(objectM.getSign_consistency());
        analysisReport.setLevel(objectM.getLevels());
        analysisReport.setWrongUsedList(objectM.getWrongUsedList());

        //TODO: 主观质量评估
//        subjectM.subjectM(ws);
//        analysisReport.setJudgeScore(subjectM.JudgmentRight());
//        analysisReport.setLawScore(subjectM.lawRight());
        return analysisReport;
    }
}
