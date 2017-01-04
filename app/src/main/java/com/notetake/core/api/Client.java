package com.notetake.core.api;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiSettings.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create());
    /**
     * Generates the Retrofit Service interface for the type of Service class passed as an input param.
     *
     * @param serviceClazz
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClazz) {
        return createService(serviceClazz, null);
    }

    /**
     * Generates the Service to to add OAuth 2.0 Access token as the Header to the HTTP call made by Retrofit. The Header is
     * added using an OkHttpClient Http Client and it contains the Interceptor to add the Header for incoming and outgoing
     * requests.
     *
     * @param serviceClazz -  The Service Interface.
     * @param authToken    -  Access token retrieved after Successful login by the Account.
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClazz, final String authToken) {
        if (authToken != null && !authToken.equals("")) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header(ApiSettings.AUTHORIZATION, authToken)
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        // build the client
        Retrofit retrofit = builder.client(httpClient.build()).build();

        return retrofit.create(serviceClazz);
    }
}