package springBatchBDD;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import springBatchBDD.util.SpringBuilder;
import cucumber.api.Format;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FetchFilesSteps {

	private String loanMLpattern;

	private Properties properties;

	private JobExecution jobExecution;

	@Before
	public void initProperties() throws IOException {
		properties = PropertiesLoader.load("/cfg/job.properties");
		
		String remoteBaseDir = properties.getProperty("remote.base.dir");
		File remoteFileDir = new File(remoteBaseDir);
		
		if(remoteFileDir.exists()){
			FileUtils.cleanDirectory(remoteFileDir);
		}
		new File(remoteFileDir, "in").mkdirs();
		new File(remoteFileDir, "old/in").mkdirs();
				
		String localBaseDir = properties.getProperty("local.base.dir");
		File localFileDir = new File(localBaseDir);
		
		if(localFileDir.exists()){
			FileUtils.cleanDirectory(localFileDir);
		}
		new File(localFileDir, "in").mkdirs();
	}

	@Given("^the loanML files pattern is \"([^\"]*)\"$")
	public void the_loanML_files_pattern_is(String pattern) throws Throwable {
		loanMLpattern = pattern;
	}

	@Given("^the remote \"([^\"]*)\" folder contains the following files:$")
	public void the_remote_folder_contains_the_following_files(
			String subDirectory, List<String> fileNames) throws Throwable {

		String remoteBaseDir = properties.getProperty("remote.base.dir");
		File remoteFileDir = new File(remoteBaseDir + File.separatorChar
				+ subDirectory);
		
		remoteFileDir.mkdirs();
		
		
		for (String fileName : fileNames) {

			File newFile = new File(remoteFileDir, fileName);
			Assert.assertTrue(newFile.createNewFile());

		}

	}

	@When("^I launch the fetchFiles batch for loanML and for date (\\d{4}\\-[A-Z]{3}\\-\\d{2})$")
	public void I_launch_the_fetchFiles_batch_for_loanML_and_for_date_(@Format(value = "yyyy-MMM-dd") Date inventoryDate) throws Throwable {
		
		AnnotationConfigApplicationContext context = 
				new SpringBuilder()
					.usingContext(new ClassPathResource("/springBatchBDD/spring-config.xml"))
					.build();
		
		JobLauncher jobLauncher=(JobLauncher)context.getBean("jobLauncher");

		Job fetchFilesJob=(Job)context.getBean("fetchFiles");

		Map<String,JobParameter> parameters=new HashMap<String, JobParameter>();
		parameters.put("inventoryDate", new JobParameter(inventoryDate));
		parameters.put("batchName", new JobParameter("LoanML"));
				
		
		JobParameters jobParameters=new JobParameters(parameters);

		jobExecution = jobLauncher.run(fetchFilesJob, jobParameters);
				
		while(jobExecution.isRunning()){
			Thread.sleep(100);
		}	
		
	}
	
	@Then("^the following files should become available in local \"([^\"]*)\" folder:$")
	public void the_following_files_should_become_available_in_local_folder(String subDirectory, List<String> expectedFileNames) throws Throwable {
	   
		String localBaseDir = properties.getProperty("local.base.dir");
		File localFileDir = new File(localBaseDir + File.separatorChar
				+ subDirectory);
		
		
		localFileDir.mkdirs();
		
		
		assertThat(localFileDir).exists();
		
		List<String> actualFiles = Arrays.asList(localFileDir.list());
		assertThat(actualFiles).containsOnly(expectedFileNames.toArray(new String[0]));
	}

}
