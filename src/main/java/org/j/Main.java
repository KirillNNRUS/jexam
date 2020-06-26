package org.j;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String stringFromXMLFile = "";
    static List<String> onlyTestCaseList = new ArrayList<>();
    static String stringForParse = "";
    static Pattern pattern = Pattern.compile("<testcase(.*?)/>|<testcase(.*?)</testcase>");

    public static void main(String[] args) {
        readXMLString();
        getUpdateXML();
        deserializeFromXML();
    }

    public static void readXMLString() {
        try {
            stringFromXMLFile = new String(Files.readAllBytes(Paths.get("report2.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUpdateXML() {
        stringFromXMLFile = stringFromXMLFile.replaceAll("\r\n", " ");
        stringFromXMLFile = stringFromXMLFile.replaceAll("\t", " ");
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
            if (i == (onlyTestCaseList.size() -1)) {
                stringBuilder.append("</list>");
            }
        }

        stringForParse = stringBuilder.toString();
        System.out.println(stringForParse);
    }

    public static void deserializeFromXML() {
        try {
            XmlMapper xmlMapper = new XmlMapper();

            // deserialize from the XML into a PhoneDetails object
            List<TestCase> myObjects = xmlMapper.readValue(stringForParse, new TypeReference<List<TestCase>>() {
            });

            // Print object details
            System.out.println("Deserialized data: ");
            System.out.println(myObjects);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
