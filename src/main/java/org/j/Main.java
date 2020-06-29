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
    static List<TestCaseHandler> testCaseHandlerList = new ArrayList<>();
    static List<TestCase> testCaseList = new ArrayList<>();
    static TestSuite testSuite = new TestSuite();

    public static void main(String[] args) {
        readXMLString();
        createUpdateXML();
        deserializeFromXML();
        createTestSuiteList();

        testSuite.setTestCases(testCaseList);
        testSuite.setTestsCount(testCaseList.size());
        testSuite.setFailuresCount((int) testCaseList.stream().filter(testCase -> testCase.getStatus() == TestCaseStatus.FAILED).count());
        testSuite.setErrorsCount((int) testCaseList.stream().filter(testCase -> testCase.getStatus() == TestCaseStatus.ERROR).count());
        testSuite.setSkippedCount((int) testCaseList.stream().filter(testCase -> testCase.getStatus() == TestCaseStatus.SKIPPED).count());
        System.out.println("");
    }

    public static void createTestSuiteList() {
        TestCase testCase;
        for (TestCaseHandler testCaseHandler : testCaseHandlerList) {
            testCase = new TestCase(testCaseHandler.getName(), testCaseHandler.getClassName(), TestCaseStatus.PASSED);

            if (testCaseHandler.getSkipped() != null) {
                testCase.setStatus(TestCaseStatus.SKIPPED);
                if (testCaseHandler.getSkipped().getExtendedMessage() != null
                        && !testCaseHandler.getSkipped().getExtendedMessage().equals("")) {

                    testCase.setDetails(testCaseHandler.getSkipped().getExtendedMessage());

                } else if (testCaseHandler.getSkipped().getMessage() != null
                        && !testCaseHandler.getSkipped().getMessage().equals("")) {

                    testCase.setDetails(testCaseHandler.getSkipped().getMessage());

                } else if (testCaseHandler.getSkipped().getType() != null
                        && !testCaseHandler.getSkipped().getType().equals("")) {

                    testCase.setDetails(testCaseHandler.getSkipped().getType());

                } else {
                    testCase.setDetails("No details");
                }
            }

            if (testCaseHandler.getError() != null) {
                testCase.setStatus(TestCaseStatus.ERROR);
                if (testCaseHandler.getError().getExtendedMessage() != null
                        && !testCaseHandler.getError().getExtendedMessage().equals("")) {

                    testCase.setDetails(testCaseHandler.getError().getExtendedMessage());

                } else if (testCaseHandler.getError().getMessage() != null
                        && !testCaseHandler.getError().getMessage().equals("")) {

                    testCase.setDetails(testCaseHandler.getError().getMessage());

                } else if (testCaseHandler.getError().getType() != null
                        && !testCaseHandler.getError().getType().equals("")) {

                    testCase.setDetails(testCaseHandler.getError().getType());

                } else {
                    testCase.setDetails("No details");
                }
            }

            if (testCaseHandler.getFailure() != null) {
                testCase.setStatus(TestCaseStatus.FAILED);
                if (testCaseHandler.getFailure().getExtendedMessage() != null
                        && !testCaseHandler.getFailure().getExtendedMessage().equals("")) {

                    testCase.setDetails(testCaseHandler.getFailure().getExtendedMessage());

                } else if (testCaseHandler.getFailure().getMessage() != null
                        && !testCaseHandler.getFailure().getMessage().equals("")) {

                    testCase.setDetails(testCaseHandler.getFailure().getMessage());

                } else if (testCaseHandler.getFailure().getType() != null
                        && !testCaseHandler.getFailure().getType().equals("")) {

                    testCase.setDetails(testCaseHandler.getFailure().getType());

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
            List<File> filesList = filesListBuilder(new File("report2.xml"), new File("report.xml"));

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

            testCaseHandlerList = xmlMapper.readValue(stringForParse, new TypeReference<List<TestCaseHandler>>() {
            });

            System.out.println("");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
