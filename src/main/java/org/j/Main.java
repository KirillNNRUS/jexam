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
    static String XMLString = "";
    static List<String> strings = new ArrayList<>();
    static Pattern pattern = Pattern.compile("<testcase(.*?)/>|<testcase(.*?)</testcase>");

    public static void main(String[] args) {
        readXMLString();
        getUpdateXML();
        deserializeFromXML();
    }

    public static void readXMLString() {
        try {
            XMLString = new String(Files.readAllBytes(Paths.get("report2.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public static void getUpdateXML() {
      XMLString = XMLString.replaceAll("\r\n", " ");
      XMLString = XMLString.replaceAll("\t", " ");
      XMLString = XMLString.replaceAll(" +", " ");
      XMLString = XMLString.replaceAll(" +", " ");
      Matcher matcher = pattern.matcher(XMLString);
      while (matcher.find()) {
          strings.add(matcher.group());
      }
    }

    public static void deserializeFromXML() {
        try {
            XmlMapper xmlMapper = new XmlMapper();

            // deserialize from the XML into a PhoneDetails object
//            TestSuite deserializedData = xmlMapper.readValue(readContent, TestSuite.class);
            List<TestSuite> myObjects = xmlMapper.readValue(XMLString, new TypeReference<List<TestSuite>>() {
            });
            // Print object details
            System.out.println("Deserialized data: ");
            System.out.println(myObjects);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
