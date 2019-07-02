package kopp.louis.githubsearcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner{
    @SerializedName("avatar_url")
    @Expose
    String avatar_url;

    public String getAvatar_url() {
        return avatar_url;
    }
}
