package io.github.zauther.hive.hybrid.jsbridge.anno.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginName {
    String namespace() default "";
    String[] alt() default {};
}
