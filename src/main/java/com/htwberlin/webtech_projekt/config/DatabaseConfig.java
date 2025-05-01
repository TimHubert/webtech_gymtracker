package com.htwberlin.webtech_projekt.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("Umgebungsvariablen nicht korrekt gesetzt: " +
                    "DB_URL=" + URL + ", DB_USER=" + USER + ", DB_PASSWORD=" + PASSWORD);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
