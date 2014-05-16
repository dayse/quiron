package JPA;

import org.testng.annotations.Test;

import util.JPAUtil;

@Test
public class TesteJPA {
	  
	@Test
	public void startupJPA() {
		JPAUtil.JPAstartUp();
	}

}
