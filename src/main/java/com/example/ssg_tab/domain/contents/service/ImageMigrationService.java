//package com.example.ssg_tab.domain.contents.service;
//
//import com.example.ssg_tab.domain.contents.entity.Contents;
//import com.example.ssg_tab.domain.contents.repository.ContentsRepository;
//import com.example.ssg_tab.global.util.storage.FileUploadService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ImageMigrationService {
//
//    private final FileUploadService fileUploadService;
//    private final ContentsRepository contentsRepository;
//
//    @Transactional
//    public void migrateImageToAzure(Long contentId) {
//        Contents content = contentsRepository.findById(contentId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 콘텐츠를 찾을 수 없습니다: " + contentId));
//        String externalImageUrl = content.getImageUrl();
//
//        if (externalImageUrl == null || externalImageUrl.isBlank()) {
//            log.info("콘텐츠 ID {}에 이미지 URL이 없어 마이그레이션을 건너뜁니다.", contentId);
//            return;
//        }
//
//        HttpURLConnection conn = null;
//        try {
//            URL url = new URL(externalImageUrl);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.connect();
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                InputStream inputStream = conn.getInputStream();
//                long contentLength = conn.getContentLengthLong();
//
//                if (contentLength == -1) {
//                    throw new IOException("콘텐츠 길이를 알 수 없습니다: " + externalImageUrl);
//                }
//
//                String originalFileName = new File(url.getPath()).getName();
//                String newFileName = UUID.randomUUID().toString() + "_" + originalFileName;
//
//                String azureUrl = fileUploadService.uploadFile(inputStream, contentLength, newFileName);
//
//                content.updateImageUrl(azureUrl);
//                // contentsRepository.save(content)는 @Transactional에 의해 자동 처리됩니다.
//
//                log.info("이미지 이전 완료: {} -> {}", externalImageUrl, azureUrl);
//            } else {
//                log.error("외부 이미지에 연결할 수 없습니다. URL: {}, 응답 코드: {}", externalImageUrl, responseCode);
//            }
//
//        } catch (IOException e) {
//            log.error("이미지 이전 중 오류 발생 (콘텐츠 ID: {}): {}", contentId, e.getMessage());
//            // 필요에 따라 예외를 다시 던지거나 처리할 수 있습니다.
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
//    }
//}
