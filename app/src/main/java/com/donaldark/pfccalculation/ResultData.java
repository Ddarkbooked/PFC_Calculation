package com.donaldark.pfccalculation;

public class ResultData {

    private String firstLine;
//    private String secondLine;

    public ResultData(String firstLine /*, String secondLine */) {
        this.firstLine = firstLine;
//        this.secondLine = secondLine;
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

    //    public String getSecondLine() {
//        return secondLine;
//    }
//
//    public void setSecondLine(String secondLine) {
//        this.secondLine = secondLine;
//    }
}
