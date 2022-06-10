package org.weather;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CloudStorageManager {

    private static final String API_KEY_PATH = "";

    public static void uploadObjectFromMemory(CloudStorageObject cloudStorageObject) throws IOException {

        // Mostly pulled from the example: https://cloud.google.com/storage/docs/uploading-objects#storage-upload-object-code-sample
        Storage storage = StorageOptions.newBuilder().setProjectId(cloudStorageObject.getProjectId()).build().getService();
        BlobId blobId = BlobId.of(cloudStorageObject.getBucketName(), cloudStorageObject.getObjectName());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] content = cloudStorageObject.getContents().getBytes(StandardCharsets.UTF_8);
        storage.createFrom(blobInfo, new ByteArrayInputStream(content));

        System.out.println(
                "Object "
                        + cloudStorageObject.getObjectName()
                        + " uploaded to bucket "
                        + cloudStorageObject.getBucketName());
    }
}