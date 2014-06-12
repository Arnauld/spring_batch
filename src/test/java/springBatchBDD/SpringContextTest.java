package springBatchBDD;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import springBatchBDD.util.PropertiesUtils;
import springBatchBDD.util.SpringBuilder;

public class SpringContextTest {

	@Test
	public void loadContext() throws IOException {
		Properties properties = PropertiesLoader.load("/cfg/job.properties");
		Map<String, String> props = PropertiesUtils.propertiesToMap(properties);
		
		
		AnnotationConfigApplicationContext context = 
				new SpringBuilder()
					.usingProperties(props)
					.usingContext(new ClassPathResource("/springBatchBDD/spring-config.xml"))
					.build();
		
		assertNotNull(context);
	}
}
