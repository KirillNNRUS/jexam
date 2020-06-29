package org.j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite {
    private int testsCount;

    private int failuresCount;

    private int errorsCount;

    private int skippedCount;

    private List<TestCase> testCases;
}
