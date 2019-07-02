package kopp.louis.githubsearcher.Controller;

import kopp.louis.githubsearcher.Model.RepositoryList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("/search/repositories")
    Call<RepositoryList> repoFromSearch(@Query("q") String user);
}
