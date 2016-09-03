package pl.kielce.tu.worldyouthday;

import org.flywaydb.core.Flyway;

public class Migrator {

    public static void main(String[] args) {

        final String url = System.getenv("JDBC_DATABASE_URL");
        final String user = System.getenv("JDBC_DATABASE_USERNAME");
        final String password = System.getenv("JDBC_DATABASE_PASSWORD");

        Flyway flyway = new Flyway();
        flyway.setDataSource(url, user, password);
        flyway.migrate();
    }

}
