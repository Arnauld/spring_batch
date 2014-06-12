package springBatchBDD.util;

import java.io.StringWriter;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

public class MemoryAppender extends WriterAppender {
	
	private StringWriter buffer = new StringWriter();

	public MemoryAppender(String pattern) {
		super();
		setLayout(new PatternLayout(pattern));
		setWriter(buffer);
	}
	
	public String rawContent() {
		return buffer.toString();
	}
	
}
