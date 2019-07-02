package kopp.louis.githubsearcher.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


import kopp.louis.githubsearcher.Model.RepositoryForDataBase;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = REPLACE)
    void insert(RepositoryForDataBase repositoryForDataBase);

    @Query("SELECT * FROM RepositoryForDataBase")
    List<RepositoryForDataBase> getAll();

    @Query("SELECT * FROM RepositoryForDataBase WHERE uid IN (:repositoryIds)")
    List<RepositoryForDataBase> loadAllByIds(int[] repositoryIds);

    @Query("SELECT * FROM RepositoryForDataBase WHERE uid LIKE :uid"
            + " LIMIT 1")
    RepositoryForDataBase findByUid(int uid);

    @Insert
    void insertAll(RepositoryForDataBase... repositories);

    @Delete
    void delete(RepositoryForDataBase repositoryForDataBase);

    @Query("DELETE FROM RepositoryForDataBase")
    void deleteAll();
}


