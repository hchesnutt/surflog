package org.weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.String.format;

public class Main {

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    // Move to Env Var
    private static final String PROJECT_ID = "";

    // Move to Env Var
    private static final String BUCKET_NAME = "";

    public static void main(String[] args) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://services.surfline.com/kbyg/spots/forecasts/wave?spotId=5c008f5313603c0001df5318&days=1&intervalHours=1"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String objectName = format("%s-surfline-wave-5c008f5313603c0001df5318.json", getUtcCurrentDate());

        CloudStorageObject cloudStorageObject = CloudStorageObject.builder()
                .projectId(PROJECT_ID)
                .bucketName(BUCKET_NAME)
                .objectName(objectName)
                .contents(response.body())
                .build();

        CloudStorageManager.uploadObjectFromMemory(cloudStorageObject);
    }

    public static String getUtcCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }
}

