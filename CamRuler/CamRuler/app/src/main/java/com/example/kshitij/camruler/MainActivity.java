package com.example.kshitij.camruler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    final Context context = this;
    private  Button btn_about;
    public static String objectsCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_about = (Button) findViewById(R.id.about);

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(MainActivity.this, About.class);
                startActivity(aboutIntent);
            }
        });

        Button btnCamera = (Button)findViewById(R.id.start);
        btnCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                LayoutInflater li = LayoutInflater.from(context);

                final View promptsView = li.inflate(R.layout.start_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptsView);

                alertDialogBuilder.setTitle("Number of Objects");
                alertDialogBuilder.setIcon(R.drawable.new_logo);

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Spinner mSpinner = (Spinner) promptsView.findViewById(R.id.objects_count);

                        objectsCount = mSpinner.getSelectedItem().toString();
                        Log.i("Objectescount", "number selected   " + objectsCount);
                        Intent intent = new Intent(MainActivity.this, Image.class);
                        startActivity(intent);

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();


                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(true);


            }
        });
    }

}
