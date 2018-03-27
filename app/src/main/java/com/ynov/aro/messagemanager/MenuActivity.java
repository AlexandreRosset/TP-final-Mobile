package com.ynov.aro.messagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        Button ecole = findViewById(R.id.listeEcole);
        ecole.setOnClickListener(genericOnClickListener);
        Button exo4 = findViewById(R.id.AddEcole);
        exo4.setOnClickListener(genericOnClickListener);
    }

    //isolation des actions des boutons
    final View.OnClickListener genericOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(final View view) {

            switch (view.getId()){
                case R.id.listeEcole:
                    Intent ecole = new Intent(MenuActivity.this, ListeMessageActivity.class);
                    startActivity(ecole);
                    break;
                default:
                    break;
            }
        }
    };
}
