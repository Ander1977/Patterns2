package ru.netology;

import com.github.javafaker.Faker;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class RegistrationInfo {
        private RegistrationInfo() {
        }

        public static String userPassword(String Locale) {
            Faker faker = new Faker(new Locale(Locale));
            return faker.internet().password();
        }

        public static String userName(String Locale) {
            Faker faker = new Faker(new Locale(Locale));
            return faker.name().username();
        }

        public static UserInfo generateUserInfo(String Locale, boolean isBlocked) {
            return new UserInfo(
                    userName(Locale),
                    userPassword(Locale),
                    (isBlocked) ? "blocked" : "active");
        }
    }
}