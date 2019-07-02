package kopp.louis.githubsearcher.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import kopp.louis.githubsearcher.Model.RepositoryForDataBase;

@Database(entities = {RepositoryForDataBase.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}