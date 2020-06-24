package org.j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"name", "classname"})
public class TestCase {
    @JsonProperty("name")
    private String name;

    @JsonProperty("classname")
    private String classname;


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
