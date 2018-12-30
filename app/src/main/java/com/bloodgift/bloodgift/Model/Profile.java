package com.bloodgift.bloodgift.Model;

import java.util.Calendar;
import java.util.Date;

public class Profile {

    //constantes
    private static final Integer minage = 18;
    private static final Integer maxage = 60;
    private static final Integer minpoids = 50;

    //propriétés
    private Date dateProfile;
    private Calendar dateSang;
    private Calendar datePlaque;
    private Calendar datePlasma;
    private Integer weight;
    private Integer age;
    private Integer sex;
    private String message;

    public Profile(Date dateProfile, Calendar dateSang, Calendar datePlaque, Calendar datePlasma, Integer age, Integer weight, Integer sex) {
        this.dateProfile = dateProfile;
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

        Integer réussite = 1;
        message = "<h1><font color=\"#DF0101\">Désolé..</font></h1>";
        if (age<18) {
            réussite=0;
            message += "<p>Il faut être agé d'au moins 18 ans pour pouvoir donner son sang</p>";
        }
        if (age>60) {
            réussite = 0;
            if (dateSang.get(Calendar.YEAR) == 2016 && dateSang.get(Calendar.MONTH) == 0 && dateSang.get(Calendar.DAY_OF_MONTH) == 1) {
                message += "<p>Il faut avoir moins de 60 pour faire son premier don</p>";
            }else {
                message += "<p>Après 60 ans, chaque don et soumis à l'aprobation de votre medecin</p>";
            }
        }
        if (weight<50) {
            réussite=0;
            message += "<p>vous êtes trop léger, il faut peser au moins 50kg pour donner son sang</p>";
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
                    if (réussitePlasma ==1) {
                        message += "<p>Cependant, vous pouvez encore donner vos plaquettes ou votre plasma !</p>";
                    } else {
                        message += "<p>Cependant, vous pouvez encore donner vos plaquettes !</p>";
                    }
                }
            } else {
                if (réussitePlaquette == 0){
                    if (réussitePlasma == 0) {
                        message += "<p>Cependant, vous pouvez encore donner votre sang !</p>";
                    } else {
                        message += "<p>Cependant, vous pouvez encore donner votre sang ou votre plasma !</p>";
                    }
                } else {
                    if (réussitePlasma == 0){
                        message += "<p>Cependant, vous pouvez encore donner votre sang, ou vos plaquettes !</p>";
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
        }


    }

    public boolean donSangTropProche(){
        Calendar today = Calendar.getInstance();
        //On ne peut pas donner son sang avant 8 semaines après un don de sang, 4 semaines après un don de plaquettes, 2 semaines après un don de plasma
        if (daysBetween(today,dateSang)<56 || daysBetween(today,datePlaque)<28 || daysBetween(today,datePlasma)< 14){
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
}
