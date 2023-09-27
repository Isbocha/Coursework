package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    @Value
    public static class AuthInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String name;
        private String cvc;
    }

    public static String getApprovedCardNumber() {
        return ("1111 2222 3333 4444");
    }

    public static String getDeclinedCardNumber() {
        return ("5555 6666 7777 8888");
    }

    public static String getInvalidCardNumber() {
        return ("0000 0000 0000 0000");
    }

    public static String generateOneARandomNumber() {
        return faker.numerify("#");
    }

    public static String generateTwoARandomNumber() {
        return faker.numerify("#");
    }

    public static String generateInvalidCardNumberWithEightDigits() {
        return faker.numerify("#### ####");
    }

    public static String generateInvalidCardNumberWithFifteenDigits() {
        return faker.numerify("#### #### #### ###");
    }

    public static String generateInvalidCardNumberWithSeventeenDigits() {
        return faker.numerify("#### #### #### #### #");
    }

    public static String generateMonth(int shift) {
        String pattern = "MM";
        String date = LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern(pattern));
        return date;
    }

    public static String generateYear(int shift) {
        String pattern = "yy";
        String date = LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern(pattern));
        return date;
    }

    public static String generateFullName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name;
    }

    public static String generatePartOfTheName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().lastName();
        return name;
    }

    public static String getRandomValidCVC() {
        return (faker.numerify("###"));
    }

}