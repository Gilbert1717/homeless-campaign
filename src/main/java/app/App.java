package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;


/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the 
 * Javalin HTTP Server and our web application.
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class App {

    public static final int         JAVALIN_PORT    = 7001;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7001
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            
            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);


        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // Note in this example we must add Movies Type as a GET and a POST!
        
        // All webpages are listed here as GET pages
        // app.get(missionStatement.URL, new missionStatement());
        app.get(Mainpage.URL, new Mainpage());
        // app.get(IndividualLga.URL, new IndividualLga());
         app.get(DataEntry.URL, new DataEntry());
        //  app.get(LGAData.URL, new LGAData());
        //  app.get(changeInHomelessness.URL, new changeInHomelessness());

        // Add / uncomment POST commands for any pages that need web form POSTS
        // app.post(PageIndex.URL, new PageIndex());
        // app.post(missionStatement.URL, new missionStatement());
        app.post(Mainpage.URL, new Mainpage());
        // app.post(IndividualLga.URL, new IndividualLga());
         app.post(DataEntry.URL, new DataEntry());
        //  app.post(LGAData.URL, new LGAData());
        //  app.post(changeInHomelessness.URL, new changeInHomelessness());
    }

}
