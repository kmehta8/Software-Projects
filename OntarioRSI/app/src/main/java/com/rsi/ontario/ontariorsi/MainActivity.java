package com.rsi.ontario.ontariorsi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Button btnCamera;
    Button btnAbout;
    Button btnQ;
    TextView title;
    File photoFile = null;
    PhotoInput input = new PhotoInput();
    final Context context = this;
    private LocationManager locationManager;
    private LocationListener listener;
    int numberOfResults = 10;
    String[] nearbyRoadSigns = new String[numberOfResults];
    public String colorChoice;
    public String shapeChoice;
    public String descChoice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.takePicture);
        btnAbout = findViewById(R.id.about);
        title = findViewById(R.id.textView);
        btnQ = findViewById(R.id.questionnaire);
        btnQ.setVisibility(View.INVISIBLE);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intent);
                runTakePicture();
                buttonDisappear();
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Some url endpoint that you may have
                String myUrl = "https://a.mapillary.com/v3/map_features?layers=trafficsigns" +
                        "&radius=600" +
                        "&client_id=YUNMM3lQRXRUR1Y0MDBwcEVLaFJsUTo2ZWFjY2Y0YWJhM2JmYmM5" +
                        "&per_page=" + numberOfResults +
                        "&closeto=" + location.getLongitude() + "," + location.getLatitude();
                //String to place our result in
                String result = null;
                //Instantiate new instance of our class
                HttpGetRequest getRequest = new HttpGetRequest();
                //Perform the doInBackground method, passing in our url
                try {
                    result = getRequest.execute(myUrl).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jObject = new JSONObject(result);
                    JSONArray features = jObject.getJSONArray("features");
                    for (int i=0; i < numberOfResults; i++){
                        try {
                            JSONObject featuresObject = features.getJSONObject(i);
                            // Pulling items from the array
                            JSONObject properties = featuresObject.getJSONObject("properties");
                            nearbyRoadSigns[i] = properties.getString("value");

                            System.out.println(nearbyRoadSigns[i]);

                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                    locationManager.removeUpdates(this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                LayoutInflater li = LayoutInflater.from(context);

                final View promptsView = li.inflate(R.layout.about_layout, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("About RSI");
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
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

        btnQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                LayoutInflater li = LayoutInflater.from(context);

                final View promptsView = li.inflate(R.layout.color_layout, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptsView);


                alertDialogBuilder.setTitle("Question 1");

                alertDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int inputUnit = ((Spinner) promptsView.findViewById(R.id.color_chooser)).getSelectedItemPosition();
                        colorChoice = ((Spinner) promptsView.findViewById(R.id.color_chooser)).getItemAtPosition(inputUnit).toString();

                        if (colorChoice.equals("Red")){
                            LayoutInflater li2 = LayoutInflater.from(context);

                            final View promptsView2 = li2.inflate(R.layout.shape_layout, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            alertDialogBuilder.setView(promptsView2);
                            alertDialogBuilder.setTitle("Question 2");

                            alertDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    int inputUnit = ((Spinner) promptsView2.findViewById(R.id.shape_chooser)).getSelectedItemPosition();
                                    shapeChoice = ((Spinner) promptsView2.findViewById(R.id.shape_chooser)).getItemAtPosition(inputUnit).toString();

                                    if (shapeChoice.equals("Triangle")){
                                        LayoutInflater li = LayoutInflater.from(context);

                                        final View promptsView = li.inflate(R.layout.triangle_layout, null);

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                                        alertDialogBuilder.setView(promptsView);
                                        alertDialogBuilder.setTitle("Question 3");
                                        alertDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                //Put actions for CANCEL button here, or leave in blank
                                            }
                                        });
                                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                //Put actions for CANCEL button here, or leave in blank
                                            }
                                        });
                                        final AlertDialog alertDialog = alertDialogBuilder.create();

                                        final Spinner mSpinner = (Spinner) promptsView
                                                .findViewById(R.id.descTri_chooser);
                                        alertDialog.show();
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        int shapeUnit = ((Spinner) promptsView.findViewById(R.id.descTri_chooser)).getSelectedItemPosition();
                                        descChoice = ((Spinner) promptsView.findViewById(R.id.descTri_chooser)).getItemAtPosition(shapeUnit).toString();

                                    }

                                    if (shapeChoice.equals("Ring/Circle")){
                                        LayoutInflater li = LayoutInflater.from(context);

                                        final View promptsView = li.inflate(R.layout.ring_layout, null);

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                                        alertDialogBuilder.setView(promptsView);
                                        alertDialogBuilder.setTitle("Question 3");
                                        alertDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                //Put actions for CANCEL button here, or leave in blank
                                            }
                                        });
                                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                //Put actions for CANCEL button here, or leave in blank
                                            }
                                        });
                                        final AlertDialog alertDialog = alertDialogBuilder.create();

                                        final Spinner mSpinner = (Spinner) promptsView
                                                .findViewById(R.id.descRing_chooser);
                                        alertDialog.show();
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        int shapeUnit = ((Spinner) promptsView.findViewById(R.id.descRing_chooser)).getSelectedItemPosition();
                                        descChoice = ((Spinner) promptsView.findViewById(R.id.descRing_chooser)).getItemAtPosition(shapeUnit).toString();
                                    }
                                }
                            });
                            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Put actions for CANCEL button here, or leave in blank
                                }
                            });

                            final AlertDialog alertDialog2 = alertDialogBuilder.create();
                            final Spinner mSpinner2 = (Spinner) promptsView2
                                    .findViewById(R.id.shape_chooser);
                            alertDialog2.show();
                            alertDialog2.setCanceledOnTouchOutside(true);

                        }

                        else if(colorChoice.equals("Grey")){
                            LayoutInflater li3 = LayoutInflater.from(context);

                            final View promptsView3 = li3.inflate(R.layout.grey_layout, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            alertDialogBuilder.setView(promptsView3);
                            alertDialogBuilder.setTitle("Question 2");

                            alertDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Put actions for CANCEL button here, or leave in blank
                                }
                            });
                            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Put actions for CANCEL button here, or leave in blank
                                }
                            });
                            final AlertDialog alertDialog3 = alertDialogBuilder.create();

                            final Spinner mSpinner3 = (Spinner) promptsView3
                                    .findViewById(R.id.options_chooser);
                            alertDialog3.show();
                            alertDialog3.setCanceledOnTouchOutside(true);

                        }



                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
                    }
                });
                final AlertDialog alertDialog = alertDialogBuilder.create();
                final Spinner mSpinner = (Spinner) promptsView
                        .findViewById(R.id.color_chooser);
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(true);

            }

        });

    }

    private void buttonDisappear() {
        btnCamera.setVisibility(View.INVISIBLE);
        btnAbout.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        btnQ.setVisibility(View.VISIBLE);


    }

    private void runTakePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            try {
                photoFile = input.createImageFile(context);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Context context = getApplicationContext();
                CharSequence text = "Error occurred while creating the File";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                //toast.show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
               // startActivityForResult(takePictureIntent, 1);
            }
        }

    }
}
