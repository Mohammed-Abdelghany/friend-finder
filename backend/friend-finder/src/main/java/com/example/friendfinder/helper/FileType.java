package com.example.friendfinder.helper;

import lombok.Getter;

import java.util.Set;

@Getter
public enum FileType {

    IMAGE(Set.of("jpg", "jpeg", "png"), "image/", 5 * 1024 * 1024),

    VIDEO(Set.of("mp4", "avi"), "video/", 50 * 1024 * 1024);

    private final Set<String> allowedExtensions;
    private final String contentTypePrefix;
    private final long maxSize;

    FileType(Set<String> allowedExtensions, String contentTypePrefix, long maxSize) {
        this.allowedExtensions = allowedExtensions;
        this.contentTypePrefix = contentTypePrefix;
        this.maxSize = maxSize;
    }

}
