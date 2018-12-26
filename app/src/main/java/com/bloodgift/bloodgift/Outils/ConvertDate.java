package com.bloodgift.bloodgift.Outils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ConvertDate {

    public static Calendar ConvertStringToCalendar(String uneDate){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(uneDate));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance();
    }
}
