package com.epam.mjc.nio;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class FileReader {

    public Profile getDataFromFile(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            String fileContent = readFileContent(inputStream);
            Map<String, String> profileData = parseProfileData(fileContent);
            return createProfile(profileData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readFileContent(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        }
    }

    private Map<String, String> parseProfileData(String fileContent) {
        Map<String, String> profileData = new HashMap<>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            String[] keyValue = line.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                profileData.put(key, value);
            }
        }

        return profileData;
    }

    private Profile createProfile(Map<String, String> profileData) {
        String name = profileData.get("Name");
        Integer age = Integer.parseInt(profileData.get("Age"));
        String email = profileData.get("Email");
        Long phone = Long.parseLong(profileData.get("Phone"));

        return new Profile(name, age, email, phone);
    }
}
