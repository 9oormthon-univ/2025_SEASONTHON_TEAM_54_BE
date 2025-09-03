package com.example.ssg_tab.domain.test.service;

import java.util.List;

public interface StorageService {
    List<String> listBlobsInContainer(String containerName);
}