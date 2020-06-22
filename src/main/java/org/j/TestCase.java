package org.j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"name", "classname", "time"})
@ToString
public class TestCase {
    @JsonProperty("name")
    private String name;

    @JsonProperty("classname")
    private String classname;

    @JsonProperty("time")
    private double time;

    @JacksonXmlProperty(localName = "failure")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Failure> failureList;

    @JacksonXmlProperty(localName = "error")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Error> errorList;
}
