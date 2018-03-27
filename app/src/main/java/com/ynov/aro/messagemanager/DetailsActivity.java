package com.ynov.aro.messagemanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    TextView nom;
    TextView espece;
    TextView sexe;
    TextView description;
    ImageView image;
    String idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent myIntent = getIntent();
        idList = myIntent.getStringExtra("id");
        nom = findViewById(R.id.nom);
        espece = findViewById(R.id.espece);
        sexe = findViewById(R.id.sexe);
        description = findViewById(R.id.description);
        image = findViewById(R.id.imageView);

        String id = myIntent.getStringExtra("id");
        id = Integer.toString(Integer.parseInt(id) + 1);
        nom.setText(myIntent.getStringExtra("nom"));
        espece.setText(myIntent.getStringExtra("espece"));
        sexe.setText(myIntent.getStringExtra("sexe"));
        description.setText(myIntent.getStringExtra("description"));

        RecupE recup = new RecupE();
        recup.delegate = new AsyncResponse() {
            @Override
            public void processFinish(Drawable output) {
                image.setImageDrawable(output);
            }
        };
        recup.execute(id);


    }

    /*@Override
    public void processFinish(Bitmap output){
        image.setImageDrawable(new BitmapDrawable(output));
    }*/



    class RecupE extends AsyncTask<String, String, Drawable> {

        private Drawable img;
        public AsyncResponse delegate=null;


        @Override
        protected Drawable doInBackground(String... strings){
            img = ImageOperations(strings[0]);
            return img;
        }

        protected Drawable ImageOperations(String id) {
            try {
                URL urle = new URL("http://thibault01.com:8081/images/" + id + ".png");
                Object content = urle.getContent();

                InputStream inputstream = (InputStream)content;
                Drawable  drawable = Drawable.createFromStream(inputstream, "src");
                return drawable;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Drawable image){
            delegate.processFinish(image);
        }
    }
}
