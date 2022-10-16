package app;


public class CompareData {


    public String lgaName;

    public String stateCode;

    public int populationDif;

    public int homelessDif;

    public int atRiskDif;

    public int shift;


    public CompareData() {
    }


    public CompareData(String lgaName, String stateCode,int populationDif, int homelessDif, int atRiskDif, int shift) {
        this.lgaName = lgaName;
        this.stateCode = stateCode;
        this.populationDif = populationDif;
        this.homelessDif = homelessDif;
        this.atRiskDif = atRiskDif;
        this.shift = shift;
    }
}
