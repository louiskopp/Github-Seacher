package kopp.louis.githubsearcher.Controller;

import java.io.IOException;

import kopp.louis.githubsearcher.Model.RepositoryList;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static final String authToken = "4da8a01b33b9a31934210efb4d729697a6303c34";

    private OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    private Call<RepositoryList> call;

    private OkHttpClient client;


    public Service(){
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        Interceptor headerAuthorizationInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                Headers headers = request.headers().newBuilder().add("Authorization", authToken).build();
                request = request.newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        };
        clientBuilder.addInterceptor(headerAuthorizationInterceptor);
        client = clientBuilder.build();

    }


   public Call<RepositoryList> getCall(String keyWord){
       Retrofit.Builder builder = new Retrofit.Builder()
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .baseUrl("https://api.github.com")
               .client(client)
               .addConverterFactory(GsonConverterFactory.create());


       Retrofit retrofit = builder.build();

       GitHubService service = retrofit.create(GitHubService.class);
       call = service.repoFromSearch(keyWord);
       return call;
   }


}
