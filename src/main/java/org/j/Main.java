package org.j;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String stringFromXMLFile = "";
    static List<String> onlyTestCaseList = new ArrayList<>();
    static String stringForParse = "";
    static Pattern pattern = Pattern.compile("<testcase(.*?)/>|<testcase(.*?)</testcase>");
    static List<TestCaseFull> testCaseFullList = new ArrayList<>();
    static List<TestCase> testCaseList = new ArrayList<>();
    static TestResult testResult = new TestResult();

    public static void main(String[] args) {
        readXMLString();
        createUpdateXML();
        deserializeFromXML();
        createTestSuiteList();

        testResult.setTestCases(testCaseList);
        testResult.setTestsCount(testCaseList.size());
        testResult.setFailuresCount((int) testCaseList.stream().filter(testCase -> testCase.getStatus() == TestCaseStatus.FAILED).count());
        testResult.setErrorsCount((int) testCaseList.stream().filter(testCase -> testCase.getStatus() == TestCaseStatus.ERROR).count());
        testResult.setSkippedCount((int) testCaseList.stream().filter(testCase -> testCase.getStatus() == TestCaseStatus.SKIPPED).count());
        System.out.println("");
    }

    public static void createTestSuiteList() {
        TestCase testCase;
        for (TestCaseFull testCaseFull : testCaseFullList) {
            testCase = new TestCase(testCaseFull.getName(), testCaseFull.getClassName(), TestCaseStatus.PASSED);

            if (testCaseFull.getSkipped() != null) {
                testCase.setStatus(TestCaseStatus.SKIPPED);
                if (testCaseFull.getSkipped().getExtendedMessage() != null
                        && !testCaseFull.getSkipped().getExtendedMessage().equals("")) {

                    testCase.setDetails(testCaseFull.getSkipped().getExtendedMessage());

                } else if (testCaseFull.getSkipped().getMessage() != null
                        && !testCaseFull.getSkipped().getMessage().equals("")) {

                    testCase.setDetails(testCaseFull.getSkipped().getMessage());

                } else if (testCaseFull.getSkipped().getType() != null
                        && !testCaseFull.getSkipped().getType().equals("")) {

                    testCase.setDetails(testCaseFull.getSkipped().getType());

                } else {
                    testCase.setDetails("No details");
                }
            }

            if (testCaseFull.getError() != null) {
                testCase.setStatus(TestCaseStatus.ERROR);
                if (testCaseFull.getError().getExtendedMessage() != null
                        && !testCaseFull.getError().getExtendedMessage().equals("")) {

                    testCase.setDetails(testCaseFull.getError().getExtendedMessage());

                } else if (testCaseFull.getError().getMessage() != null
                        && !testCaseFull.getError().getMessage().equals("")) {

                    testCase.setDetails(testCaseFull.getError().getMessage());

                } else if (testCaseFull.getError().getType() != null
                        && !testCaseFull.getError().getType().equals("")) {

                    testCase.setDetails(testCaseFull.getError().getType());

                } else {
                    testCase.setDetails("No details");
                }
            }

            if (testCaseFull.getFailure() != null) {
                testCase.setStatus(TestCaseStatus.FAILED);
                if (testCaseFull.getFailure().getExtendedMessage() != null
                        && !testCaseFull.getFailure().getExtendedMessage().equals("")) {

                    testCase.setDetails(testCaseFull.getFailure().getExtendedMessage());

                } else if (testCaseFull.getFailure().getMessage() != null
                        && !testCaseFull.getFailure().getMessage().equals("")) {

                    testCase.setDetails(testCaseFull.getFailure().getMessage());

                } else if (testCaseFull.getFailure().getType() != null
                        && !testCaseFull.getFailure().getType().equals("")) {

                    testCase.setDetails(testCaseFull.getFailure().getType());

                } else {
                    testCase.setDetails("No details");
                }
            }


            testCaseList.add(testCase);
        }
    }

    public static List<File> filesListBuilder(File... files) {
        List<File> fileArrayList = new ArrayList<>();
        Collections.addAll(fileArrayList, files);
        return fileArrayList;
    }

    public static void readXMLString() {
        try {
            List<File> filesList = filesListBuilder(
                    new File("xUnit.xml"),
                    new File("report2.xml")
            );

            StringBuilder stringBuilder = new StringBuilder();
            for (File file : filesList) {
                stringBuilder.append(new String(Files.readAllBytes(Paths.get(file.getPath()))));
            }
            stringFromXMLFile = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createUpdateXML() {
        stringFromXMLFile = stringFromXMLFile.replaceAll("\r\n", " ");
        stringFromXMLFile = stringFromXMLFile.replaceAll("test-case", "testcase");
        stringFromXMLFile = stringFromXMLFile.replaceAll("&#xA;", " ");
        stringFromXMLFile = stringFromXMLFile.replaceAll("&#x9;", " ");
        stringFromXMLFile = stringFromXMLFile.replaceAll("\n", " ");
        stringFromXMLFile = stringFromXMLFile.replaceAll("\t", " ");
        stringFromXMLFile = stringFromXMLFile.replaceAll("\n\t", " ");
        stringFromXMLFile = stringFromXMLFile.replaceAll(" +", " ");

        Matcher matcher = pattern.matcher(stringFromXMLFile);
        while (matcher.find()) {
            onlyTestCaseList.add(matcher.group());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < onlyTestCaseList.size(); i++) {
            if (i == 0) {
                stringBuilder.append("<list>");
            }
            stringBuilder.append(onlyTestCaseList.get(i));
            if (i == (onlyTestCaseList.size() - 1)) {
                stringBuilder.append("</list>");
            }
        }

        stringForParse = stringBuilder.toString();
    }

    public static void deserializeFromXML() {
        try {
            XmlMapper xmlMapper = new XmlMapper();

            testCaseFullList = xmlMapper.readValue(stringForParse, new TypeReference<List<TestCaseFull>>() {
            });

            System.out.println("");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
