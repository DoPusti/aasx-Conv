package com.example.aasxConv;

public class PairSynonym {

    private String synonym;
    private String originName;
    private String unit;

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public PairSynonym(String synonym, String originName, String unit) {
        this.synonym = synonym;
        this.originName = originName;
        this.unit = unit;
    }
}
