package com.example.tripplanner.Views.TripView;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.Adapters.NotesAdapter;
import com.example.tripplanner.R;

import java.util.ArrayList;

public class FloatingViewService extends Service {

    RecyclerView recyclerView;
    public NotesAdapter arrayAdapter;
    RecyclerView.LayoutManager recyce;
    public ArrayList<String> notes=new ArrayList<>();


    WindowManager windowManager;
    View floatingView;
    WindowManager.LayoutParams params;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();




        floatingView = LayoutInflater.from(this).inflate(R.layout.floathead, null);

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        // adding view whick is at top and left to the floating view

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatingView, params);



        //root element of collapsed view
        final View collapsedView = floatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = floatingView.findViewById(R.id.expanded_container);




        ImageView closeButton = (ImageView) floatingView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
        ImageView openButton = (ImageView) floatingView.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.GONE);
                expandedView.setVisibility(View.VISIBLE);
            }
        });
        /*
      //      ImageView openButton = (ImageView) floatingView.findViewById(R.id.open_button);
       openButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Open the application  click.
                    Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //close the service and remove view from the view hierarchy
                    stopSelf();
                }
            });
*/


        ImageView closeButtonCollapsed = (ImageView) floatingView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the service and remove the from from the window
                stopSelf();
            }
        });




        floatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {


            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                expandedView.setVisibility(View.VISIBLE);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;


                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        windowManager.updateViewLayout(floatingView, params);
                        return true;
                }
                return false;
            }




        });
        recyclerView=floatingView.findViewById(R.id.notesRecyclerView2);
        recyce = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(recyce);
        arrayAdapter=new NotesAdapter(this,R.layout.note_row ,R.id.noteTextView,notes);
        recyclerView.setAdapter(arrayAdapter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int ret= super.onStartCommand(intent, flags, startId);
        notes=intent.getExtras().getStringArrayList("notes");       ///  notifyyyyyyyyy
        arrayAdapter.notifyDataSetChanged();
        Log.i("nasor","yarab");
        return ret;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
    }
}
