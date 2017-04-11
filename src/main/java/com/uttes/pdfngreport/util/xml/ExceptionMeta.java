/*
 * Copyright 2015 Uttesh Kumar T.H..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uttes.pdfngreport.util.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *This class used by the JAXB compiler for the XML tree Element generation,.
 * @author Uttesh Kumar T.H.
 */
@XmlRootElement
public class ExceptionMeta {

    @XmlElement(name = "ErrorCode")
    private String errorCode;

    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "StackTrace")
    private String stackTrace;

    @XmlElement(name = "Heading")
    private String heading;



    @XmlTransient
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @XmlTransient
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @XmlTransient
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }


}
