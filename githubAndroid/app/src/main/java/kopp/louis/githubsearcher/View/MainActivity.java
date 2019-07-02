package kopp.louis.githubsearcher.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kopp.louis.githubsearcher.R;
import kopp.louis.githubsearcher.Model.Repository;
import kopp.louis.githubsearcher.Model.RepositoryForDataBase;
import kopp.louis.githubsearcher.Model.RepositoryList;
import kopp.louis.githubsearcher.Controller.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView repoListView;
    Button searchButton;
    EditText searchText;
    String keyWord;
    Service service = new Service();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        repoListView = findViewById(R.id.repo_list);
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setBackgroundResource(R.drawable.search);
        searchText = (EditText) findViewById(R.id.search_text);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyWord = s.toString();
            }
        });

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                    if(keyWord==null||keyWord.equals("")){
                        alert();
                    }
                    else {
                       search(service.getCall(keyWord));
                    }
                    return true;
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyWord==null||keyWord.equals("")) {
                    alert();

                } else {
                    search(service.getCall(keyWord));
            }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_favorites: {
                Intent intent = new Intent(getApplicationContext(), Favorites.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    public void search(Call<RepositoryList> call){

        call.enqueue(new Callback<RepositoryList>() {
            @Override
            public void onResponse(Call<RepositoryList> call, Response<RepositoryList> response) {

                RepositoryList list = response.body();
                ArrayList<Repository> repositories = (ArrayList<Repository>) list.getItems();
                ArrayList<RepositoryForDataBase> repositoryForDataBases = new ArrayList<RepositoryForDataBase>();
                for (Repository repository : repositories) {
                    repositoryForDataBases.add(new RepositoryForDataBase(repository.getUid(), repository.getName(), repository.getDescription(), repository.getLanguage(), repository.getOwner().getAvatar_url()));
                }
                ListViewAdapter listViewAdapter = new ListViewAdapter(MainActivity.this, repositoryForDataBases);
                repoListView.setAdapter(listViewAdapter);
            }

            @Override
            public void onFailure(Call<RepositoryList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void alert(){
        final AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        a_builder.setMessage("You need to enter something to search for.").setCancelable(false)
                .setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Need More Info!");
        alert.show();
    }
}
