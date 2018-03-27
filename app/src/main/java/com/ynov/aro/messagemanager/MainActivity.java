package com.ynov.aro.messagemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView login;
    TextView password;
    CheckBox remember;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //récupération des éléments de la vue
        login = findViewById(R.id.nom);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //récupération des info mémorisées si le remember me a été coché
        if (prefs.getString("login", null) != null && prefs.getString("password", null) != null){
            login.setText(prefs.getString("login", null));
            password.setText(prefs.getString("password", null));
            remember.setChecked(true);
        }

        //association d'action aux boutons
        Button valider = findViewById(R.id.valider);
        valider.setOnClickListener(genericOnClickListener);

        Button reset = findViewById(R.id.vider);
        reset.setOnClickListener(genericOnClickListener);


    }

    //isolation des actions des boutons
    final View.OnClickListener genericOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(final View view) {

            switch (view.getId()){
                case R.id.valider:
                    if (TextUtils.isEmpty(login.getText())){
                        login.setError("login empty");
                    }

                    if (TextUtils.isEmpty(password.getText())){
                        password.setError("password empty");
                    }

                    if (!TextUtils.isEmpty(login.getText()) && !TextUtils.isEmpty(password.getText())) {

                        new Autentification().execute(login.getText().toString(), password.getText().toString());

                    }
                    break;
                case R.id.vider:
                    login.setText(null);
                    password.setText(null);
                    remember.setChecked(false);

                    prefs.edit()
                            .putString("login", "")
                            .putString("password", "")
                            .apply();
                    break;
                default:
                    break;
            }
        }
    };

    class Autentification extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String ... strings){
            URL url = null;

            HttpURLConnection urlConnection = null;

            String content = "";

            try {

                //on prépare la requête au web service
                url = new URL("http://thibault01.com:8081/authorization?login=" + strings[0] + "&mdp=" + strings[1]);

                urlConnection = (HttpURLConnection) url.openConnection();

                //on récupère la réponse
                InputStream in = urlConnection.getInputStream();

                //on traite la réponse du serveur
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while((content = reader.readLine()) != null){
                    sb.append(content);
                }

                content = sb.toString();


            }catch (Exception excep){
                //si jamais l'autentification échoue, que ce soit de mauvais paramètres ou que la connection au serveur ai un soucis
                Log.e("autentification", "échec de l'autentification via l'API", excep);
            }

            publishProgress(content);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);

            //les actions à effectuer pendant la reception du message du serveur
            if (values[0].equals("true")) {

                //mémorisation des infos pour préremplir les champs à la prochaine utilisation de l'appli
                if (remember.isChecked()){

                    prefs.edit()
                            .putString("login", login.getText().toString())
                            .putString("password", password.getText().toString())
                            .apply();

                }else{

                    prefs.edit()
                            .putString("login", "")
                            .putString("password", "")
                            .apply();
                }

                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this, "mauvais mot de passe ou login", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
