package com.mobisystems.automation;


import org.openqa.selenium.WebDriverException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Utils {


    private static final List<String> upcaseStrings = Arrays.asList(
            "Label_Note",
            "Label_FreeText",
            "Label_Highlight",
            "Label_Underline",
            "Label_Strikethrough",
            "Label_FreeDraw",
            "Label_Line",
            "Label_LineStart",
            "Label_LineEnd",
            "Label_Rectangle",
            "Label_Ellipse",
            "Label_AddComment",
            "Label_EditComment",
            "Label_Color",
            "Label_Opacity",
            "Label_Thickness",
            "Label_GoToPage",
            "Label_Content",
            "Label_Find",
            "Label_ViewMode",
            "Label_Zoom",
            "Label_ExportToWord",
            "Label_ExportToExcel",
            "Label_ExportToEPub",
            "Label_Delete"
    );
    // This is yet another beautiful fix(hack) that Appium forces us
    // to implement. Appium does some black sorcery with few chosen
    // umlauted chars before putting them into the hierarchy.
    private static final String[][] charFixes = {
            {"Ü", "Ü"},
            {"Ö", "Ö"},
            {"Й", "Й"},
            {"À", "À"},
            {"Á", "Á"},
            {"é", "é"},
            {"ó", "ó"},
            {"ç", "ç"},
            {"á", "á"},
            {"ã", "ã"},
            {"õ", "õ"},
            {"ñ", "ñ"},
            {"Ñ", "Ñ"},
            {"Í", "Í"},
            {"É", "É"},
            {"ú", "ú"}
    };

    private Utils() {
    }

    public static Throwable trimAppiumException(Throwable e) {

        if (!(e instanceof WebDriverException))
            return e;

        String trimmedMessage = e.getMessage().split("Capabilities")[0];
        WebDriverException trimmedException = new WebDriverException(trimmedMessage);
        trimmedException.setStackTrace(e.getStackTrace());
        return trimmedException;
    }

    public static Properties readStringsFile(String path) throws IOException {

        Properties result = new Properties();
        boolean inComment = false;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            for (String line; (line = br.readLine()) != null; ) {
                line = line.trim();

                boolean commentStart = line.startsWith("/*");
                boolean commentEnd = line.endsWith("*/");
                inComment = inComment ? !commentEnd : commentStart && !commentEnd;

                if (!inComment && line.startsWith("\"") && line.contains("=")) {
                    String key = line.substring(0, line.indexOf("=")).trim();
                    String value = line.substring(line.indexOf("=") + 1).trim();
                    key = key.substring(1, key.length() - 1);
                    value = value.substring(1, value.length() - 2);

                    value = upcaseStrings.contains(key) ? value.toUpperCase() : value;
                    for (String[] fix : Utils.charFixes)
                        value = value.replace(fix[0], fix[1]);

                    result.put(key, value);
                }
            }
        }

        return result;
    }

    public static String languageCodeToName(String code) {
        switch (code) {
            case "en_US":
                return "English";
            case "de_DE":
                return "Deutsch";
            case "fr_FR":
                return "Français";
            case "it_IT":
                return "Italiano";
            case "es_ES":
                return "Español";
            case "hi_IN":
                return "हिन्दी";
            case "ja_JP":
                return "日本語";
            case "nl_NL":
                return "Nederlands";
            case "pt_PT":
                return "Português (Portugal)";
            case "ru_RU":
                return "Русский";
            case "th_TH":
                return "ภาษาไทย";
            case "zh_CN":
                return "简体中文";
            default:
                throw new RuntimeException("Unknown language code provided: " + code);
        }
    }
}
