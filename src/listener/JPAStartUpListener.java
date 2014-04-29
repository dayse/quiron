package listener;     


import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import modelo.Usuario;

import com.google.gson.Gson;

import util.Constantes;
import util.JPAUtil;
import util.JsonConfigLoader;

/**
 * Classe relativa ao listener da aplicacao.
 * Este listener � definido no web.xml.
 * Isso significa que este objeto vai ser criado no start up do tomcat.
 * 
 * @author daysemou
 *
 */
public class JPAStartUpListener implements ServletContextListener {
	
	public JPAStartUpListener() {
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		System.out.println(">>>>>>>>>>>>>> Criando o Entity Manager Factory");
		
		//Este m�todo vai fazer com que seja criado o entity manager factory no startup do tomcat e n�o quando for 
		//enviada a primeira requisi�ao 
		JPAUtil.JPAstartUp();
		System.out.println(">>>>>>>>>>>>>> Criou o Entity Manager Factory");
		
		//---------------------- Bloco de inicializa��o de constantes de Relat�rios ----------------------------------------------------------
		Constantes.CAMINHO_RELATORIOS_GERADOS = servletContextEvent.getServletContext().getRealPath("/privado/gerado/") + "/";
        Constantes.CAMINHO_ABSOLUTO_RELATORIOS = servletContextEvent.getServletContext().getRealPath(Constantes.CAMINHO_JASPERS) + "/";
        Constantes.CAMINHO_LOGOTIPOS = 			 servletContextEvent.getServletContext().getRealPath(Constantes.CAMINHO_LOGOTIPOS) + "/";
        Constantes.CAMINHO_SERVLET_LOGO_INT =    servletContextEvent.getServletContext().getRealPath(Constantes.CAMINHO_SERVLET_LOGO_INT) + "/";
        Constantes.CAMINHO_SERVLET_LOGO_COPPE =  servletContextEvent.getServletContext().getRealPath(Constantes.CAMINHO_SERVLET_LOGO_COPPE) + "/";
        //------------------------------------------------------------------------------------------------------------------------------------
        //---------------------- Bloco de inicializa��o de constantes do Caminho das Cargas  ----------------------------------------------------------
        Constantes.CAMINHO_ABSOLUTO_ARQUIVOS_CARGA = servletContextEvent.getServletContext().getRealPath(Constantes.CAMINHO_ARQUIVOS_CARGA) + "/";
        Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA = servletContextEvent.getServletContext().getRealPath(Constantes.CAMINHO_ARQUIVO_USUARIOS_CARGA);
        
        //Senha da carga BD
        Constantes.SENHA_CARGABD = getSenhaCargaBD();
        //------------------------------------------------------------------------------------------------------------------------------------

	}
	
	public String getSenhaCargaBD(){

		String json = JsonConfigLoader.getJson(Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA);
		Gson gson = new Gson();
		Usuario[] arrayUsuarios = gson.fromJson(json, Usuario[].class);
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>(Arrays.asList(arrayUsuarios));
		return usuarios.get(0).getSenha();
	}
}