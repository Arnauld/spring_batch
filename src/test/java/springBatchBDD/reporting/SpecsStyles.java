package springBatchBDD.reporting;

import com.itextpdf.text.*;

import java.awt.*;

/**
 * @author Arnauld Loyer (@aloyer)
 */
public class SpecsStyles {
    private static final Color P_COLOR = new Color(80, 153, 170);

    public static final BaseColor PRIMARY_COLOR = new BaseColor(P_COLOR.getRed(), P_COLOR.getGreen(), P_COLOR.getBlue());

    public static final com.itextpdf.text.Font DEFAULT_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
    public static final com.itextpdf.text.Font MAIN_TITLE_FONT = FontFactory.getFont("Arial", 16, com.itextpdf.text.Font.NORMAL, PRIMARY_COLOR);
    public static final com.itextpdf.text.Font CHAPTER_TITLE_FONT = FontFactory.getFont("Arial", 13, com.itextpdf.text.Font.BOLD, PRIMARY_COLOR);
    public static final com.itextpdf.text.Font SECTION_TITLE_FONT = FontFactory.getFont("Arial", 12, com.itextpdf.text.Font.NORMAL, PRIMARY_COLOR);
    public static final com.itextpdf.text.Font SUB_TITLE_FONT = FontFactory.getFont("Arial", 20, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
    public static final com.itextpdf.text.Font TABLE_HEADER_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
    public static final com.itextpdf.text.Font TABLE_CONTENT_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
    public static final com.itextpdf.text.Font STEP_DEFAULT_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
    public static final com.itextpdf.text.Font STEP_KEYWORD_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.BOLD, PRIMARY_COLOR);
    public static final com.itextpdf.text.Font STEP_PARAMETER_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.NORMAL, PRIMARY_COLOR);
    public static final com.itextpdf.text.Font STEP_DATA_TABLE_HEADER_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
    public static final com.itextpdf.text.Font STEP_DATA_TABLE_CONTENT_FONT = FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
    public static final BaseColor STEP_DATA_TABLE_HEADER_BACKGROUND = PRIMARY_COLOR;

    public static Phrase pageHeaderC2() {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk("C2", DEFAULT_FONT));
        phrase.add(new Chunk(" | ", FontFactory.getFont("Arial", 9, com.itextpdf.text.Font.BOLD, PRIMARY_COLOR)));
        phrase.add(new Chunk("CONFIDENTIAL", DEFAULT_FONT));
        return phrase;
    }
}
