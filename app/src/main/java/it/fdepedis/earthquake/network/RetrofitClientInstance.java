package it.fdepedis.earthquake.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    //http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    public static Retrofit getRetrofitInstance() {

        // OkHttpClient. Be conscious with the order
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                //.addInterceptor(httpLoggingInterceptor)
                //.addInterceptor(encryptionInterceptor)
                //.addInterceptor(decryptionInterceptor)
                .build();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(USGS_REQUEST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
