package com.gav.j17.ptc.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author alex.gera
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
@Documented 
public @interface Loggable {
 //for slf4j
}

