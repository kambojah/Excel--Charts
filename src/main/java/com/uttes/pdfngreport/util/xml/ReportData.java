package com.uttes.pdfngreport.util.xml;

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

import com.uttesh.pdfngreport.util.xml.*;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class used by the JAXB compiler for the XML tree Element generation,.
 *
 * @author Uttesh Kumar T.H.
 */
@XmlRootElement(name = "ReportData")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportData {

    @XmlElement(name = "CreatedBy")
    String createdBy;

    @XmlElement(name = "ReportTitle")
    String reportTitle;

    @XmlElement(name = "ReportLocation")
    String reportLocation;

    @XmlElement(name = "LogoFile")
    String logoFile;

    @XmlElement(name = "TitleLogoFile")
    String titleLogoFile;

    @XmlElement(name = "TitleType")
    String titleType;

    @XmlElement(name = "Logo")
    String logo;

    @XmlElement(name = "LogoAlign")
    String logoAlign;

    @XmlElement(name = "TitleAlign")
    String titleAlign;

    @XmlElement(name = "Chart")
    String chart;

    @XmlElement(name = "ExceptionIcon")
    String exceptionIcon;

    @XmlElement(name = "Table")
    private List<com.uttesh.pdfngreport.util.xml.Table> table;

    @XmlElement(name = "ExceptionMeta")
    private List<ExceptionMeta> exceptionMeta;

    @XmlElement(name = "ActionDetailIcon")
    private String actionDetailIcon;

    @XmlElement(name = "ActionImageIcon")
    private String actionImageIcon;

    @XmlElement(name = "ExceptionPage")
    private String exceptionPage;

    @XmlElement(name = "BuildSystemDetails")
    private String buildSystemDetails;

    @XmlElement(name = "BuildSystemDetailBy")
    private String buildSystemDetailsBy;

    @XmlElement(name = "BuildVersion")
    private String buildVersion;

    @XmlElement(name = "OSName")
    private String osName;

    @XmlElement(name = "ADDITIONAL_LINE1")
    private String additionLine1;

    @XmlElement(name = "ADDITIONAL_LINE2")
    private String additionLine2;

    @XmlElement(name = "ADDITIONAL_LINE3")
    private String additionLine3;

    @XmlElement(name = "SHOW_COLUMN_TIME")
    String showTime;

    @XmlElement(name = "SHOW_COLUMN_TEST_NAME")
    String showTestName;

    @XmlElement(name = "SHOW_COLUMN_TEST_CASE")
    String showTestCase;

    @XmlElement(name = "SHOW_COLUMN_TIME_TAKEN")
    String showTimeTaken;

    @XmlElement(name = "SHOW_COLUMN_DESCRIPTION")
    String showDesciprtion;

    @XmlTransient
    public List<com.uttesh.pdfngreport.util.xml.Table> getTable() {
        return table;
    }

    public void setTable(List<com.uttesh.pdfngreport.util.xml.Table> table) {
        this.table = table;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }

    public List<ExceptionMeta> getExceptionMeta() {
        return exceptionMeta;
    }

    public void setExceptionMeta(List<ExceptionMeta> exceptionMeta) {
        this.exceptionMeta = exceptionMeta;
    }

    public String getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(String logoFile) {
        this.logoFile = logoFile;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getExceptionIcon() {
        return exceptionIcon;
    }

    public void setExceptionIcon(String exceptionIcon) {
        this.exceptionIcon = exceptionIcon;
    }

    public String getLogoAlign() {
        return logoAlign;
    }

    public void setLogoAlign(String logoAlign) {
        this.logoAlign = logoAlign;
    }

    public String getTitleAlign() {
        return titleAlign;
    }

    public void setTitleAlign(String titleAlign) {
        this.titleAlign = titleAlign;
    }

    public String getTitleLogoFile() {
        return titleLogoFile;
    }

    public void setTitleLogoFile(String titleLogoFile) {
        this.titleLogoFile = titleLogoFile;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getActionDetailIcon() {
        return actionDetailIcon;
    }

    public void setActionDetailIcon(String actionDetailIcon) {
        this.actionDetailIcon = actionDetailIcon;
    }

    public String getActionImageIcon() {
        return actionImageIcon;
    }

    public void setActionImageIcon(String actionImageIcon) {
        this.actionImageIcon = actionImageIcon;
    }

    public String getExceptionPage() {
        return exceptionPage;
    }

    public void setExceptionPage(String exceptionPage) {
        this.exceptionPage = exceptionPage;
    }

    public String getBuildSystemDetails() {
        return buildSystemDetails;
    }

    public void setBuildSystemDetails(String buildSystemDetails) {
        this.buildSystemDetails = buildSystemDetails;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getBuildSystemDetailsBy() {
        return buildSystemDetailsBy;
    }

    public void setBuildSystemDetailsBy(String buildSystemDetailsBy) {
        this.buildSystemDetailsBy = buildSystemDetailsBy;
    }

    public String getAdditionLine1() {
        return additionLine1;
    }

    public void setAdditionLine1(String additionLine1) {
        this.additionLine1 = additionLine1;
    }

    public String getAdditionLine2() {
        return additionLine2;
    }

    public void setAdditionLine2(String additionLine2) {
        this.additionLine2 = additionLine2;
    }

    public String getAdditionLine3() {
        return additionLine3;
    }

    public void setAdditionLine3(String additionLine3) {
        this.additionLine3 = additionLine3;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getShowTestName() {
        return showTestName;
    }

    public void setShowTestName(String showTestName) {
        this.showTestName = showTestName;
    }

    public String getShowTestCase() {
        return showTestCase;
    }

    public void setShowTestCase(String showTestCase) {
        this.showTestCase = showTestCase;
    }

    public String getShowTimeTaken() {
        return showTimeTaken;
    }

    public void setShowTimeTaken(String showTimeTaken) {
        this.showTimeTaken = showTimeTaken;
    }

    public String getShowDesciprtion() {
        return showDesciprtion;
    }

    public void setShowDesciprtion(String showDesciprtion) {
        this.showDesciprtion = showDesciprtion;
    }


}
