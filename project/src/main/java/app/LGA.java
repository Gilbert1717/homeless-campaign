package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class LGA {


    public int population;

    public int statePopulation;


    public String gender;

    public String homelessType;

    public String ageRange;

    public String LGAType;

    public int lga_population;

    


    /**
     * Create an LGA and set the fields
     */
    public LGA() {

    }


    public LGA(int population, int statePopulation, int ausPopulation, String lga_name, String gender, String homelessType, String ageRange, String LGAType, int lga_population) {
        this.population = population;
        this.statePopulation = statePopulation;
        this.gender = gender;
        this.homelessType = homelessType;
        this.ageRange = ageRange;
        this.LGAType = LGAType;
        this.lga_population = lga_population;
    }
}


   
