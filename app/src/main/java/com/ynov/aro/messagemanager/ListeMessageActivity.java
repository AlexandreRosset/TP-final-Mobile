package com.ynov.aro.messagemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ynov.aro.messagemanager.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListeMessageActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Entree> entrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_liste_message);

        entrees = new ArrayList<Entree>();

        //fonction pour récupérer la liste des entrées
        new RecupEntree().execute();

        recyclerView = (RecyclerView) findViewById(R.id.liste_entree);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new MyAdapter(entrees, new ClickListener()));


    }

    class RecupEntree extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String ... strings){
            URL url = null;

            HttpURLConnection urlConnection = null;

            String content = "";

            try {
                //on prépare la requête au webservice
                url = new URL("http://thibault01.com:8081/getEntree");

                urlConnection = (HttpURLConnection) url.openConnection();

                //on traite la réponse du serveur
                InputStream in = urlConnection.getInputStream();

                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while((content = reader.readLine()) != null){
                    sb.append(content);
                }

                content = sb.toString();

                //on parse le JSON que nous a renvoyer le web service
                JSONArray ja = new JSONArray(content);

                for (int i = 0; i < ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    Entree entree = new Entree(jo.getString("id"),
                            jo.getString("nom"),
                            jo.getString("espece"),
                            jo.getString("sexe"),
                            jo.getString("description"));
                    entrees.add(entree);
                }

            }catch (Exception excep){
                //si un soucis surviens pendant la commmmunication avec le serveur
                Log.e("recupEntree", "échec de la récupération des entrées", excep);
            }

            publishProgress(content);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);
        }
    }

    public class ClickListener implements OnClickListener {
        @Override
        public void onClick(View view, int position){
            Intent myIntent = new Intent(ListeMessageActivity.this, DetailsActivity.class);
            myIntent.putExtra("id", Integer.toString(position));
            myIntent.putExtra("nom", entrees.get(position).getNom());
            myIntent.putExtra("espece", entrees.get(position).getEspece());
            myIntent.putExtra("sexe", entrees.get(position).getSexe());
            myIntent.putExtra("description", entrees.get(position).getDescription());
            startActivity(myIntent);
        }
    }

}
