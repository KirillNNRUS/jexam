package org.j;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        deserializeFromXML();
    }

    public static void deserializeFromXML() {
        try {
            XmlMapper xmlMapper = new XmlMapper();

            // read file and put contents into the string
            String readContent = new String(Files.readAllBytes(Paths.get("001.xml")));

            // deserialize from the XML into a PhoneDetails object
//            TestSuite deserializedData = xmlMapper.readValue(readContent, TestSuite.class);
            List<TestSuite> myObjects = xmlMapper.readValue(readContent, new TypeReference<List<TestSuite>>() {
            });
            // Print object details
            System.out.println("Deserialized data: ");
            System.out.println(myObjects);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
