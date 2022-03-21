package io.beesports.domain.enums;

public enum Role {

    ADC("adc"),
    SUPPORT("sup"),
    MID("mid"),
    TOP("top"),
    JUNGLE("jun");

    private String name;

    Role(String name){
        this.name = name;
    }
}
