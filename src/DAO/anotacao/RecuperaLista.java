package DAO.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Classe de declaração da anotação @RecuperaLista
 * que é utilizada dentro dos DAOs.
 * 
 * A anotação @RecuperaLista trata de lista não paginada
 * de informações.
 * 
 * @author bruno.oliveira (Atualização)
 *
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface RecuperaLista
{}
