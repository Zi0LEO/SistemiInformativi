package org.example;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);


    }
}