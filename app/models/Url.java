package models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

/**
 * Created by Thomas on 15/04/2015.
 */
public @interface Url {

    String value();
    Parameter[] parameters() default {};

    HttpMethod method();
}
