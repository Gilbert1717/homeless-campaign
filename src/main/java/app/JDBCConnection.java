package app;

import kotlin.text.UStringsKt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/homeless.db";

    // If you are using the Homelessness data set replace this with the below
    //private static final String DATABASE = "jdbc:sqlite:database/homelessness.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }


    public ArrayList<DataTable> getDataTable(String SQLquery, String where, String group, String order) {

        ArrayList<DataTable> dataTables = new ArrayList<DataTable>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = SQLquery + " " + where + " " + group + " " + order + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                DataTable dataTable = new DataTable();



                 dataTable.sumPopulation = results.getInt("sum(population)");
                 dataTable.percentage = results.getDouble("percentage");
                 dataTable.stateCode = results.getString("state_code");
                 dataTable.lga_name = results.getString("lga_name");
                 dataTable.lga_population = results.getInt("lga_population");
                 dataTable.median_income = results.getInt("median_income");
                 dataTable.median_age = results.getInt("median_age");
                 dataTable.median_mortgage = results.getInt("median_mortgage");
                 dataTable.median_rent = results.getInt("median_rent");



                dataTables.add(dataTable);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }


        return dataTables;
    }

    public ArrayList<SumData> getSumData() {
        // Create the ArrayList to return - of Strings for the movie titles
        ArrayList<SumData> sumDatas = new ArrayList<SumData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select sum(population) as homelessTotal,count(distinct lga_code) as lgaNumber,year from data group by year";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // We can lookup a column of the a single record in the
                // result using the column name
                // BUT, we must be careful of the column type!
                SumData sumData = new SumData();

                sumData.homelessTotal  = results.getInt("homelessTotal");
                sumData.lgaNumber  = results.getInt("lgaNumber");
                sumData.year    = results.getInt("year");


                sumDatas.add(sumData);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movie titles
        return sumDatas;
    }

    public ArrayList<SumData> getSumData(String SQLquery, String where, String group) {
        // Create the ArrayList to return - of Strings for the movie titles
        ArrayList<SumData> sumDatas = new ArrayList<SumData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = SQLquery + " " + where + " " + group + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // We can lookup a column of the a single record in the
                // result using the column name
                // BUT, we must be careful of the column type!
                SumData sumData = new SumData();

                sumData.homelessTotal  = results.getInt("homelessTotal");
                sumData.percentage = results.getDouble("percentage");
                sumData.lgaNumber  = results.getInt("lgaNumber");
                sumData.year    = results.getInt("year");


                sumDatas.add(sumData);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movie titles
        return sumDatas;
    }

    public ArrayList<CompareData> getCompareData(String SQLquery, String order) {
        // Create the ArrayList to return - of Strings for the movie titles
        ArrayList<CompareData> compareDatas = new ArrayList<CompareData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = SQLquery + " " + order + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // We can lookup a column of the a single record in the
                // result using the column name
                // BUT, we must be careful of the column type!
                CompareData compareData = new CompareData();
                compareData.stateCode = results.getString("state_code");
                compareData.lgaName = results.getString("lga_name");
                compareData.populationDif = results.getInt("populationDif");
                compareData.homelessDif  = results.getInt("homelessDif");
                compareData.atRiskDif    = results.getInt("riskDif");
                compareData.shift    = results.getInt("shift");


                compareDatas.add(compareData);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movie titles
        return compareDatas;
    }

    public ArrayList<PerCompareData> getPerCompareData(String SQLquery, String order) {
        // Create the ArrayList to return - of Strings for the movie titles
        ArrayList<PerCompareData> perCompareDatas = new ArrayList<PerCompareData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = SQLquery + " " + order + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // We can lookup a column of the a single record in the
                // result using the column name
                // BUT, we must be careful of the column type!
                PerCompareData perCompareData = new PerCompareData();

                perCompareData.lgaName = results.getString("lga_name");
                perCompareData.stateCode = results.getString("state_code");
                perCompareData.homelessDif  = results.getDouble("homelessDif");
                perCompareData.atRiskDif    = results.getDouble("riskDif");
                perCompareData.shift    = results.getDouble("shift");


                perCompareDatas.add(perCompareData);
            }
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movie titles
        return perCompareDatas;
    }

    public static String getPopMax() {
        int pop = 0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select max(lga_population) from data;";
            ResultSet results = statement.executeQuery(query);

            pop = results.getInt("max(lga_population)");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return Integer.toString(pop);
    }

    public static String getAreaMax() {
        double area = 0.0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select max(area_aqkm) from data";
            ResultSet results = statement.executeQuery(query);

            area = results.getDouble("max(area_aqkm)");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        int areaInt = (int)Math.ceil(area);

        return Integer.toString(areaInt);
    }

    public static String getAgeMax() {
        int Age = 0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select max(median_age) from data";
            ResultSet results = statement.executeQuery(query);

            Age = results.getInt("max(median_age)");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return Integer.toString(Age);
    }

    public static String getMortgageMax() {
        int mortgage = 0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select max(median_mortgage) from data";
            ResultSet results = statement.executeQuery(query);

            mortgage = results.getInt("max(median_mortgage)");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return Integer.toString(mortgage);
    }

    public static String getIncomeMax() {
        int income = 0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select max(median_income) from data";
            ResultSet results = statement.executeQuery(query);

            income = results.getInt("max(median_income)");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return Integer.toString(income);
    }

    public static String getRentMax() {
        int rent = 0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select max(median_rent) from data";
            ResultSet results = statement.executeQuery(query);

            rent = results.getInt("max(median_rent)");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return Integer.toString(rent);
    }




    //Method for persona name.
    public String getPName(int ID) {
        String name = "";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT name FROM persona NATURAL JOIN personaAttributes where personaID = '" + ID + "';";
            ResultSet results = statement.executeQuery(query);
            name = results.getString("name");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return name;
    }

    public String getPImage(int ID) {
        String imgHref = "";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT image_FilePath FROM persona NATURAL JOIN personaAttributes where personaID = '" + ID + "';";
            ResultSet results = statement.executeQuery(query);
            imgHref = results.getString("image_FilePath");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return imgHref;
    }





    //Fix me
    public String getPdescription(int ID) {
        String html = "<h4>Who I am:</h4><p>";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT description FROM persona NATURAL JOIN personaAttributes where personaID = '" + ID + "';";
            ResultSet results = statement.executeQuery(query);
            html = html + results.getString("description") + "</p>";
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return html;
    }
    public static String getLGAType(String LGA) {
        Connection connection = null;
        String LGAType = "";
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "Select lga_type from lgas " +
                    "where lga_name = '" +
                    LGA + "';";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                LGAType = results.getString("lga_type");

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return LGAType;
    }

    public static int getLGAPop(String LGA) {
        Connection connection = null;
        int LGAPop = -1;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "Select lga_population from (select p.lga_population, l.lga_name, year from lgas_population p left join lgas l on p.lga_code = l.lga_code) " +
                    "where lga_name = '" +
                    LGA + "';";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                LGAPop = results.getInt("lga_population");

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return LGAPop;
    }
    public static int getAusPop(String year) {
        Connection connection = null;
        int ausPop = -1;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "Select sum(lga_population) as AusPop from lgas_population where year = '" + year + "';";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                ausPop = results.getInt("AusPop");

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return ausPop;
    }
    public static ArrayList<String> getLGAName() {
        ArrayList<String> LGANames = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "Select distinct lga_name from lgas;";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                String LGAName = results.getString("lga_name");

                LGANames.add(LGAName);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return LGANames;
    }


    public static ArrayList<LGA> getLGAs(String SQLquery, String where) {
        ArrayList<LGA> LGAs = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = SQLquery + where + ";" ;
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                LGA LGA = new LGA();

                 LGA.population = results.getInt("Population");

                 LGA.statePopulation = results.getInt("statePopulation");

                 LGA.gender = results.getString("gender");

                 LGA.homelessType = results.getString("homeless_type");

                 LGA.ageRange = results.getString("age_range");

                 LGA.LGAType = results.getString("lga_type");

                 LGA.lga_population = results.getInt("lga_population");

                //Adds the object to the arraylist
                LGAs.add(LGA);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return LGAs;
    }
}


   
