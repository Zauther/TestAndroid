package io.github.zauther.android.hive.api.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface HiveParam {
    String value() default "";

    int intDefault() default 0;

    float floatDefault() default 0.0F;

    boolean booleanDefault() default false;

    double doubleDefault() default 0.0D;

    long longDefault() default 0L;

    String stringDefault() default "";

    boolean required() default false;
}
