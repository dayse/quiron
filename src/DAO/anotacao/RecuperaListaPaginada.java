package DAO.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Classe de declaração da anotação @RecuperaListaPaginada
 * que é utilizada dentro dos DAOs.
 * 
 * Essa classe é responsável pelas listas paginadas do sistema.
 * Cada página da lista, por default, possui dez itens. Esse valor
 * pode ser alterado no momento da declaração da anotação.
 * 
 * @author bruno.oliveira (Atualização)
 *
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface RecuperaListaPaginada
{
    int tamanhoPagina() default 10;	
}
