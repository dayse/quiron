package DAO.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Classe de declara��o da anota��o @RecuperaCojunto
 * que � utilizada dentro dos DAOs.
 * 
 * @author bruno.oliveira (Atualiza��o)
 *
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface RecuperaConjunto
{}