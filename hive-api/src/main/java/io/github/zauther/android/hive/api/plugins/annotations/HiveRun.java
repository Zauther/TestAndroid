package io.github.zauther.android.hive.api.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HiveRun {
    HiveThreadType run() default HiveThreadType.immediate;
}
