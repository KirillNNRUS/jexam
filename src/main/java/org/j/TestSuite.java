package org.j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"name", "tests", "failures", "errors", "skipped", "time"})
public class TestSuite {
    @JsonProperty("name")
    private String name;

    @JsonProperty("tests")
    private int tests;

    @JsonProperty("failures")
    private int failures;

    @JsonProperty("errors")
    private int errors;

    @JsonProperty("skipped")
    private int skipped;

    @JsonProperty("time")
    private double time;

    @JacksonXmlProperty(localName = "testcase")
    @JacksonXmlElementWrapper( useWrapping = false)
    List<TestCase> testCaseList;
}
