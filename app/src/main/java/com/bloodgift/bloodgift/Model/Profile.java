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
    private Integer weight;
    private Integer age;
    private Integer sex;
    private String message;

    public Profile(Date dateProfile, Calendar dateSang, Integer age, Integer weight, Integer sex) {
        this.dateProfile = dateProfile;
        this.dateSang = dateSang;
        this.weight = weight;
        this.age = age;
        this.sex = sex;
        this.resultatTest();
    }

    public Date getDateProfile() {
        return dateProfile;
    }

    public Calendar getDateSang() { return dateSang; }

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

        if (age<18) {
            message = "Il faut être agé d'au moins 18 ans pour pouvoir donner son sang";
        } else if (age>60){
            message = "Il faut avoir moins de 60 pour son premier don mais vous pouvez donner jusqu'à 70 ans sous reserve de l'accord d'un medecin";
        } else if (weight<50) {
            message = "vous êtes trop léger, il faut peser au moins 50kg pour donner son sang";
        } else {
            message = "Félicitation, vous pouvez donner votre sang !";
        }
    }
}
