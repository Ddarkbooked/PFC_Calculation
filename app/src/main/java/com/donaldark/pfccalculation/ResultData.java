package com.donaldark.pfccalculation;

public class ResultData {

    private String firstLine;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ResultData(String firstLine,String date) {
        this.firstLine = firstLine;
        this.date = date;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "firstLine='" + firstLine + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
