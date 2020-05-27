package Util;

import java.sql.Date;
import java.time.LocalDate;

public class dateUtil {

    public static boolean olderThan2days(Object dateReservation) {
        Date _dateReserv = (Date) dateReservation;
        LocalDate dateReserv = _dateReserv.toLocalDate();
        LocalDate dateToday = LocalDate.now();
        if(dateReserv.compareTo(dateToday)>0) return false;
        else
        {
            int diff = dateToday.getDayOfYear()-dateReserv.getDayOfYear();
            if(diff>=2) return true;
            return false;
        }
    }
}
