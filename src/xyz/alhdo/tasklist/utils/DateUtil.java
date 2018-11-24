package xyz.alhdo.tasklist.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static Date transformLocalDateToDate(LocalDate localDate){
       Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
       return Date.from(instant);
    }

    public static java.sql.Date transformUtilToSql(Date date){
       return new java.sql.Date(date.getTime());
    }
}
