package com.example.ssg_tab.global.util.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final BlobServiceClient blobServiceClient;

    @Value("${spring.cloud.azure.storage.blob.account-name}")
    private String containerName;

    public String uploadFile(InputStream inputStream, long length, String fileName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        // true: 덮어쓰기 허용
        blobClient.upload(inputStream, length, true);

        return blobClient.getBlobUrl();
    }
}
