package app.config;

import io.github.cdimascio.dotenv.Dotenv;

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
        // app.env is used for terminal access (change of .env file).
        // For example running the program in terminal with test as environement will
        // load .env.test. If there's no test available it'll fall back to our environement attribute.
        String environmentLoad = System.getProperty("set.env", environment);
        String filename = "src/main/resources/.env." + environmentLoad;

        // Load (I/O) the .env.development file
        dotenv = Dotenv.configure()
                .filename(filename)
                .ignoreIfMissing()
                .load();

        // Set value(s)
        TMDB_KEY = dotenv.get("TMDB_KEY");
        // .... more?

        if (TMDB_KEY == null) {
            System.out.println("No TMDB_KEY found in: " + filename);
        }

    }

    // _________________________________________________

    public static String getTmdbKey(){
        return TMDB_KEY;
    }

}