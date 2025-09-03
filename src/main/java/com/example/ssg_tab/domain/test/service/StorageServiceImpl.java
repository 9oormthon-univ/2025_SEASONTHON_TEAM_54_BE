package com.example.ssg_tab.domain.test.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final BlobServiceClient blobServiceClient;

    @Override
    public List<String> listBlobsInContainer(String containerName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        return containerClient.listBlobs().stream()
                .map(blobItem -> blobItem.getName())
                .collect(Collectors.toList());
    }
}