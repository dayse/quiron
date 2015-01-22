package DAO.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Classe de declaração da anotação @RecuperaCojunto
 * que é utilizada dentro dos DAOs.
 * 
 * @author bruno.oliveira (Atualização)
 *
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface RecuperaConjunto
{}