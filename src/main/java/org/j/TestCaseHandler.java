package org.j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseHandler {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true, localName = "classname")
    private String className;

    @JacksonXmlProperty(localName = "failure")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Failure failure;

    @JacksonXmlProperty(localName = "error")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Error error;

    @JacksonXmlProperty(localName = "skipped")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Skipped skipped;
}
