package springBatchBDD.reporting;

import com.google.common.base.Strings;
import com.itextpdf.text.*;
import cucumber.contrib.formatter.pdf.Configuration;
import cucumber.contrib.formatter.pdf.ContentUpdater;

/**
 * @author Arnauld Loyer (@aloyer)
 */
public class SpecsFirstPageRenderer implements ContentUpdater {

    private Confidentiality confidentiality;

    public SpecsFirstPageRenderer(Confidentiality confidentiality) {
        this.confidentiality = confidentiality;
    }

    @Override
    public void update(Configuration configuration, Document document) throws DocumentException {
        appendMainPage(configuration, document);
        document.newPage();
    }

    private void appendMainPage(Configuration configuration, Document document) throws DocumentException {
        Paragraph mainPage = new Paragraph();
        appendConfidentiality(configuration, mainPage);
        appendTitle(configuration, mainPage);
        appendSubject(configuration, mainPage);
        document.add(mainPage);
    }

    private void appendSubject(Configuration configuration, Paragraph preface) {
        String subject = configuration.getSubject();
        if (!Strings.isNullOrEmpty(subject)) {
            Font font = configuration.subTitleFont();
            for (String titlePart : subject.split("[\n\r]+")) {
                Paragraph paragraph = new Paragraph(titlePart, font);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.setSpacingAfter(15.0f);
                preface.add(paragraph);
            }
        }
    }

    private void appendTitle(Configuration configuration, Paragraph preface) {
        String title = configuration.getTitle();
        if (!Strings.isNullOrEmpty(title)) {
            Font font = configuration.mainTitleFont();
            Paragraph paragraph = null;
            for (String titlePart : title.split("[\n\r]+")) {
                paragraph = new Paragraph(titlePart, font);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.setSpacingAfter(15.0f);
                preface.add(paragraph);
            }

            if (paragraph != null) {
                paragraph.setSpacingAfter(30.0f);
            }
        }
    }

    private void appendConfidentiality(Configuration configuration, Paragraph preface) {
        Font font = configuration.mainTitleFont();
        Paragraph paragraph = new Paragraph(confidentiality + "    |    CONFIDENTIAL", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(60.0f);
        preface.add(paragraph);
    }
}
