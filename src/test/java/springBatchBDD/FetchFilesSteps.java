package springBatchBDD;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class FetchFilesSteps {

	private String loanMLpattern;

	private Properties properties;

	@Before
	public void initProperties() throws IOException {
		properties = PropertiesLoader.load("/cfg/job.properties");
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

	@When("^I launch the fetchFiles batch for loanML and for date (\\d{2}\\-[A-Z]{3}\\-\\d{2})$")
	public void I_launch_the_fetchFiles_batch_for_loanML_and_for_date_FEB(
			int arg1, @Format arg2) throws Throwable {
		// Express the Regexp above with the code you wish you had
		throw new PendingException();
	}

}
