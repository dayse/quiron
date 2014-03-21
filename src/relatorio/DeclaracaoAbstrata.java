package relatorio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * Implementa os metodos que recuperam o arquivo compilado Jasper.
 * Implementa a interface Relatorio.java.
 * (não precisou implementar o metodo gerarRelatorio porque esta é uma classe abstrata).
 * @author walanem
 *
 */
public abstract class DeclaracaoAbstrata implements Relatorio {

    protected InputStream recuperaJasper(String nomeJasper) {
        
    	FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();

        return servletContext.getResourceAsStream(nomeJasper);		//Recupera o arquivo com extensão .jasper
    }

    protected InputStream recuperaJasperLocalmente(String nomeJasper) throws FileNotFoundException {

        InputStream jasper = new FileInputStream(nomeJasper);

        return jasper;
    }
}
