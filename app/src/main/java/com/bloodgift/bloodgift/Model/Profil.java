package com.bloodgift.bloodgift.Model;

import java.util.Date;

public class Profil {

    //constantes
    private static final Integer minage = 18;
    private static final Integer maxage = 60;
    private static final Integer minpoids = 50;

    //propriétés
    private Date dateProfil;
    private Integer poids;
    private Integer age;
    private Integer sexe;
    private String message;

    public Profil(Date dateProfil, Integer age, Integer poids, Integer sexe) {
        this.dateProfil = dateProfil;
        this.poids = poids;
        this.age = age;
        this.sexe = sexe;
        this.resultatTest();
    }

    public Date getDateProfil() {
        return dateProfil;
    }

    public Integer getPoids() {
        return poids;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSexe() {
        return sexe;
    }

    public String getMessage() {
        return message;
    }

    private void resultatTest(){

        if (age<18) {
            message = "Il faut être agé d'au moins 18 ans pour pouvoir donner son sang";
        } else if (age>60){
            message = "Il faut avoir moins de 60 pour son premier don mais vous pouvez donner jusqu'à 70 ans sous reserve de l'accord d'un medecin";
        } else if (poids<50) {
            message = "vous êtes trop léger, il faut peser au moins 50kg pour donner son sang";
        } else {
            message = "Félicitation, vous pouvez donner votre sang !";
        }
    }
}
