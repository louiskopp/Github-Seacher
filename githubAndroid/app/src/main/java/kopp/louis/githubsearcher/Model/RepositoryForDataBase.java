package kopp.louis.githubsearcher.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "repositoryForDatabase")
public class RepositoryForDataBase {

    @PrimaryKey
    private int uid;
    private String name;
    private String description;
    private String language;
    private String avatar_url;
    private boolean favorite;

    public RepositoryForDataBase(int uid, String name, String description, String language, String avatar_url){
        this.avatar_url = avatar_url;
        this.description = description;
        this.uid = uid;
        this.name = name;
        this.language = language;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}

