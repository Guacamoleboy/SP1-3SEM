package app.config;

import io.github.cdimascio.dotenv.Dotenv;

// Created by: Guacamoleboy
// ________________________
// Last updated: 24/02-2026
// By: Guacamoleboy

public class DotEnv {

    // Attributes
    private static final Dotenv dotenv;
    private static final String TMDB_KEY;
    private static final String environment = "development";

    // _________________________________________________
    // Usage:
    // ______
    // DotEnv.getTmdbKey(). Returns a String.

    static {

        // Environment setup + file definition
        // set.env is used for terminal access (change of .env file).
        // For example running the program in terminal with test as environement will
        // load .env.test. If there's no test available it'll fall back to our environement attribute.

        String environmentLoad = System.getProperty("set.env", environment);
        String filename = ".env." + environmentLoad;

        // Load (I/O) the .env.development file
        dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .filename(filename)
                .ignoreIfMissing()
                .load();

        // Set value(s)
        TMDB_KEY = dotenv.get("TMDB_KEY");
        // .... more?

        if (TMDB_KEY == null) {
            System.out.println("No TMDB_KEY found in: src/main/resources/" + filename);
        }

    }

    // _________________________________________________

    public static String getTmdbKey(){
        return TMDB_KEY;
    }

}