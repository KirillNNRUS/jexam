package org.j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skipped {
    @JacksonXmlProperty(isAttribute = true)
    private String message;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlText
    private String extendedMessage;
}
