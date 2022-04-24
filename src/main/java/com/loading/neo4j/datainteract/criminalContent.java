package com.loading.neo4j.datainteract;

import java.util.List;

public class criminalContent {

    String criminalId;//罪犯id

    String criminalName;//罪犯姓名

    String criminalConsequence;//犯罪后果

    List<String> punishment;//犯罪处罚

    String seriousness;//情节严重性

    List<String> articles_law;//法律条文

    List<String> chargeList;//罪名

    criminalContent(){

    }

    public List<String> getArticles_law() {
        return articles_law;
    }

    public List<String> getPunishment() {
        return punishment;
    }

    public List<String> getChargeList() {
        return chargeList;
    }

    public String getCriminalConsequence() {
        return criminalConsequence;
    }

    public String getCriminalId() {
        return criminalId;
    }

    public String getCriminalName() {
        return criminalName;
    }

    public String getSeriousness() {
        return seriousness;
    }
}
