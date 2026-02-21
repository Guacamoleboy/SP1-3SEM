package app.config;

public class HibernateEnvironment {

    // Attributes
    private static Boolean isTest = false;

    // _______________________________________________________________

    public static void setTest(Boolean test) {
        isTest = test;
    }

    // _______________________________________________________________

    public static Boolean getTest() {
        return isTest;
    }

}