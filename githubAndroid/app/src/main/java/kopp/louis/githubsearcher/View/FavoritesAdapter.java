package kopp.louis.githubsearcher.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kopp.louis.githubsearcher.R;
import kopp.louis.githubsearcher.Model.RepositoryForDataBase;

public class FavoritesAdapter extends BaseAdapter {

    private ArrayList<RepositoryForDataBase> repositories;
    private LayoutInflater inflater;
    private Context context;

    public FavoritesAdapter(Context context,  List<RepositoryForDataBase> repositories) {
        this.repositories = (ArrayList<RepositoryForDataBase>) repositories;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    static class ViewHolder{
        public TextView name;
        public TextView description;
        public TextView language;
        public Button favorite;
        public ImageView avatar;
        int ref;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView ==null){
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder.name =  convertView.findViewById(R.id.name);
            holder.description = convertView.findViewById(R.id.description);
            holder.language = convertView.findViewById(R.id.language);
            holder.favorite = convertView.findViewById(R.id.favorite_button);
            holder.favorite.setVisibility(View.INVISIBLE);
            holder.avatar = convertView.findViewById(R.id.avatar_holder);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = position;
        holder.name.setText(repositories.get(position).getName());
        holder.description.setText(repositories.get(position).getDescription());
        holder.language.setText(repositories.get(position).getLanguage());
        Picasso.get().load(repositories.get(position).getAvatar_url()).placeholder(R.drawable.loading_animation).into(holder.avatar);
        return convertView;



    }
}
