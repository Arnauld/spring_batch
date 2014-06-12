package springBatchBDD;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FetchFilesTasklet implements Tasklet {

	public static final String BATCH_NAME = "batchName";

	public static final String INVENTORY_DATE = "inventoryDate";

	private String remoteBaseDir;

	private String localBaseDir;

	private Map<String, String> knownPatterns;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext params) throws Exception {

		Map<String, Object> jobParameters = params.getStepContext().getJobParameters();

		String inventoryDateFromCommandLine = (String) jobParameters.get(INVENTORY_DATE);
		Date inventoryDate = new SimpleDateFormat("ddMMyy").parse(inventoryDateFromCommandLine);
		String formattedDate = new SimpleDateFormat("yyyyMMdd").format(inventoryDate);

		String batchName = (String) jobParameters.get(BATCH_NAME);

		String regexp = knownPatterns.get(batchName);
		FilenameFilter filenameFilter = createFilenameFilter(regexp, formattedDate);

		File inRemoteFolder = new File(remoteBaseDir, "in");
		File[] matchingRemoteFiles = inRemoteFolder.listFiles(filenameFilter);

		File localInFolder = new File(localBaseDir, "in");

		for (File remoteFile : matchingRemoteFiles) {
			FileUtils.copyFileToDirectory(remoteFile, localInFolder, true);
		}

		return RepeatStatus.FINISHED;

	}

	public FilenameFilter createFilenameFilter(String regexp, final String formattedDate) {
		final Pattern pattern = Pattern.compile(regexp);
		FilenameFilter filenameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String fileName) {
				return pattern.matcher(fileName).matches() && fileName.contains(formattedDate);
			}
		};
		return filenameFilter;
	}

	public String getRemoteBaseDir() {
		return remoteBaseDir;
	}

	public void setRemoteBaseDir(String remoteBaseDir) {
		this.remoteBaseDir = remoteBaseDir;
	}

	public String getLocalBaseDir() {
		return localBaseDir;
	}

	public void setLocalBaseDir(String localBaseDir) {
		this.localBaseDir = localBaseDir;
	}

	public Map<String, String> getKnownPatterns() {
		return knownPatterns;
	}

	public void setKnownPatterns(Map<String, String> knownPatterns) {
		this.knownPatterns = knownPatterns;
	}

}
