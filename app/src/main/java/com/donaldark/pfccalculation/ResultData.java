package com.donaldark.pfccalculation;

public class ResultData {

    private String firstLine;

    public ResultData(String firstLine) {
        this.firstLine = firstLine;
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
                '}';
    }
}
