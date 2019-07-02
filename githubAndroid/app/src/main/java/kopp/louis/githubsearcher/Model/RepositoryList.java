package kopp.louis.githubsearcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepositoryList {

    @SerializedName("items")
    @Expose
    private List<Repository> repositories = null;

    public List<Repository> getItems() {
        return repositories;
    }

    public void setItems(List<Repository> repos) {
        this.repositories = repos;
    }
}
