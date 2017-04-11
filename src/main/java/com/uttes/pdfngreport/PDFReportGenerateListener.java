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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 *
 * @author Rivet Systems
 */
public class PDFReportGenerateListener implements ITestListener, ISuiteListener {

    public static Map<String, ResultMeta> result = new HashMap<String, ResultMeta>();
    public static String suiteName = "";
    public static String className = "";

    public void onStart(ISuite isuite) {
    }

    public void onFinish(ISuite isuite) {
        System.out.println("On suite finish :" + result.size());
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
            try {
                generator.generateReport(outpurDir + "\\" + reportFileName+Constants.PDF_FILE_EXT, result);
            } catch (Exception ex) {
                Logger.getLogger(PDFReportGenerateListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void onTestStart(ITestResult itr) {
        className = itr.getTestClass().getName();
    }

    public void onTestSuccess(ITestResult itr) {
    }

    public void onTestFailure(ITestResult itr) {
    }

    public void onTestSkipped(ITestResult itr) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult itr) {
    }

    public void onStart(ITestContext itc) {
    }

    public void onFinish(ITestContext itc) {
        ResultMeta resultMeta = new ResultMeta();
        System.out.println("Class :" + className + " passed :" + itc.getPassedTests().getAllResults().size());
        System.out.println("Class :" + className + " failed :" + itc.getFailedTests().getAllResults().size());
        System.out.println("Class :" + className + " skipped :" + itc.getSkippedTests().getAllResults().size());
        List<Set<ITestResult>> passedList = new ArrayList<Set<ITestResult>>();
        passedList.add(itc.getPassedTests().getAllResults());
        List<Set<ITestResult>> failedList = new ArrayList<Set<ITestResult>>();
        failedList.add(itc.getFailedTests().getAllResults());
        List<Set<ITestResult>> skippedList = new ArrayList<Set<ITestResult>>();
        skippedList.add(itc.getSkippedTests().getAllResults());
        resultMeta.setFailedList(failedList);
        resultMeta.setPassedList(passedList);
        resultMeta.setSkippedList(skippedList);
        result.put(className, resultMeta);
    }

}
