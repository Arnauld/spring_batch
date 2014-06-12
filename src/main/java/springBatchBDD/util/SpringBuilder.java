package springBatchBDD.util;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

public class SpringBuilder {

	public static final String PROPERTY_PLACEHOLDER_CONFIGURER_BEAN_NAME = "propertyPlaceholderConfigurer-builder";

	private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
	
	private Properties localProperties;
	
	public SpringBuilder usingContext(Resource... resources) {
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(applicationContext);
		xmlReader.loadBeanDefinitions(resources);
		return this;
	}

	public SpringBuilder scanningPackages(String... basePackages) {
		applicationContext.scan(basePackages);
		return this;
	}

	public SpringBuilder scanningClasses(Class<?>... classes) {
		applicationContext.register(classes);
		return this;
	}
	
	public SpringBuilder usingSingleton(String beanName, Object singletonObject) {
		applicationContext.getBeanFactory().registerSingleton(beanName, singletonObject);
		return this;
	}




	/**
	 * This method register a new {@link PropertyPlaceholderConfigurer} in the
	 * context. <strong>Be aware that it may lead to undefined behavior</strong>
	 * (or at least unknown from my point of view) <strong>if an other </strong>
	 * {@link PropertyPlaceholderConfigurer} is already registered.
	 */
	public SpringBuilder usingProperty(String key, String value) {
		if (localProperties == null) {
			localProperties = new Properties();
		}
		localProperties.put(key, value);
		return this;
	}

	/**
	 * This method register a new {@link PropertyPlaceholderConfigurer} in the
	 * context. <strong>Be aware that it may lead to undefined behavior</strong>
	 * (or at least unknown from my point of view) <strong>if an other </strong>
	 * {@link PropertyPlaceholderConfigurer} is already registered.
	 */
	public SpringBuilder usingProperties(Map<String, String> properties) {
		if (localProperties == null) {
			localProperties = new Properties();
		}
		localProperties.putAll(properties);
		return this;
	}

	public AnnotationConfigApplicationContext build() {
		if (localProperties != null) {
			PropertyPlaceholderConfigurer placeholderConfigurer = createPropertyPlaceholderConfigurer();
			placeholderConfigurer.setProperties(localProperties);
			applicationContext.getBeanFactory().registerSingleton(
					PROPERTY_PLACEHOLDER_CONFIGURER_BEAN_NAME,
					placeholderConfigurer);
		}
		applicationContext.refresh();
		return applicationContext;
	}

	protected PropertyPlaceholderConfigurer createPropertyPlaceholderConfigurer() {
		return new PropertyPlaceholderConfigurer();
	}

}
