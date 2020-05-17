package com.example.tripplanner.Views.TripView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripplanner.Models.DialogModel.DialogContract;
import com.example.tripplanner.Presenters.DialogPresenter.DialogPresenter;
import com.example.tripplanner.R;

import java.util.ArrayList;

public class Dialog extends AppCompatActivity implements DialogContract.IView {

    Button start;
    Button cancel;
    Button snooze;
    Intent intent;
    DialogContract.IPresenter presenter;
    String endPoint;
    MediaPlayer media;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    String reqCode;
    String id;
    boolean chkService=false;
    ArrayList<String> notes;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Log.i("nasor","create");
        presenter=new DialogPresenter(this);
         intent=getIntent();
        start = findViewById(R.id.start);
        cancel = findViewById(R.id.cancel);
        snooze = findViewById(R.id.snooze);

         endPoint=intent.getStringExtra("endPoint");
         reqCode=intent.getStringExtra("reqCode");
        final String id=intent.getStringExtra("id");
        notes=intent.getStringArrayListExtra("notes");

        media = MediaPlayer.create(this, R.raw.cool);
        media.setLooping(true); // Set looping
        media.start();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
                media.stop();
                Dialog.this.finish();
                presenter.handleDoneTrip(id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(Dialog.this)) {
                    Intent intent2 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent2, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                } else {
                    Intent intent=new Intent(Dialog.this, FloatingViewService.class);
                    intent.putExtra("notes",notes);
                    startService(intent);
                }
                if(chkService)
                    stopService(new Intent(v.getContext(),ForegroundService.class));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                media.stop();
                presenter.handleDoneTrip(id);
                if(chkService)
                    stopService(new Intent(v.getContext(),ForegroundService.class));
            }
        });
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.stop();
                if(!chkService){
                Intent intent=new Intent(v.getContext(), ForegroundService.class);
                intent.putExtra("reqCode",reqCode);
                startService(intent);
                }
                chkService=true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("nasor","save");
        outState.putString("endPoint",endPoint);
        outState.putString("reqCode",reqCode);
        outState.putString("id",id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
//                startService(new Intent(Dialog.this, FloatingViewService.class));
                Intent intent=new Intent(Dialog.this, FloatingViewService.class);
                intent.putExtra("notes",notes);
                startService(intent);
                finish();            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void openMap() {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+endPoint));       //[use langtiude and latitude]
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);


    }
}
