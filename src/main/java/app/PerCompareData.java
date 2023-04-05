package app;


public class PerCompareData {
    public String lgaName;

    public String stateCode;

    public double homelessDif;

    public double atRiskDif;

    public double shift;


    public PerCompareData() {
    }


    public PerCompareData(String lgaName, String stateCode, double homelessDif, double atRiskDif, double shift) {
        this.lgaName = lgaName;
        this.stateCode = stateCode;
        this.homelessDif = homelessDif;
        this.atRiskDif = atRiskDif;
        this.shift = shift;
    }
}
