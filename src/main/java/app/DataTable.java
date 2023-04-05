package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class DataTable {


    public int sumPopulation;

    public double percentage;

    public String lga_name;

    public String stateCode;
    public int lga_population;

    public int median_income;

    public int median_age;

    public int median_mortgage;

    public int median_rent;


    /**
     * Create an LGA and set the fields
     */
    public DataTable() {

    }



    public DataTable(int sumPopulation, double percentage, String lga_name, String stateCode, int lga_population, int median_income, int median_age, int median_mortgage, int median_rent) {

        this.sumPopulation = sumPopulation;
        this.percentage = percentage;
        this.lga_name = lga_name;
        this.stateCode = stateCode;
        this.lga_population = lga_population;
        this.median_income = median_income;
        this.median_age = median_age;
        this.median_mortgage = median_mortgage;
        this.median_rent = median_rent;
    }
}


   
