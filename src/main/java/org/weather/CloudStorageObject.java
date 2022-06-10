package org.weather;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CloudStorageObject {
    public String projectId;
    public String bucketName;
    public String objectName;
    public String contents;
}
