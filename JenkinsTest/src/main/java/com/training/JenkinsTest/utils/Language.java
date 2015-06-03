package com.training.JenkinsTest.utils;

import java.util.Locale;

public enum Language {
    EN(Locale.ENGLISH),
    FR(Locale.FRENCH),
    IT(Locale.ITALIAN),
    RU(Locale.forLanguageTag("ru-RU")),
    UA(Locale.forLanguageTag("uk-UA")),
    DE(Locale.GERMAN);

    private final Locale current;

    Language(Locale locale) {
        current = locale;
    }

    public Locale toLocale() {
        return current;
    }

    public static Language getCurrentLanguage() {
        return Language.valueOf(System.getProperty("lang", "en").toUpperCase());
    }
    
}
