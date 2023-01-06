package com.elba.proficiencytest.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDate integerToLocalDate(int date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return LocalDate.parse(String.valueOf(date), dateFormatter);
    }

}
