package springBatchBDD;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesLoader {

	
	public static Properties load(String resourcePath) throws IOException {
		java.io.InputStream in = PropertiesLoader.class.getResourceAsStream(resourcePath);
		try  {
			Properties properties = new Properties();
			properties.load(new InputStreamReader(in, "UTF8"));
			return properties;
		}
		finally {
			in.close();
		}
	}
}
