package com.example.elouancustommod.api.generic;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;



@Retention(RUNTIME)
@Target(value = ElementType.FIELD)
public @interface ConfigurableItem {

	/**
	 * @return Human-readable object name that will appear in config, as part of
	 * comment on generated accessibility option. Specifically, it will look like
	 * "Whether or not {@link #value()} should be enabled."
	 */

	public String value() default "";

}
