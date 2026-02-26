package app;

import app.config.PoolConfig;

public class Main {

    // Attributes

    // ________________________________________________

    public static void main(String[] args) {
        new Router().run();
        PoolConfig.getExecutor().shutdown();
    }

}