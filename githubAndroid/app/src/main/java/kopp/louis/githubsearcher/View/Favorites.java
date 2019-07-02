package kopp.louis.githubsearcher.View;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import kopp.louis.githubsearcher.DataBase.FavoriteDatabase;
import kopp.louis.githubsearcher.R;
import kopp.louis.githubsearcher.Model.RepositoryForDataBase;

import static kopp.louis.githubsearcher.Controller.Constants.DATABASE_NAME;

public class Favorites extends AppCompatActivity {

    ArrayList<RepositoryForDataBase> repositories = new ArrayList<RepositoryForDataBase>();
    private FavoriteDatabase db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        db = Room.databaseBuilder(getApplicationContext(),
                FavoriteDatabase.class, DATABASE_NAME).build();
        listView = (ListView) findViewById(R.id.favorites_list);

        Thread go = new Thread(new Runnable() {
            @Override
            public void run() {
               repositories = (ArrayList<RepositoryForDataBase>) db.favoriteDao().getAll();
            }
        });
        go.start();
        try {
            go.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getApplicationContext(), repositories);
        listView.setAdapter(favoritesAdapter);

    }
}
