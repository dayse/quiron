package JPA;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import util.JPAUtil;

@Test
public class TesteJPA {
	  
	@Test
	public void startupJPA() {
		JPAUtil.JPAstartUp();
		AssertJUnit.assertEquals(1, 1);
	}

}
