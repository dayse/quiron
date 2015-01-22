package DAO.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Classe de declara��o da anota��o @RecuperaLista
 * que � utilizada dentro dos DAOs.
 * 
 * A anota��o @RecuperaLista trata de lista n�o paginada
 * de informa��es.
 * 
 * @author bruno.oliveira (Atualiza��o)
 *
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface RecuperaLista
{}
