package io.beesports.utils;

import io.beesports.exceptions.BSError;
import io.beesports.exceptions.BSException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class ApiClient {

    @Autowired
    private final OkHttpClient client;

    @Autowired
    public ApiClient(OkHttpClient client) {
        this.client = client;
    }

    public String callApi(Request request) throws IOException {
        Call call = client.newCall(request);
        Response response = call.execute();
        String responseString = Objects.requireNonNull(response.body()).string();

        if (responseString.contains("Too many requests")) {
            throw new BSException(BSError.API_RATE_EXCEEDED);
        }

        if (response.code() != 200) {
            throw new BSException(BSError.API_ERROR, response.code() + " - " + responseString);
        }

        response.close();

        return responseString;
    }

}
