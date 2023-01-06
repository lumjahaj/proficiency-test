package com.elba.proficiencytest.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDate integerToLocalDate(int date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return LocalDate.parse(String.valueOf(date), dateFormatter);
    }

    public static String localDateToString(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : null;
    }

}
