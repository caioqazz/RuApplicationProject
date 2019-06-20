package br.ufop.ruapplicationpassivemvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

    public static String simpleFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateObj = new Date();

        try {
            dateObj = format.parse(date);
            SimpleDateFormat humanFormat = new SimpleDateFormat("dd/MM/yy");
            return humanFormat.format(dateObj);
        } catch (ParseException e1) {

        }

        return "";
    }

}
