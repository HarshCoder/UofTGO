package com.hvdevelopers.uoftgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectionScreen extends AppCompatActivity {

    private Button btnYellow;
    private Button btnBlue;
    private Button btnContinue;
    private TextView lblUserName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnYellow = (Button) findViewById(R.id.yellowbtn);
        btnBlue = (Button) findViewById(R.id.bluebtn);
        btnContinue = (Button) findViewById((R.id.btncontinue));
        lblUserName = (TextView) findViewById(R.id.userName);


        btnYellow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnYellow.setTextSize(50);
                btnBlue.setTextSize(10);
            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnBlue.setTextSize(50);
                btnYellow.setTextSize(10);
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent openPreGame = new Intent(SelectionScreen.this, PreGameScreen.class);

                Bundle bundle = new Bundle();
                name = lblUserName.getText().toString();
                bundle.putString("username", name);

                openPreGame.putExtras(bundle);

                startActivity(openPreGame);
            }
        });
    }
}