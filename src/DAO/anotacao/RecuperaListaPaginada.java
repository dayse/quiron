package DAO.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Classe de declara��o da anota��o @RecuperaListaPaginada
 * que � utilizada dentro dos DAOs.
 * 
 * Essa classe � respons�vel pelas listas paginadas do sistema.
 * Cada p�gina da lista, por default, possui dez itens. Esse valor
 * pode ser alterado no momento da declara��o da anota��o.
 * 
 * @author bruno.oliveira (Atualiza��o)
 *
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface RecuperaListaPaginada
{
    int tamanhoPagina() default 10;	
}
