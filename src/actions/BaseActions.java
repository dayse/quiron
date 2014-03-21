package actions;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import actions.controle.SessaoDoUsuario;

/**
 * 
 * Superclasse das classes do pacote actions.
 * 
 * @author bruno.oliveira (Atualiza��o)
 * 
 */
public class BaseActions {

	// Componentes de Controle
	private static final String CAMINHO_BUNDLE_MENSAGENS = "mensagens";
	protected SessaoDoUsuario sessaoUsuarioCorrente;

	/**
	 * 
	 * Construtor que carrega a sess�o do usu�rio.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public BaseActions() {
		sessaoUsuarioCorrente = (SessaoDoUsuario) this
				.getManagedBean("sessaoDoUsuario");
	}

	/**
	 * 
	 * M�todo usado para inserir um objeto e uma chave a sess�o.
	 * 
	 * @param chave
	 *            - String - Chave String que identifica o Objeto.
	 * @param valor
	 *            - Object - O objeto a ser inserido.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	protected void inserirObjetoNaSessao(String chave, Object valor) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(chave, valor);
	}

	/**
	 * 
	 * M�todo usado para recuperar um objeto a partir de uma String.
	 * 
	 * @param chave
	 *            - String - Chave String que identifica o Objeto.
	 * @return Retorna o objeto correspondente a chave passada como par�metro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public Object recuperarObjetoDaSessao(String chave) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(chave);
	}

	/**
	 * 
	 * Insere o objeto na requisi��o
	 * 
	 * @param chave
	 *            - String - Chave String que identifica o Objeto.
	 * @param valor
	 *            - Object - O objeto a ser inserido.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 */
	public void inserirObjetoNaRequest(String chave, Object valor) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put(chave, valor);
	}

	/**
	 * 
	 * Recupera um determinado objeto da requisi��o atrav�s da sua chave.
	 * 
	 * @param chave
	 *            - String - Chave String que identifica o Objeto.
	 * @return Retorna o objeto correspondente a chave passada como par�metro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public Object recuperarObjetoDaRequest(String chave) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestMap().get(chave);
	}

	/**
	 * 
	 * M�todo usado para renderizar mensagens de informa��o do sistema.
	 * Geralmente, � utilizado para a rederiza��o de mensagens de sucesso.
	 * 
	 * @param messageKey
	 *            Recebe como par�metro uma mensagem cadastrada no arquivo
	 *            mensagens.properties.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	protected void info(String messageKey) {

		FacesMessage mensagemSucesso = new FacesMessage();
		mensagemSucesso.setSummary(getMensagemBundled(messageKey));
		mensagemSucesso.setSeverity(FacesMessage.SEVERITY_INFO);

		FacesContext.getCurrentInstance().addMessage(null, mensagemSucesso);
	}

	/**
	 * 
	 * M�todo usado para renderizar mensagens de erro do sistema.
	 * 
	 * @param messageKey
	 *            Recebe como par�metro uma mensagem cadastrada no arquivo
	 *            mensagens.properties.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	protected void error(String messageKey) {

		FacesMessage mensagemErro = new FacesMessage();
		mensagemErro.setSummary(getMensagemBundled(messageKey));
		mensagemErro.setSeverity(FacesMessage.SEVERITY_ERROR);

		FacesContext.getCurrentInstance().addMessage(null, mensagemErro);
	}

	/**
	 * 
	 * M�todo usado para renderizar mensagens de alerta do sistema.
	 * 
	 * @param messageKey
	 *            Recebe como par�metro uma mensagem cadastrada no arquivo
	 *            mensagens.properties.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	protected void warn(String messageKey) {

		FacesMessage mensagemWarning = new FacesMessage();
		mensagemWarning.setSummary(getMensagemBundled(messageKey));
		mensagemWarning.setSeverity(FacesMessage.SEVERITY_WARN);

		FacesContext.getCurrentInstance().addMessage(null, mensagemWarning);
	}

	/**
	 * 
	 * Obt�m um ManagedBean j� instanciado em outro ManagedBean.
	 * 
	 * @param nomeManagedBean
	 *            - String com o nome do ManagedBean definido no FacesConfig.
	 * @return ManagedBean - Retorna a inst�ncia do objeto procurado.
	 * 
	 * @author Walanem
	 * 
	 */
	protected Object getManagedBean(String nomeManagedBean) {

		FacesContext context = FacesContext.getCurrentInstance();

		return context.getELContext().getELResolver().getValue(
				context.getELContext(), null, nomeManagedBean);
	}

	/**
	 * 
	 * Captura um Mensage Bundled atrav�s de uma chave.
	 * 
	 * @param key
	 *            - Chave que identifica a mensage budled
	 * @return Retorna um Menssage Bundled
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	protected String getMensagemBundled(String key) {
		return this.getMessageResourceString(CAMINHO_BUNDLE_MENSAGENS, key,
				null, new Locale("pt-BR"));
	}

	/**
	 * 
	 * M�todo que captura um Message Resource String a partir de uma s�rie de
	 * par�metros.
	 * 
	 * @param bundleName
	 *            Nome do bundle
	 * @param key
	 *            Chave de identifica��o
	 * @param params
	 *            Lista de par�metros
	 * @param locale
	 *            Objeto do tipo Locale
	 * @return Message Resource String
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public String getMessageResourceString(String bundleName, String key,
			Object params[], Locale locale) {

		String text = null;

		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale,
				getCurrentClassLoader(params));

		try {
			text = bundle.getString(key);
		} catch (MissingResourceException e) {
			text = "?? mensagem \"" + key + "\" n�o encontrada ??";
		}

		if (params != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}

		return text;
	}

	/**
	 * 
	 * Captura um Current Class Loader
	 * 
	 * @param defaultObject
	 *            Um objeto
	 * @return Current Class Loader
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	protected static ClassLoader getCurrentClassLoader(Object defaultObject) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = defaultObject.getClass().getClassLoader();
		}
		return loader;
	}
}