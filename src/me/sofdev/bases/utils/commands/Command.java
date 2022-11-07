package me.sofdev.bases.utils.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by SofDev w/Apreciada
 * 14/06/2022 - 02:52:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();

    String permission() default "";

    String noPerm() default "No permission!";

    String[] aliases() default {};

    String description() default "";

    String usage() default "";

    boolean inGameOnly() default false;
}
