package io.github.ripetangerine.todolist.utils;


import java.util.Locale;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
public final class ProjectConstants {

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static final Locale LOCALE = new Locale.Builder().setLanguage("ko").setRegion("KO").build();

    private ProjectConstants() {

        throw new UnsupportedOperationException();
    }

}