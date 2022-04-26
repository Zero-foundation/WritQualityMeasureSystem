package com.loading.neo4j.datainteract;

import java.util.List;

public class criminalContent {

    String criminalId;//罪犯id

    String criminalName;//罪犯姓名

    //String criminalConsequence;//犯罪后果

    List<String> Judgement;//犯罪处罚

    List<String> circumstance;//情节和后果

    List<String> articles_law;//法律条文

    List<String> crimeList;//罪名

    criminalContent(){

    }

    public List<String> getArticles_law() {
        return articles_law;
    }

    public List<String> getJudgement() {
        return Judgement;
    }

    public List<String> getCrimeList() {
        return crimeList;
    }

    //public String getCriminalConsequence() {
        //return criminalConsequence;
    //}

    public String getCriminalId() {
        return criminalId;
    }

    public String getCriminalName() {
        return criminalName;
    }

    public List<String> getCircumstance() {
        return circumstance;
    }
}
