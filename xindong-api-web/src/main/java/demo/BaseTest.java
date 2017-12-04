package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
public abstract class BaseTest {
    
	@Test
	public abstract void test() throws Exception;
	@Test
  public abstract void test1() throws Exception;
}
