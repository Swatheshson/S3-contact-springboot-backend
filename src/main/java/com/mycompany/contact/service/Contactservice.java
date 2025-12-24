package com.mycompany.contact.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.lang.reflect.Type;

@Service
public class Contactservice {

    private final String BUCKET_NAME = "my-contact-bucket-swatheshson";
    private final String FILE_KEY = "contacts.json";

    private Map<String, String> contacts = new HashMap<>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private S3Client s3;

    @PostConstruct
    public void init() {
        // Initialize S3 client
        s3 = S3Client.builder().region(Region.US_EAST_1).build();

        // Load existing contacts from S3
        try {
            String json = s3.getObjectAsBytes(b -> b.bucket(BUCKET_NAME).key(FILE_KEY))
                            .asUtf8String();

            Type type = new TypeToken<Map<String, String>>() {}.getType();
            contacts = gson.fromJson(json, type);

            System.out.println("Contacts loaded from S3: " + contacts);
        } catch (Exception e) {
            System.out.println("⚠ No existing contacts found. Starting fresh.");
            contacts = new HashMap<>();
        }
    }

    // Add or update a contact
    public void addOrUpdateContact(String name, String phone) {
        contacts.put(name, phone);  // Update in-memory map
        saveToS3();                 // Persist updated map to S3
    }

    // Optional: delete a contact
    public void deleteContact(String name) {
        contacts.remove(name);
        saveToS3();
    }

    // Save current map to S3
    private void saveToS3() {
        String jsonContent = gson.toJson(contacts);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(FILE_KEY)
                .serverSideEncryption("AES256")
                .contentType("application/json")
                .build();

        s3.putObject(request, RequestBody.fromBytes(jsonContent.getBytes(StandardCharsets.UTF_8)));

        System.out.println("✅ Contacts uploaded to S3 successfully!");
    }

    public Map<String, String> getContacts() {
        return contacts;
    }
}

