package de.maxwell.qa.infrastructure.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ejb.ApplicationException;
import javax.enterprise.inject.Stereotype;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Stereotype
@ApplicationException
@Retention(RUNTIME)
@Target(TYPE)
public @interface Repository {

}