package Util;

import java.sql.Date;
import java.time.LocalDate;

public class dateUtil {

    public static boolean olderThan2days(LocalDate dateDepart) {
        LocalDate dateToday = LocalDate.now();
        if(dateDepart.compareTo(dateToday)<0) return false;
        else
        {
            int diff = dateDepart.getDayOfYear()-dateToday.getDayOfYear();
            if(diff<=2) return false;
            return true;
        }
    }
}
