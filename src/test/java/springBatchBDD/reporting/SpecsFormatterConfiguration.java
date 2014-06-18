package springBatchBDD.reporting;

import cucumber.contrib.formatter.pdf.Configuration;
import cucumber.contrib.formatter.pdf.PdfFormatter;

import static springBatchBDD.reporting.SpecsStyles.*;

/**
 * @author Arnauld Loyer (@aloyer)
 */
public class SpecsFormatterConfiguration {
    public static Configuration defaultConfiguration(Confidentiality confidentiality) {
        Configuration configuration = PdfFormatter.defaultConfiguration()
                .withPrimaryColor(PRIMARY_COLOR)
                .withStepDefaultFont(STEP_DEFAULT_FONT)
                .withStepKeywordFont(STEP_KEYWORD_FONT)
                .withStepParameterFont(STEP_PARAMETER_FONT)
                .withStepDataTableHeaderFont(STEP_DATA_TABLE_HEADER_FONT)
                .withStepDataTableHeaderBackground(STEP_DATA_TABLE_HEADER_BACKGROUND)
                .withStepDataTableContentFont(STEP_DATA_TABLE_CONTENT_FONT)
                .withMainTitleFont(MAIN_TITLE_FONT)
                .withSubTitleFont(SUB_TITLE_FONT)
                .withDefaultFont(DEFAULT_FONT)
                .withChapterTitleFont(CHAPTER_TITLE_FONT)
                .withSectionTitleFont(SECTION_TITLE_FONT)
                .withTableHeaderFont(TABLE_HEADER_FONT)
                .withTableContentFont(TABLE_CONTENT_FONT)
                .withFirstPageContentProvider(new SpecsFirstPageRenderer(confidentiality))
                .withPageFooterFont(DEFAULT_FONT)
                .withScenarioMargin(10.0f, 10.0f, 10.0f, 20.0f)
                .withDocumentMargin(15.0f, 30.0f)
                ;

        if (confidentiality == Confidentiality.C2)
            configuration.withPageHeader(pageHeaderC2());
        return configuration;
    }
}
