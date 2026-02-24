package app;

import app.util.PopulateDB;

public class Main {

    // Attributes

    // ______________________________________________

    public static void main(String[] args) {
        PopulateDB.populateRoles();
        PopulateDB.populateLanguages();
    }

}