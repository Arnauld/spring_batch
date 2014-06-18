package springBatchBDD;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.contrib.formatter.pdf.PdfFormatter;
import org.junit.runner.RunWith;
import springBatchBDD.reporting.Confidentiality;
import springBatchBDD.reporting.SpecsFormatterConfiguration;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Arnauld Loyer (@aloyer)
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"~@wip"},
        format = {"springBatchBDD.RunSpecsTest$ConfiguredFormatter:target/specs"},
        features="classpath:features"
)
public class RunSpecsTest {

    public static class ConfiguredFormatter extends PdfFormatter {
        public ConfiguredFormatter(File reportDir) throws FileNotFoundException {
            super(reportDir, SpecsFormatterConfiguration.defaultConfiguration(Confidentiality.C1)
                    .withReportFilename("fetch-files-specs.pdf")
                    .withPageFooterTemplateText("FetchFiles - Business Testing")
                    .withMetaInformationsResources(RunSpecsTest.class, "reporting/00-meta.properties")
                            // override some of the previous meta infomations
                    .withTitle("Fetch Files\n" +
                            "FUNCTIONAL SPECIFICATIONS \n" +
                            "ANALYSIS AND REQUIREMENTS")
                    .withSubject("2014 Business Requirements")
                    .withPreambuleResource(RunSpecsTest.class, "reporting/00-preambule.md"));
        }
    }
}
