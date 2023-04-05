package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Mainpage implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<!DOCTYPE html>"
                + "<html id='mainpage-bg' lang='en'>";
        // icon cite from: https://www.demantelez.com/static/img/homeless.b83a206.png
        html = html + """

                <head>
                    <meta charset='UTF-8'>
                    <title>Ending Homelessness</title>
                    <link rel='stylesheet' href='homelessStyle.css'>
                </head>

                <body>
                <header>
                    <div class='topnav'>
                    <a href='/'><h2>Main Page</h2></a>    
                    <a href='/dataentry.html'><h2>Data Exploring</h2></a>
                    </div>
                    <div class="icon">
                        <a href="http://localhost:7001/">
                            <img src='homeless.png' height=100em width=100em>
                        </a>
                    </div> 
                </header>

            <div id="mp-question"><span>Do you know these data?</span></div>
            <div class='mp-table'>
                <table>
                    <tr>
                        <th>Year</th>
                        <th>Homeless Population (Australia)</th>
                        <th>Total LGA Number</th>
                    </tr>
                    """;
        JDBCConnection jdbc = new JDBCConnection();


        ArrayList<SumData> sumDatas = jdbc.getSumData();

        for (SumData mainpageData : sumDatas) {
            html = html + "<tr>";
            html = html + "<td>" + mainpageData.year + "</td>";
            html = html + "<td>" + mainpageData.homelessTotal + "</td>";
            html = html + "<td>" + mainpageData.lgaNumber + "</td>";
            html = html + "</tr>";
        }


        html = html + "</table> </div>";


        html = html + """
                <div class="mp-grid-2">  
                    <div id="mp-facts-grid">
                        <div id="mp-facts-grid-1"><p>Brisbane has the highest recorded homeless individuals at<br> <span>3748</span></p></div>
                        <div id="mp-facts-grid-2"><p>The ratio of female that at the risk to become homeless is<br> <span>2 out of 3</span></p> </div>
                        <div id="mp-facts-grid-3"><p>The ratio of homeless that are under 30 years old is<br> <span>57%</span> </p></div>
                    </div>
                </div>
                """;
        context.html(html);
    }

}
