package conversores;

import java.text.MessageFormat;

import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import util.Utilitario;

/**
 * 
 * Classe que disponibiliza recursos para a conversão de objetos do tipo Double.
 * 
 * @author marques
 * 
 */
public class ConversorDouble implements Converter {

	/**
	 * 
	 * Construdor da classe, atualmente apenas imprimindo um aviso de que o
	 * conversor for instanciado.
	 * 
	 * @author marques
	 * 
	 */
	public ConversorDouble() {
		System.out.println(">>>>>>>> Instanciou o ConversorDouble");
	}

	/**
	 * 
	 * Método que obtém um objeto através da combinação de um contexto, um
	 * componente e uma String passados através de parâmetros.
	 * 
	 * @throws ConverterException
	 *             - Retorna uma exception caso haja algum problema durante a
	 *             conversão.
	 * 
	 * @author marques
	 * 
	 */
	public Object getAsObject(FacesContext contexto, UIComponent componente,
			String novoValor) throws ConverterException {
		System.out
				.println(">>>>>>>>>>>>>>>>>>> Executou getAsObject de ConversorDouble");
		try {
			Double valor = null;
			if (novoValor != null && novoValor.trim().length() > 0) {
				System.out
						.println(">>>>>>>>>>>>>>>>>>> Executou getAsObject de ConversorDouble <<<<<<<<<<<<<<<<<<<<<<");
				valor = Utilitario.strToDouble(novoValor);
			}
			return valor;
		} catch (Exception e) {
			// recuperando o texto da mensagem a partir do message bundle
			FacesContext context = FacesContext.getCurrentInstance();
			Application app = context.getApplication();
			String appBundleName = app.getMessageBundle();

			ResourceBundle bundle = ResourceBundle.getBundle(appBundleName);
			String msg = bundle.getString("erroConversaoDouble");

			String campo = bundle.getString(componente.getId());
			campo = "'" + campo + "'";

			Object[] params = { campo };
			MessageFormat formatter = new MessageFormat(msg);
			String texto = formatter.format(params);

			FacesMessage mensagem = new FacesMessage(texto);

			throw new ConverterException(mensagem);
		}
	}

	/**
	 * 
	 * Método que obtém uma String através da combinação de um contexto, um
	 * componente e um objeto passados através de parâmetros.
	 * 
	 * @throws ConverterException
	 *             - Retorna uma exception caso haja algum problema durante a
	 *             conversão.
	 * 
	 * @author marques
	 * 
	 */
	public String getAsString(FacesContext contexto, UIComponent componente,
			Object valor) throws ConverterException {
		System.out
				.println(">>>>>>>>>>>>>>>>>>> Executou getAsString de ConversorDouble");
		return Utilitario.doubleToStr((Double) valor);
	}
}