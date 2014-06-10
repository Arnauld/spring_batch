package springBatchBDD.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SpringBuilder {

	public static final String PROPERTY_PLACEHOLDER_CONFIGURER_BEAN_NAME = "propertyPlaceholderConfigurer-builder";

	private Properties localProperties;
	private List<Resource> resourceList = new ArrayList<Resource>();
	private List<ApplicationContextInitializer<AnnotationConfigApplicationContext>> contextInitializers = new ArrayList<ApplicationContextInitializer<AnnotationConfigApplicationContext>>();
	private List<String> packagesToScan = new ArrayList<String>();
	private List<Class> classesToScan = new ArrayList<Class>();

	public SpringBuilder usingContext(Resource... resources) {
		resourceList.addAll(Arrays.asList(resources));
		return this;
	}

	public SpringBuilder scanningPackages(String... basePackages) {
		packagesToScan.addAll(Arrays.asList(basePackages));
		return this;
	}

	public SpringBuilder scanningClasses(Class... classes) {
		classesToScan.addAll(Arrays.asList(classes));
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
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		for (ApplicationContextInitializer<AnnotationConfigApplicationContext> initializer : contextInitializers)
			initializer.initialize(applicationContext);

		if (!packagesToScan.isEmpty())
			applicationContext.scan(packagesToScan
					.toArray(new String[packagesToScan.size()]));

		if (!classesToScan.isEmpty())
			applicationContext.register(classesToScan
					.toArray(new Class[classesToScan.size()]));

		if (localProperties != null) {
			PropertyPlaceholderConfigurer placeholderConfigurer = createPropertyPlaceholderConfigurer();
			placeholderConfigurer.setProperties(localProperties);
			applicationContext.getBeanFactory().registerSingleton(
					PROPERTY_PLACEHOLDER_CONFIGURER_BEAN_NAME,
					placeholderConfigurer);
		}

		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(
				applicationContext);
		xmlReader.loadBeanDefinitions(resourceList
				.toArray(new Resource[resourceList.size()]));

		applicationContext.refresh();
		return applicationContext;
	}

	protected PropertyPlaceholderConfigurer createPropertyPlaceholderConfigurer() {
		return new PropertyPlaceholderConfigurer();
	}

	public SpringBuilder usingIntializer(
			ApplicationContextInitializer<AnnotationConfigApplicationContext> contextInitializer) {
		contextInitializers.add(contextInitializer);
		return this;
	}
}
