/*
 Copyright 2015 Uttesh Kumar T.H.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.uttes.pdfngreport;

import com.uttesh.pdfngreport.*;
import com.uttesh.pdfngreport.common.Constants;
import com.uttesh.pdfngreport.model.ResultMeta;
import com.uttesh.pdfngreport.util.PDFCache;
import com.uttesh.pdfngreport.util.PdfngUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

/**
 * @author Uttesh Kumar T.H. This class is the main class for the pdf report
 * generation
 * @see IReporter
 */
public class PDFReportListener implements IReporter {

    public static Map<String, ResultMeta> result = new HashMap<String, ResultMeta>();

    /**
     * This will be called from IReporter
     *
     * @param list
     * @param suites
     * @param outputFolder
     */
    public void generateReport(List<XmlSuite> list, List<ISuite> suites, String outputFolder) {
        ResultMeta resultMeta = null;
        String os = System.getProperty(Constants.BuidSystem.OS_NAME).trim().substring(0, 1);
        for (ISuite suite : suites) {
            String suiteName = suite.getName();
            String configPath = suite.getParameter("pdfngreport-properties");
            PdfngUtil pdfngUtil = new PdfngUtil();
            pdfngUtil.loadProperties(configPath);
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                if (result.get(suiteName) != null) {
                    resultMeta = result.get(suiteName);
                } else {
                    resultMeta = new ResultMeta();
                    resultMeta.setSuiteName(suiteName);
                }
                resultMeta.setPropertiesFileLocation(configPath);
                ITestContext tc = sr.getTestContext();
                if (tc.getFailedTests().getAllResults() != null && tc.getFailedTests().getAllResults().size() > 0) {
                    resultMeta.getFailedList().add(tc.getFailedTests().getAllResults());
                }
                if (tc.getPassedTests().getAllResults() != null && tc.getPassedTests().getAllResults().size() > 0) {
                    resultMeta.getPassedList().add(tc.getPassedTests().getAllResults());
                }
                if (tc.getSkippedTests().getAllResults() != null && tc.getSkippedTests().getAllResults().size() > 0) {
                    resultMeta.getSkippedList().add(tc.getSkippedTests().getAllResults());
                }
                result.put(suiteName, resultMeta);
            }
            if (result.size() > 0) {
                com.uttesh.pdfngreport.PDFGenerator generator = new com.uttesh.pdfngreport.PDFGenerator();
                String outpurDir = System.getProperty(Constants.SystemProps.REPORT_OUPUT_DIR);
                String reportFileName = System.getProperty(Constants.SystemProps.REPORT_FILE_NAME);
                if (outpurDir == null || outpurDir.trim().length() == 0) {
                    outpurDir = (String) PDFCache.getConfig(Constants.SystemProps.REPORT_OUPUT_DIR);
                }
                if (reportFileName == null || reportFileName.trim().length() == 0) {
                    reportFileName = (String) PDFCache.getConfig(Constants.SystemProps.REPORT_FILE_NAME);
                }
                System.out.println("Report Location : " + outpurDir + "File Name : " + reportFileName + Constants.PDF_FILE_EXT);
                try {
                    if (os != null && os.equalsIgnoreCase("w")) {
                        generator.generateReport(outpurDir + Constants.BACKWARD_SLASH + reportFileName + Constants.PDF_FILE_EXT, result);
                    } else {
                        generator.generateReport(outpurDir + Constants.FORWARD_SLASH + reportFileName + Constants.PDF_FILE_EXT, result);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(PDFReportListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


}
