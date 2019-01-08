package com.bloodgift.bloodgift.Model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.bloodgift.bloodgift.AlarmReceiver;
import com.bloodgift.bloodgift.Controller.SettingsController;
import com.bloodgift.bloodgift.MyApplication;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class Profile {

    private static Context context;

    //propriétés
    private Date dateProfile;
    private Calendar dateSang;
    private Calendar datePlaque;
    private Calendar datePlasma;
    private Integer weight;
    private Integer age;
    private Integer sex;
    private String message;

    public Profile(Date dateProfile, Context context, Calendar dateSang, Calendar datePlaque, Calendar datePlasma, Integer age, Integer weight, Integer sex) {
        this.dateProfile = dateProfile;
        this.context = MyApplication.getAppContext();
        this.dateSang = dateSang;
        this.datePlaque = datePlaque;
        this.datePlasma = datePlasma;
        this.weight = weight;
        this.age = age;
        this.sex = sex;
        this.resultatTest();
    }

    public Date getDateProfile() {
        return dateProfile;
    }

    public Calendar getDateSang() { return dateSang; }

    public Calendar getDatePlaque() { return datePlaque; }

    public Calendar getDatePlasma() { return datePlasma; }

    public Integer getWeight() {
        return weight;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSex() {
        return sex;
    }

    public String getMessage() {
        return message;
    }

    private void resultatTest(){

        //Initialisé à 1, la variable retombe sur 0 au moindre problème.
        Integer réussite = 1;
        message = "<h1><font color=\"#DF0101\">Désolé..</font></h1>";
        if (age<18) {
            réussite=0;
            message += "<p>Il faut être agé d'au moins 18 ans pour pouvoir donner son sang</p>";
        }
        if (age>60) {
            réussite = 0;
            //Test premier don (si aucun don n'a été enregistré, la date par default est le 1/1/2016)
            if (dateSang.get(Calendar.YEAR) == 2016 && dateSang.get(Calendar.MONTH) == 0 && dateSang.get(Calendar.DAY_OF_MONTH) == 1) {
                message += "<p>Il faut avoir moins de 60 pour faire son premier don</p>";
            }else {
                message += "<p>Après 60 ans, chaque don est soumis à l'aprobation de votre medecin</p>";
                //cas spécial de semi-réussite
                réussite=2;

            }
        }
        if (weight<50) {
            réussite=0;
            message += "<p>Vous êtes trop léger, il faut peser au moins 50kg pour donner son sang</p>";
        }


        int réussiteSang = 1;
        int réussitePlaquette = 1;
        int réussitePlasma = 1;
        if (donSangTropProche()){
            message += "<p>Vous ne pouvez donner votre sang que toutes les 8 semaines</p>";
            réussiteSang=0;
        }
        if (donPlaquetteTropProche()){
            message += "<P>Vous ne pouvez donner vos plaquettes que toutes les 4 semaines</p>";
            réussitePlaquette=0;
        }
        if (donPlasmaTropProche()){
            message += "<p>Vous ne pouvez donner votre plasma que toutes les 2 semaines</p>";
            réussitePlasma=0;
        }

        if (réussite == 1) {
            if (réussiteSang == 0) {
                if (réussitePlaquette == 0) {
                    if (réussitePlasma == 1) {
                        message += "<p>Cependant, vous pouvez encore donner votre plasma !</p>";
                    }
                } else {
                    if (réussitePlasma == 1) {
                        message += "<p>Cependant, vous pouvez encore donner vos plaquettes ou votre plasma !</p>";
                    }
                }
            }
        }

        if (réussiteSang == 0 || réussitePlaquette == 0 || réussitePlasma == 0 ){
            réussite = 0;
        }


        if (réussite ==1){
            message = "<h1><font color=\"#DF0101\">Félicitation !</font></h1>" +
                    "<h1>vous pouvez donner votre sang !</h1>";
        } else if (réussite ==2){
            message = "<h1><font color=\"#DF0101\">Félicitation !</font></h1>" +
                    "<p>vous pouvez donner votre sang !\nCependant, après 60 ans, chaque don est soumis à l'aprobabtion de votre medecin</p>";
        }


    }

    public boolean donSangTropProche(){
        Calendar today = Calendar.getInstance();
        //On ne peut pas donner son sang avant 8 semaines après un don de sang, 4 semaines après un don de plaquettes, 2 semaines après un don de plasma
        if (daysBetween(today,dateSang)<56){
            seFaireNotifier(56 - daysBetween(today,dateSang));
            return true;
        } else if (daysBetween(today,datePlaque)<28){
            seFaireNotifier(28 - daysBetween(today,datePlaque));
            return true;
        } else if (daysBetween(today,datePlasma)< 14){
            seFaireNotifier(14 - daysBetween(today,datePlasma));
            return true;
        }
        return false;
    }

    public boolean donPlaquetteTropProche(){
        Calendar today = Calendar.getInstance();
        //On ne peut pas donner ses plaquettes avant 4 semaines après un don de sang, 4 semaines après un don de plaquettes, 2 semaines après un don de plasma
        if (daysBetween(today,datePlaque)<28 || daysBetween(today,dateSang)<28 || daysBetween(today,datePlasma)<14){
            return true;
        }
        return false;
    }

    public boolean donPlasmaTropProche(){
        Calendar today = Calendar.getInstance();
        //On ne peut pas donner son sang avant 2 semaines après un don de sang, 2 semaines après un don de plaquettes, 2 semaines après un don de plasma
        if (daysBetween(today,datePlasma)<14 || daysBetween(today,dateSang)<14 || daysBetween(today,datePlaque)<14){
            return true;
        }
        return false;
    }

    public static int daysBetween(Calendar day1, Calendar day2){
        Calendar dayOne = (Calendar) day1.clone(),
                dayTwo = (Calendar) day2.clone();

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
        }
    }

    public static void seFaireNotifier(Integer joursAvantDonSang){

        SettingsController controller = new SettingsController(context);
        if (controller.getNotifSang() != null){
            if(controller.getNotifSang()==1){

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                calendar.add(Calendar.DAY_OF_MONTH,joursAvantDonSang);

                Date date = calendar.getTime();

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmReceiver.class);
                intent.putExtra("Alarm", true);
                PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

                alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
            }
        }
    }
}
