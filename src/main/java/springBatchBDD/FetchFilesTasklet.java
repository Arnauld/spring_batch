package springBatchBDD;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FetchFilesTasklet implements Tasklet {

	public static final String BATCH_NAME = "batchName";

	public static final String INVENTORY_DATE = "inventoryDate";
	
	private static final Logger LOGGER = Logger.getLogger(FetchFilesTasklet.class);

	private String remoteBaseDir;

	private String localBaseDir;

	private Map<String, String> knownPatterns;
	
	private FileDao fileDao;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext params) throws Exception {
		

		Map<String, Object> jobParameters = params.getStepContext().getJobParameters();

		String inventoryDateFromCommandLine = (String) jobParameters.get(INVENTORY_DATE);
		Date inventoryDate = new SimpleDateFormat("ddMMyy").parse(inventoryDateFromCommandLine);
		String formattedDate = new SimpleDateFormat("yyyyMMdd").format(inventoryDate);

		String batchName = (String) jobParameters.get(BATCH_NAME);

		String regexp = knownPatterns.get(batchName);
		
		LOGGER.debug("Job pattern " + regexp);
		
		FilenameFilter filenameFilter = createFilenameFilter(regexp, formattedDate);

		File inRemoteFolder = new File(remoteBaseDir, "in");
		File[] matchingRemoteFiles = inRemoteFolder.listFiles(filenameFilter);
		File localInFolder = new File(localBaseDir, "in");
		
		if(matchingRemoteFiles.length == 0) {
			File oldInRemoteFolder = new File(remoteBaseDir, "old/in");
			matchingRemoteFiles = oldInRemoteFolder.listFiles(filenameFilter);
		}
		
		
		int nbFilesCopied=0;
		for (File remoteFile : matchingRemoteFiles) {
			FileUtils.copyFileToDirectory(remoteFile, localInFolder, true);
				
			fileDao.save(new CpmFile(remoteFile.getName()));
			
			nbFilesCopied++;
		}
		
		if(nbFilesCopied>0){
			LOGGER.info("OK - "+batchName+" : "+nbFilesCopied+" file(s) have been copied");
		}
		else{
			LOGGER.warn("KO - no file copied for "+batchName+" for date "+inventoryDate);
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

	public void setRemoteBaseDir(String remoteBaseDir) {
		this.remoteBaseDir = remoteBaseDir;
	}

	public void setLocalBaseDir(String localBaseDir) {
		this.localBaseDir = localBaseDir;
	}

	public void setKnownPatterns(Map<String, String> knownPatterns) {
		this.knownPatterns = knownPatterns;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}
}
