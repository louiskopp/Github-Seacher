package kopp.louis.githubsearcher.View;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kopp.louis.githubsearcher.DataBase.FavoriteDatabase;
import kopp.louis.githubsearcher.R;
import kopp.louis.githubsearcher.Model.RepositoryForDataBase;

import static kopp.louis.githubsearcher.Controller.Constants.DATABASE_NAME;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<RepositoryForDataBase> repositories;
    private ArrayList<RepositoryForDataBase> favorites;
    private LayoutInflater inflater;
    private Context context;
    private FavoriteDatabase db;


    public ListViewAdapter(Context context, List<RepositoryForDataBase> repositories) {
        this.repositories = (ArrayList<RepositoryForDataBase>) repositories;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = Room.databaseBuilder(context,
                FavoriteDatabase.class, DATABASE_NAME).build();
        Thread go = new Thread(new Runnable() {
            @Override
            public void run() {
                favorites = (ArrayList<RepositoryForDataBase>) db.favoriteDao().getAll();
            }
        });
        go.start();
        try {
            go.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (RepositoryForDataBase repositoryForDataBase : repositories) {
            for (RepositoryForDataBase repositoryForDataBase1 : favorites) {
                if (repositoryForDataBase.getUid() == repositoryForDataBase1.getUid()) {
                    repositoryForDataBase.setFavorite(true);
                }
            }

        }
    }


    @Override
    public int getCount() {
        return repositories.size();
    }

    @Override
    public Object getItem(int position) {
        return repositories.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public TextView name;
        public TextView description;
        public TextView language;
        public Button favorite;
        public ImageView avatar;
        int ref;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder.name = convertView.findViewById(R.id.name);
            holder.description = convertView.findViewById(R.id.description);
            holder.language = convertView.findViewById(R.id.language);
            holder.favorite = convertView.findViewById(R.id.favorite_button);
            holder.avatar = convertView.findViewById(R.id.avatar_holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = position;
        holder.name.setText(repositories.get(position).getName());
        holder.description.setText(repositories.get(position).getDescription());
        holder.language.setText(repositories.get(position).getLanguage());
        Picasso.get().load(repositories.get(position).getAvatar_url()).placeholder(R.drawable.loading_animation).into(holder.avatar);
        if (repositories.get(position).isFavorite()) {
            holder.favorite.setBackgroundResource(R.drawable.filled_heart);
        } else {
            holder.favorite.setBackgroundResource(R.drawable.heart);
        }
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repositories.get(position).isFavorite()) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            RepositoryForDataBase repositoryForDataBase = db.favoriteDao().findByUid(repositories.get(position).getUid());
                            db.favoriteDao().delete(repositoryForDataBase);
                            repositories.get(position).setFavorite(false);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void agentsCount) {
                            holder.favorite.setBackgroundResource(R.drawable.heart);
                        }
                    }.execute();

                } else {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            RepositoryForDataBase repositoryForDataBase = repositories.get(position);
                            repositoryForDataBase.setFavorite(true);
                            db.favoriteDao().insert(repositoryForDataBase);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void agentsCount) {
                            holder.favorite.setBackgroundResource(R.drawable.filled_heart);
                        }
                    }.execute();

                    }
                }
        });
        return convertView;


    }

}
