package org.j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    private String name;

    private String className;

    private TestCaseStatus status;

    private String details;

    public TestCase(String name, String className, TestCaseStatus status) {
        this.name = name;
        this.className = className;
        this.status = status;
    }
}
