package com.loading.neo4j.datainteract;

public class Accused {
    //被告人姓名
    private String name;
    //被告人情节，包括犯罪事实、从轻、从重、是否使用缓刑等
    private String[] circumstance;
    //对该被告人的审判结果
    private String[] judge_res;
    //罪名（一个被告人在同一份文书中可能犯多个罪）
    private String[] crimes;

    public Accused() {
    }

    public Accused(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCircumstance() {
        return circumstance;
    }

    public void setCircumstance(String[] circumstance) {
        this.circumstance = circumstance;
    }

    public String[] getJudge_res() {
        return judge_res;
    }

    public void setJudge_res(String[] judge_res) {
        this.judge_res = judge_res;
    }

    public String[] getCrimes() {
        return crimes;
    }

    public void setCrimes(String[] crimes) {
        this.crimes = crimes;
    }
}
