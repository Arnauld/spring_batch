package springBatchBDD;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import springBatchBDD.util.SpringBuilder;

public class SpringContextTest {

	@Test
	public void loadContext() {
		AnnotationConfigApplicationContext context = 
				new SpringBuilder()
					.usingContext(new ClassPathResource("/springBatchBDD/spring-config.xml"))
					.build();
		
		assertNotNull(context);
	}
}
