package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class DataEntry implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/dataentry.html";

    @Override
    public void handle(Context context) throws Exception {

        String SQLquery = "";
        String where = "";
        String order = "";
        String group = "";
        String display = context.formParam("selector");
        String disYear = " All";
        String disGender = " All";
        String disHomelessType = " All";
        String disAgeRange = " All";
        String disState = " All";
        String disPopulation = " -";
        String disArea = " -";
        String disAge = " -";
        String disMortgage = " -";
        String disIncome = " -";
        String disRent = " -";


        String year = context.formParam("year");
        if (!"compare".equals(display)) {
            if ("2016".equals(year)) {
                where = "where year='2016'";
                disYear = "2016";
            } else if ("2018".equals(year)) {
                where = "where year='2018'";
                disYear = "2018";
            } else {
                where = "where (year='2016' or year='2018')";
            }
        }


        String gender = context.formParam("gender");
        if ("male".equals(gender)) {

            where = where + " and g_id = 'm'";
            disGender = "male";
        } else if ("female".equals(gender)) {

            where = where + " and g_id = 'f'";
            disGender = "female";
        }
        if (!"compare".equals(display)) {
            String homelessType = context.formParam("homelessType");
            if ("homeless".equals(homelessType)) {

                where = where + " and HT_id = '1'";
                disHomelessType = "homeless";
            } else if ("homeless_at_risk".equals(homelessType)) {

                where = where + " and HT_id = '2'";
                disHomelessType = "homeless at risk";
            }
        }

        if (!"0".equals(context.formParam("age-range0")) && "1".equals((context.formParam("age-range")))) {
            ArrayList<String> ageRanges = new ArrayList<>();
            for (int i = 1; i <= 8; i++) {
                String ageRange = context.formParam("age-range" + i);
                if (ageRange != null) {
                    ageRanges.add(ageRange);
                }
            }

            if (!ageRanges.isEmpty()) {
                where = where + " and (";
                disAgeRange = "";
                for (String ageRange : ageRanges) {
                    where = where + "AR_id = " + ageRange;
                    disAgeRange += ageRangeCov(ageRange) + "<br>";
                    if (!(ageRanges.size() == 1
                            || ageRanges.get(ageRanges.size() - 1).equals(ageRange))
                    ) {
                        where = where + " or ";
                    }
                }
                where = where + ")";
            }
        }
        String popMax = JDBCConnection.getPopMax();
        String areaMax = JDBCConnection.getAreaMax();
        String ageMax = JDBCConnection.getAgeMax();
        String mortgageMax = JDBCConnection.getMortgageMax();
        String incomeMax = JDBCConnection.getIncomeMax();
        String rentMax = JDBCConnection.getRentMax();


        String minPopulation = context.formParam("min-population");
        String maxPopulation = context.formParam("max-population");
        if (minPopulation != null || maxPopulation != null) {
            disPopulation = minPopulation + "-" + maxPopulation;
        }


        if (!popMax.equals(maxPopulation)) {
            where = where + " and lga_population <=" + maxPopulation;

        } else if (!"0".equals(minPopulation)) {
            where = where + " and lga_population >=" + minPopulation;
        }

        // area range
        String minArea = context.formParam("min-area");
        String maxArea = context.formParam("max-area");
        if (minArea != null || maxArea != null) {
            disArea = minArea + "-" + maxArea;
        }


        if (!areaMax.equals(maxArea)) {
            where = where + " and area_aqkm <=" + maxArea;
        } else if (!"0".equals(minArea)) {
            where = where + " and area_aqkm >=" + minArea;
        }

        // age range
        String minAge = context.formParam("min-age");
        String maxAge = context.formParam("max-age");
        if (minAge != null || maxAge != null) {
            disAge = minAge + "-" + maxAge;
        }
        if (!ageMax.equals(maxAge)) {
            where = where + " and median_age <=" + maxAge;
        } else if (!"0".equals(minAge)) {
            where = where + " and median_age >=" + minAge;
        }

        // mortgage range
        String minMortgage = context.formParam("min-mortgage");
        String maxMortgage = context.formParam("max-mortgage");
        if (minMortgage != null || maxMortgage != null) {
            disMortgage = minMortgage + "-" + maxMortgage;
        }


        if (!mortgageMax.equals(maxMortgage)) {
            where = where + " and median_mortgage <=" + maxMortgage;
        } else if (!"0".equals(minMortgage)) {
            where = where + " and median_mortgage >=" + minMortgage;
        }

        // income range
        String minIncome = context.formParam("min-income");
        String maxIncome = context.formParam("max-income");

        if (minIncome != null || maxIncome != null) {
            disIncome = minIncome + "-" + maxIncome;
        }


        if (!incomeMax.equals(maxIncome)) {
            where = where + " and median_income <=" + maxIncome;
        } else if (!"0".equals(minIncome)) {
            where = where + " and median_income >=" + minIncome;
        }

        // rent range
        String minRent = context.formParam("min-rent");
        String maxRent = context.formParam("max-rent");
        if (minRent != null || maxRent != null) {
            disRent = minRent + "-" + maxRent;
        }


        if (!rentMax.equals(maxRent)) {
            where = where + " and median_rent <=" + maxRent;
        } else if (!"0".equals(minRent)) {
            where = where + " and median_rent >=" + minRent;
        }


        if (!"0".equals(context.formParam("state"))) {
            ArrayList<String> states = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                String state = context.formParam("state" + i);
                if (state != null) {
                    states.add(state);
                }
            }

            if (!states.isEmpty()) {
                where = where + " and (";
                disState = "";
                for (String state : states) {
                    where = where + "lga_code like '" + state + "%'";
                    disState += stateNameCovert(state) + "<br>";
                    if (!(states.size() == 1
                            || states.get(states.size() - 1).equals(state))
                    ) {
                        where = where + " or ";
                    }
                }
                where = where + ")";
            }
        }

        String lga = context.formParam("lga");

        if (lga != null && !lga.isEmpty() && "1".equals(context.formParam("State-LGA-filter"))) {
            where = where + " and lga_name like '%" + lga + "%'";
        }


        String html = "";
        // icon cite from: https://www.demantelez.com/static/img/homeless.b83a206.png
        html = html + """
                <!DOCTYPE html>
                <html class="datapage" lang="en" xmlns="http://www.w3.org/1999/html">
                <head>
                    <meta charset='UTF-8'>
                    <title>Ending Homelessness</title>
                    <link rel='stylesheet' href='homelessStyle.css'>
                    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
                    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
                    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
                </head>
                                
                <body>
                <header>
                    <div class='topnav'>
                        <a href='/'><h2>Main Page</h2></a>
                        <a href='/dataentry.html'><h2>Data Exploring</h2></a>
                    </div>
                    <div class="icon">
                        <a href="/">
                            <img src='homeless.png' height=100em width=100em>
                        </a>
                    </div>
                </header>
                           
                <form  id="form" action="/dataentry.html" method="post" onkeydown="return event.key != 'Enter';">
                    <div id="tabledisplay">
                    <h1>Please select the data type</h1><br>
                    <fieldset id="selector">
                        <div class="radio">
                            <input type="radio" id="sum" name="selector" value="sum" required>
                            <label for="sum">Summary</label>
                        </div>
                        <div class="radio">
                            <input type="radio" id="compare" name="selector" value="compare" required>
                            <label for="compare">Comparison</label>
                        </div>
                        <div class="radio">
                            <input type="radio" id="detail" name="selector" value="detail" required>
                            <label for="detail">Details</label>
                        </div>
                    </fieldset>
                </select>
                    </div>
                    <div id="data-entry-filter-panel" hidden>
                    <div class="grid-datapage-filter">
                        <div class="grid-datapage-filter-item-1">
                            <div class="filter-controller">
                                <label>Display Controller</label>
                            </div>
                            <br>
                            <div class="filter-panel">
                           
                            
                            <div id="group" hidden>
                            <label for="groupCheck">Show by</label>
                            <select id="groupCheck" name="groupCheck">
                                <option value="LGA" selected>LGA</option>
                                <option value="State">State</option>
                            </select>
                            <br>
                            </div>
                            
                            <div id="year">
                            <label for="year">Year</label>
                            <select id="year" name="year">
                                <option value="">all years</option>
                                <option value="2016">2016</option>
                                <option value="2018">2018</option>
                            </select>
                             <br>
                            </div>
                            
                            <div id="percentage" hidden>
                            <label for="percentage">Show Percentage</label>
                            <input type="checkbox" id="percentage" name="percentage" value="1">
                            <br>
                            </div>
                           
                              
                                
                            <div id="display-controller-sort" hidden>
                                <h2>Sorting</h2>
                                <label for="sorting1">Sorting Order 1</label>
                                <select id="sorting1" name="sorting1">
                                    <option value="">-</option>
                                    <option value="lga_name">LGA</option>
                                    <option value="sum(population)">Homeless Population</option>
                                    <option value="percentage">Ratio</option>
                                    <option value="lga_population">LGA population</option>
                                    <option value="median_age">median age</option>
                                    <option value="median_mortgage">median mortgage</option>
                                    <option value="median_income">median income</option>
                                    <option value="median_rent">median rent</option>
                                </select>
                                <label for="descending1">Descending</label>
                                <input type="checkbox" id="descending1" name="descending1" value="desc"><br>
                                <div id = 'more-sorting' hidden>
                                <label for="sorting2">Sorting Order 2</label>
                                <select id="sorting2" name="sorting2">
                                    <option value="">-</option>
                                    <option value="lga_name">LGA</option>
                                    <option value="sum(population)">Homeless Population</option>
                                    <option value="percentage">Ratio</option>
                                    <option value="lga_population">LGA population</option>
                                    <option value="median_age">median age</option>
                                    <option value="median_mortgage">median mortgage</option>
                                    <option value="median_income">median income</option>
                                    <option value="median_rent">median rent</option>
                                </select>
                                <label for="descending2">Descending</label>
                                <input type="checkbox" id="descending2" name="descending2" value="desc">
                                <br>
                                </div>
                            </div>
                            
                            <div id="compare-sort" hidden>
                                <h2>Sorting</h2>
                                <label for="compareaSorting">Sorting Order 1</label>
                                <select id="compareaSorting" name="compareaSorting">
                                    <option value="lga_name">LGA</option>
                                    <option value="homelessDif">Increase In Homeless</option>
                                    <option value="riskDif">Increase In Homeless at Risk</option>
                                    <option value="shift">Shift in Homeless</option>
                                </select>
                                <label for="compareDesc">Descending</label>
                                <input type="checkbox" id="compareDesc" name="compareDesc" value="desc"><br>
                            </div>
                            </div>
                        </div>
                                
                        <div class="grid-datapage-filter-item-4">
                            <div class="filter-controller">
                                 <label>Advanced LGA Filters</label>
                             </div><br>
                            <div class="filter-panel" id="advanced">     
                    
                                
                            <label>Population</label>
                            <div class="wrapper">
                                <div class="values">
                                    <input class="filter-input" id="min-population" type="number" name="min-population">
                                    <span> &dash; </span>
                                    <input class="filter-input" id='max-population' type='number' name="max-population">
                                </div>
                                <div class="container">
                                    <div class="slider-track slider-track-pop"></div>
                """;
        html = html + "<input type='range' min='0' max='" + popMax + "' value='0' id='slider-pop1' oninput='popSlide1()'>";
        html = html + "<input type='range' min='0' max='" + popMax + "' value='" + popMax + "' id='slider-pop2' oninput='popSlide2()'>";
        html = html + """        
                                </div>
                            </div>


                            <label>LGA area</label>
                            <div class="wrapper">
                                <div class="values">
                                  
                                    <input class="filter-input" id="min-area" type="number" name="min-area">
                                    <span> &dash; </span>
                                    <input class="filter-input" id="max-area" type="number" name="max-area">
                                        </div>
                                        <div class="container">
                                            <div class="slider-track slider-track-area"></div>
                """;
        html = html + "<input type='range' min='0' max='" + areaMax + "' value='0' id='slider-area1' oninput='areaSlide1()'>";
        html = html + "<input type='range' min='0' max='" + areaMax + "' value='" + areaMax + "' id='slider-area2' oninput='areaSlide2()'>";


        html = html + """   
                                </div>
                            </div>


                            <label>Median Age</label>
                            <div class="wrapper">
                                <div class="values"> 
                                    <input class="filter-input" id="min-age" type="number" name="min-age">
                                    <span> &dash; </span>
                                    <input class="filter-input" id="max-age" type="number" name="max-age">
                        </div>
                <div class="container">
                  <div class="slider-track slider-track-age"></div>
                  """;
        html = html + "<input type='range' min='0' max='" + ageMax + "' value='0' id='slider-age1' oninput='ageSlide1()'>";
        html = html + "<input type='range' min='0' max='" + ageMax + "' value='" + ageMax + "' id='slider-age2' oninput='ageSlide2()'>";

        html = html + """   

                            </div>
                        </div>


                            <label>Median Mortgage</label>
                            <div class="wrapper">
                                <div class="values">
                                    
                                    <input class="filter-input" id="min-mortgage" type="number" name="min-mortgage" >
                                    <span> &dash; </span>
                                    <input class="filter-input" id="max-mortgage" type="number" name="max-mortgage" >
                </div>
                <div class="container">
                    <div class="slider-track slider-track-mortgage"></div>
                    """;
        html = html + "<input type='range' min='0' max='" + mortgageMax + "' value='0' id='slider-mortgage1' oninput='mortgageSlide1()'>";
        html = html + "<input type='range' min='0' max='" + mortgageMax + "' value='" + mortgageMax + "' id='slider-mortgage2' oninput='mortgageSlide2()'>";

        html = html + """   
                                 
                    </div>
                </div>


                <label>Median Income</label>
                <div class="wrapper">
                    <div class="values">
                        <input class="filter-input" id="min-income" type="number" name="min-income">
                        <span> &dash; </span>    
                        <input class="filter-input" id="max-income" type="number" name="max-income">
                    </div>
                <div class="container">
                    <div class="slider-track slider-track-income"></div>
                    """;

        html = html + "<input type='range' min='0' max='" + incomeMax + "' value='0' id='slider-income1' oninput='incomeSlide1()'>";
        html = html + "<input type='range' min='0' max='" + incomeMax + "' value='" + incomeMax + "' id='slider-income2' oninput='incomeSlide2()'>";

        html = html + """   
                                </div>
                            </div>

                            <label>Median Rent</label>
                            <div class="wrapper">
                                <div class="values">
                                    <input class="filter-input" id="min-rent" type="number" name="min-rent">
                                    <span> &dash; </span>    
                                    <input class="filter-input" id="max-rent" type="number" name="max-rent">
                                    
                                    
                </div>
                <div class="container">
                    <div class="slider-track slider-track-rent"></div>
                    """;

        html = html + "<input type='range' min='0' max='" + rentMax + "' value='0' id='slider-rent1' oninput='rentSlide1()'>";
        html = html + "<input type='range' min='0' max='" + rentMax + "' value='" + rentMax + "' id='slider-rent2' oninput='rentSlide2()'>";

        html = html + """   
                                    </div>
                                </div>
                            </div>
                            </div>

                            <div class="grid-datapage-filter-item-2">
                            <div class="filter-controller">
                                <label>Homeless Filter</label>
                            </div>
                            <br>
                            
                             <div class="filter-panel">
                               
                                <label for="gender">Gender</label>
                                <select id="gender" name="gender">
                                    <option value="">all</option>
                                    <option value="male">male</option>
                                    <option value="female">female</option>
                                </select><br>
                                <br>
                                <div id = "homeless">
                                <label for="homelessType">Homeless Type</label>
                                <select id="homelessType" name="homelessType">
                                    <option value="">all</option>
                                    <option value="homeless">homeless</option>
                                    <option value="homeless_at_risk">at_risk</option>
                                </select><br>
                                <br>
                                </div>
                                <label for="age-range">Age Range Filter</label>
                                <input type="checkbox" id="age-range" name="age-range" value="1"><br>
                                <div id="age-range-filter" hidden>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range0" name="age-range0" value="0">
                                <label for="age-range0">all</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range1" name="age-range1" value="1">
                                <label for="age-range1">0-9</label>
                                 </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range2" name="age-range2" value="2">
                                <label for="age-range2">10-19</label>
                                 </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range3" name="age-range3" value="3">
                                <label for="age-range3">20-29</label>
                                 </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range4" name="age-range4" value="4">
                                <label for="age-range4">30-39</label>
                                 </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range5" name="age-range5" value="5">
                                <label for="age-range5">40-49</label>
                                 </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range6" name="age-range6" value="6">
                                <label for="age-range6">50-59</label>
                                 </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range7" name="age-range7" value="7">
                                <label for="age-range7">60+</label>
                                 </span>
                                <span class="nowrap">
                                <input type="checkbox" id="age-range8" name="age-range8" value="8">
                                <label for="age-range8">unknown</label>
                                 </span>
                                 </div>
                                 </div>
                            </div>

                            <div class="grid-datapage-filter-item-3">
                                <div class="filter-controller">
                                    <label for="State-LGA-filter">State and LGA filter</label> 
                                </div>
                                <br>
                                <div class="filter-panel" id="LGA-panel">
                                
                                
                                <span class="nowrap">
                                <input type="checkbox" id="all" name="state" value="0">
                                <label for="all">all</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="nsw" name="state1" value="1">
                                <label for="nsw">NSW</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="vic" name="state2" value="2">
                                <label for="vic">VIC</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="qld" name="state3" value="3">
                                <label for="qld">QLD</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="sa" name="state4" value="4">
                                <label for="sa">SA</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="wa" name="state5" value="5">
                                <label for="wa">WA</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="Tasmania" name="state6" value="6">
                                <label for="Tasmania">TAS</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="nt" name="state7" value="7">
                                <label for="nt">NT</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="act" name="state8" value="8">
                                <label for="act">ACT</label>
                                </span>
                                <span class="nowrap">
                                <input type="checkbox" id="other" name="state9" value="9">
                                <label for="other">other</label>
                                </span>
                                <br>
                                <br>
                                
                                
                                
                                <label for="lga">lga search</label>
                                <input type="search" id="lga" name="lga" placeholder="e.g. melbourne"><br><br>
                                </div>   
                            </div>
                        </div>
                        <div>
                        
                        <div class="form-button">
                        <button id="reset-button" value="clear" >clear filters</button>
                        <button id="submit-button" type="submit" value="submit" >retrieve data</button>
                        </div>
                        </div>
                        </div>
                        
                    </form>
                """;

        if (display != null) {
            html += "<div id='filter-information-flex'>";
            html += "<div id='filter-information-1'><p>Year: " + disYear + "</p>";
            html += "<p>Gender: " + disGender + "</p>";
            html += "<p>HomelessType: " + disHomelessType + "</p></div>";
            html += "<div id='filter-information-2'><p>AgeRange: <br>" + disAgeRange + "</p></div>";
            html += "<div id='filter-information-3'><p>State: <br>" + disState + "</p></div>";
            html += "<div id='filter-information-4'><p>LGA Population: " + disPopulation + "</p>";
            html += "<p>LGA Area: " + disArea + "</p>";
            html += "<p>Median Age: " + disAge + "</p></div>";
            html += "<div id='filter-information-5'><p>Median Mortgage: " + disMortgage + "</p>";
            html += "<p>Median Income: " + disIncome + "</p>";
            html += "<p>Median Rent: " + disRent + "</p></div></div>";
        }

        if ("compare".equals(display)) {
            String compareSorting = context.formParam("compareaSorting");
            String compareDesc = context.formParam("compareDesc");
            if ("compare".equals(display)) {
                order = "order by " + compareSorting;
            }

            if (compareDesc != null) {
                order = order + " DESC";
            }
            String percentage = context.formParam("percentage");
            String groupby = context.formParam("groupCheck");
            if ("LGA".equals(groupby)) {
                group = "group by lga_name";
                if ("1".equals(percentage)) {
                    SQLquery = "Select l.lga_name,substr(l.lga_code,1,1) as state_code,j3.homelessDif,j3.riskDif,j3.shift from" +
                            "(Select j1.lga_code, j2.homeless18 - j1.homeless16 as homelessDif, j2.risk18 - j1.risk16 as riskDif, (j2.homeless18-j1.homeless16+j1.risk16 - j2.risk18) as shift from" +
                            "((select lga_code, round(SUM(population)*100.0/lga_population,2) as homeless16 from data where year = '2016' and HT_id = '1' " + where + group +
                            " ) th2016 left join" +
                            "(select lga_code,round(SUM(population)*100.0/lga_population,2) as risk16 from data where year = '2016' and HT_id = '2' " + where + group +
                            " ) ta2016 on th2016.lga_code = ta2016.lga_code) j1 left join" +
                            "((select lga_code,round(SUM(population)*100.0/lga_population,2) as homeless18 from data where year = '2018' and HT_id = '1' " + where + group +
                            " ) th2018 left join (select lga_code,round(SUM(population)*100.0/lga_population,2) as risk18 from data where year = '2018' and HT_id = '2' " + where + group +
                            " ) ta2018 on th2018.lga_code = ta2018.lga_code) j2 on j1.lga_code = j2.lga_code) j3 " +
                            "left join lgas l on l.lga_code = j3.lga_code";
                    JDBCConnection jdbc = new JDBCConnection();
                    ArrayList<PerCompareData> perCompareDatas = jdbc.getPerCompareData(SQLquery, order);
                    html += """
                            <div class="table">
                            <table id="result-table" class="hover" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>LGA</th>
                                        <th>Increase in Homeless</th>
                                        <th>Increase in Homeless at Risk</th>
                                        <th>Shift in Homeless</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            </div>
                                """;
                    html += "<script>let data = [" + perCompareData(perCompareDatas) + "]; " +

                            """
                                        console.log(data);
                                        $('#result-table').DataTable( {
                                            data: data, 
                                            columns: [
                                                { data: 'lgaName' },
                                                { data: 'homelessDif' },
                                                { data: 'atRiskDif' },
                                                 { data: 'shift' }
                                            ],
                                            "iDisplayLength": 25,
                                            "order": [],
                                            scrollX: true
                                        } );
                                    """;

                    html += "</script>";


                } else {
                    SQLquery = "Select l.lga_name,substr(l.lga_code,1,1) as state_code,j3.populationDif,j3.homelessDif,j3.riskDif,j3.shift from" +
                            "(Select j1.lga_code, j2.lga_population18 - j1.lga_population16 as populationDif,j2.homeless18 - j1.homeless16 as homelessDif, j2.risk18 - j1.risk16 as riskDif, (j2.homeless18-j1.homeless16+j1.risk16 - j2.risk18) as shift from" +
                            "((select lga_code, SUM(population) as homeless16, sum(distinct lga_population) as lga_population16 from data where year = '2016' and HT_id = '1' " + where + " " + group + ") th2016 left join" +
                            "(select lga_code,SUM(population) as risk16 from data where year = '2016' and HT_id = '2' " + where + " " + group +
                            ") ta2016 on th2016.lga_code = ta2016.lga_code) j1 left join" +
                            "((select lga_code,SUM(population) as homeless18, sum(distinct lga_population) as lga_population18 from data where year = '2018' and HT_id = '1' " + where + " " + group +
                            ") th2018 left join (select lga_code,SUM(population) as risk18 from data where year = '2018' and HT_id = '2' " + where + " " + group +
                            ") ta2018 on th2018.lga_code = ta2018.lga_code) j2 on j1.lga_code = j2.lga_code) j3 " +
                            "left join lgas l on l.lga_code = j3.lga_code";


                    JDBCConnection jdbc = new JDBCConnection();
                    ArrayList<CompareData> compareDatas = jdbc.getCompareData(SQLquery, order);
                    html += """
                            <div class="table">
                            <table id="result-table" class="hover" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>LGA</th>
                                        <th>Increase in Population</th>
                                        <th>Increase in Homeless</th>
                                        <th>Increase in Homeless at Risk</th>
                                        <th>Shift in Homeless</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            </div>
                                """;
                    html += "<script>let data = [" + compareData(compareDatas) + "]; " +

                            """
                                        console.log(data);
                                        $('#result-table').DataTable( {
                                            data: data, 
                                            columns: [
                                                { data: 'lgaName' },
                                                { data: 'populationDif' },
                                                { data: 'homelessDif' },
                                                { data: 'atRiskDif' },
                                                 { data: 'shift' }
                                            ],
                                            "iDisplayLength": 25,
                                            "order": [],
                                            scrollX: true
                                        } );
                                    """;

                    html += "</script>";
                }

            } else if ("State".equals(groupby)) {
                group = "group by state_code";
                if ("1".equals(percentage)) {
                    SQLquery = "Select l.lga_name,substr(l.lga_code,1,1) as state_code,j3.homelessDif,j3.riskDif,j3.shift from" +
                            "(Select j1.lga_code, j2.homeless18 - j1.homeless16 as homelessDif, j2.risk18 - j1.risk16 as riskDif, (j2.homeless18-j1.homeless16+j1.risk16 - j2.risk18) as shift from" +
                            "((select lga_code, round(SUM(population)*100.0/lga_population,2) as homeless16 from data where year = '2016' and HT_id = '1' " + where + group +
                            " ) th2016 left join" +
                            "(select lga_code,round(SUM(population)*100.0/lga_population,2) as risk16 from data where year = '2016' and HT_id = '2' " + where + group +
                            " ) ta2016 on th2016.lga_code = ta2016.lga_code) j1 left join" +
                            "((select lga_code,round(SUM(population)*100.0/lga_population,2) as homeless18 from data where year = '2018' and HT_id = '1' " + where + group +
                            " ) th2018 left join (select lga_code,round(SUM(population)*100.0/lga_population,2) as risk18 from data where year = '2018' and HT_id = '2' " + where + group +
                            " ) ta2018 on th2018.lga_code = ta2018.lga_code) j2 on j1.lga_code = j2.lga_code) j3 " +
                            "left join lgas l on l.lga_code = j3.lga_code";
                    JDBCConnection jdbc = new JDBCConnection();
                    ArrayList<PerCompareData> perCompareDatas = jdbc.getPerCompareData(SQLquery, order);
                    html += """
                            <div class="table">
                            <table id="result-table" class="hover" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>State</th>
                                        <th>Increase in Homeless</th>
                                        <th>Increase in Homeless at Risk</th>
                                        <th>Shift in Homeless</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            </div>
                                """;
                    html += "<script>let data = [" + perCompareData(perCompareDatas) + "]; " +

                            """
                                        console.log(data);
                                        $('#result-table').DataTable( {
                                            data: data, 
                                            columns: [
                                                { data: 'state' },
                                                { data: 'homelessDif' },
                                                { data: 'atRiskDif' },
                                                 { data: 'shift' }
                                            ],
                                            "iDisplayLength": 25,
                                            "order": [],
                                            scrollX: true
                                        } );
                                    """;

                    html += "</script>";


                } else {
                    SQLquery = "Select l.lga_name,substr(l.lga_code,1,1) as state_code, j3.populationDif,j3.homelessDif,j3.riskDif,j3.shift from" +
                            "(Select j1.lga_code, j2.lga_population18 - j1.lga_population16 as populationDif, j2.homeless18 - j1.homeless16 as homelessDif, j2.risk18 - j1.risk16 as riskDif, (j2.homeless18-j1.homeless16+j1.risk16 - j2.risk18) as shift from" +
                            "((select lga_code, SUM(population) as homeless16, sum(distinct lga_population) as lga_population16 from data where year = '2016' and HT_id = '1' " + where + " " + group + ") th2016 left join" +
                            "(select lga_code,SUM(population) as risk16 from data where year = '2016' and HT_id = '2' " + where + " " + group +
                            ") ta2016 on th2016.lga_code = ta2016.lga_code) j1 left join" +
                            "((select lga_code,SUM(population) as homeless18, sum(distinct lga_population) as lga_population18 from data where year = '2018' and HT_id = '1' " + where + " " + group +
                            ") th2018 left join (select lga_code,SUM(population) as risk18 from data where year = '2018' and HT_id = '2' " + where + " " + group +
                            ") ta2018 on th2018.lga_code = ta2018.lga_code) j2 on j1.lga_code = j2.lga_code) j3 " +
                            "left join lgas l on l.lga_code = j3.lga_code";


                    JDBCConnection jdbc = new JDBCConnection();
                    ArrayList<CompareData> compareDatas = jdbc.getCompareData(SQLquery, order);
                    html += """
                            <div class="table">
                            <table id="result-table" class="hover" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>State</th>
                                        <th>Increase in Population</th>
                                        <th>Increase in Homeless</th>
                                        <th>Increase in Homeless at Risk</th>
                                        <th>Shift in Homeless</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            </div>
                                """;
                    html += "<script>let data = [" + compareData(compareDatas) + "]; " +

                            """
                                        console.log(data);
                                        $('#result-table').DataTable( {
                                            data: data, 
                                            columns: [
                                                { data: 'state' },
                                                { data: 'populationDif' },
                                                { data: 'homelessDif' },
                                                { data: 'atRiskDif' },
                                                 { data: 'shift' }
                                            ],
                                            "iDisplayLength": 25,
                                            "order": [],
                                            scrollX: true
                                        } );
                                    """;

                    html += "</script>";
                }
            }


        } else if ("detail".equals(display)) {
            String sorting1 = context.formParam("sorting1");
            String sorting2 = context.formParam("sorting2");
            String descending1 = context.formParam("descending1");
            String descending2 = context.formParam("descending2");
            if (sorting1 != null && !sorting1.isEmpty()) {
                order = "order by " + sorting1;
                if (descending1 != null) {
                    order = order + " DESC";
                }
            }


            if (sorting2 != null && !sorting2.isEmpty()) {
                order = order + " , " + sorting2;
                if (descending2 != null) {
                    order = order + " DESC";
                }
            }
            String groupby = context.formParam("groupCheck");
            if ("LGA".equals(groupby)) {
                group = "group by lga_name";
                JDBCConnection jdbc = new JDBCConnection();

                SQLquery = "select sum(population),round(sum(population)*100.0/sum(distinct lga_population),2) as percentage,state_code, lga_name, lga_population,  median_income,  median_age,  median_mortgage,  median_rent from data";
                ArrayList<DataTable> dataTables = jdbc.getDataTable(SQLquery, where, group, order);

                html += """
                        <div class="table">
                        <table id="result-table" class="hover" style="width:100%">
                            <thead>
                                <tr>
                                    <th>LGA</th>
                                    <th>Homeless Population</th>
                                    <th>Homeless Ratio</th>
                                    <th>LGA population</th>
                                    <th>Median Income</th>
                                    <th>Median Age</th>
                                    <th>Median Mortgage</th>
                                    <th>Median Rent</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        </div>
                            """;
                html += "<script> let data = [" + tableData(dataTables) + "]; " +

                        """
                                     console.log(data);
                                    $('#result-table').DataTable( {
                                        data: data, 
                                        columns: [
                                            { data: 'lga_name' },
                                            { data: 'sumPopulation' },
                                            { data: 'percentage' },
                                            { data: 'lga_population' },
                                            { data: 'median_income' },
                                            { data: 'median_age' },
                                            { data: 'median_mortgage' },
                                            { data: 'median_rent' }
                                        ],
                                        "iDisplayLength": 25,
                                        "order": [],
                                        scrollX: true
                                    } );
                                """;

                html += "</script>";
            } else if ("State".equals(groupby)) {
                group = "group by state_code";
                JDBCConnection jdbc = new JDBCConnection();

                SQLquery = "select sum(population),round(sum(population)*100.0/sum(distinct lga_population),2) as percentage,state_code, lga_name, lga_population,  median_income,  median_age,  median_mortgage,  median_rent from data";
                ArrayList<DataTable> dataTables = jdbc.getDataTable(SQLquery, where, group, order);

                html += """
                        <div class="table">
                        <table id="result-table" class="hover" style="width:100%">
                            <thead>
                                <tr>
                                    <th>State</th>
                                    <th>Homeless Population</th>
                                    <th>Homeless Ratio</th>
                                    <th>LGA population</th>
                                    <th>Median Income</th>
                                    <th>Median Age</th>
                                    <th>Median Mortgage</th>
                                    <th>Median Rent</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        </div>
                            """;
                html += "<script> let data = [" + tableData(dataTables) + "]; " +

                        """
                                     console.log(data);
                                    $('#result-table').DataTable( {
                                        data: data, 
                                        columns: [
                                            { data: 'state' },
                                            { data: 'sumPopulation' },
                                            { data: 'percentage' },
                                            { data: 'lga_population' },
                                            { data: 'median_income' },
                                            { data: 'median_age' },
                                            { data: 'median_mortgage' },
                                            { data: 'median_rent' }
                                        ],
                                        "iDisplayLength": 25,
                                        "order": [],
                                        scrollX: true
                                    } );
                                """;

                html += "</script>";
            }


        } else if ("sum".equals(display)) {


            JDBCConnection jdbc = new JDBCConnection();


            SQLquery = "select sum(population) as homelessTotal, round(sum(population)*100.0/sum(distinct lga_population),2) as percentage, count(distinct lga_code) as lgaNumber, year from data";
            group = "group by year";
            ArrayList<SumData> sumDatas = jdbc.getSumData(SQLquery, where, group);


            html += """
                    <div class="table">
                    <table id="result-table" class="hover" style="width:100%">
                        <thead>
                            <tr>
                                <th>Homeless Population</th>
                                <th>Ratio</th>
                                <th>Total LGA Number</th>
                                <th>year</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    </div>
                        """;
            html += "<script>let data = [" + sumData(sumDatas) + "]; " +

                    """
                                console.log(data);
                                $('#result-table').DataTable( {
                                    data: data, 
                                    columns: [
                                        { data: 'homelessTotal' },
                                        { data: 'percentage' },
                                        { data: 'lgaNumber' },
                                        { data: 'year' }
                                    ],
                                    "iDisplayLength": 25,
                                    "order": [],
                                    scrollX: true
                                } );
                            """;

            html += "</script>";
        }
        html = html + """
                     <script type="text/javascript" src="homelessJS.js"></script>
                    """;
        context.html(html);
    }

    private String tableData(ArrayList<DataTable> dataTables) {
        String data = "";
        for (DataTable dataTable : dataTables) {
            data += "{\n\"sumPopulation\":\"" + dataTable.sumPopulation + "\",\n" +
                    "\"percentage\":\"" + dataTable.percentage + "%\",\n" +
                    "\"lga_name\":\"" + dataTable.lga_name + "\",\n" +
                    "\"state\":\"" + stateNameCovert(dataTable.stateCode) + "\",\n" +
                    "\"lga_population\":\"" + dataTable.lga_population + "\",\n" +
                    "\"median_income\":\"" + dataTable.median_income + "\",\n" +
                    "\"median_age\":\"" + dataTable.median_age + "\",\n" +
                    "\"median_mortgage\":\"" + dataTable.median_mortgage + "\",\n" +
                    "\"median_rent\":\"" + dataTable.median_rent + "\",\n" +
                    "},\n";

        }
        return data;
    }

    private String sumData(ArrayList<SumData> sumDatas) {
        String data = "";
        for (SumData sumData : sumDatas) {
            data += "{\n\"homelessTotal\":\"" + sumData.homelessTotal + "\",\n" +
                    "\"percentage\":\"" + sumData.percentage + "%\",\n" +
                    "\"lgaNumber\":\"" + sumData.lgaNumber + "\",\n" +
                    "\"year\":\"" + sumData.year + "\",\n" +
                    "},\n";

        }
        return data;
    }

    private String compareData(ArrayList<CompareData> compareDatas) {
        String data = "";
        for (CompareData compareData : compareDatas) {
            data += "{\n\"lgaName\":\"" + compareData.lgaName + "\",\n" +
                    "\"state\":\"" + stateNameCovert(compareData.stateCode) + "\",\n" +
                    "\"populationDif\":\"" + compareData.populationDif + "\",\n" +
                    "\"homelessDif\":\"" + compareData.homelessDif + "\",\n" +
                    "\"atRiskDif\":\"" + compareData.atRiskDif + "\",\n" +
                    "\"shift\":\"" + compareData.shift + "\",\n" +
                    "},\n";

        }
        return data;
    }

    private String perCompareData(ArrayList<PerCompareData> perCompareDatas) {
        String data = "";
        for (PerCompareData perCompareData : perCompareDatas) {
            data += "{\n\"lgaName\":\"" + perCompareData.lgaName + "\",\n" +
                    "\"state\":\"" + stateNameCovert(perCompareData.stateCode) + "\",\n" +
                    "\"homelessDif\":parseFloat(" + perCompareData.homelessDif + ").toFixed(2)+'%',\n" +
                    "\"atRiskDif\":parseFloat(" + perCompareData.atRiskDif + ").toFixed(2)+'%',\n" +
                    "\"shift\":parseFloat(" + perCompareData.shift + ").toFixed(2)+'%',\n" +
                    "},\n";

        }
        return data;
    }

    private String ageRangeCov(String ageRange) {
        switch (ageRange) {
            case "1":
                return "0-9 years old";
            case "2":
                return "10-19 years old";
            case "3":
                return "20-29 years old";
            case "4":
                return "30-39 years old";
            case "5":
                return "40-49 years old";
            case "6":
                return "50-59 years old";
            case "7":
                return "60 plus";
            case "8":
                return "unknown";
            default:
                return null;
        }
    }

    public static String stateNameCovert(String stateCode) {
        switch (stateCode) {
            case "1":
                return "New South Wales";
            case "2":
                return "Victoria";
            case "3":
                return "Queensland";
            case "4":
                return "South Australia";
            case "5":
                return "Western Australia";
            case "6":
                return "Tasmania";
            case "7":
                return "Northern Territory";
            case "8":
                return "Australian Capital Territory";
            case "9":
                return "Other";
            default:
                return null;
        }
    }
}





