package com.example.tictactoe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ResultPop extends Activity {

    private TextView resultTextView;
    private int widthPopUp;
    private int heightPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_popup_layout);
        //To make the activity look like a popup: Change in the dimensions of the screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        heightPopUp = dm.heightPixels;
        widthPopUp = dm.widthPixels;
        //making the activity to show up as a fraction of the window size
        getWindow().setLayout((int) (0.8 * widthPopUp),(int) (0.8 * heightPopUp));
        //setting up the intent
        Intent intent = getIntent();
        String result = intent.getStringExtra(MainActivity.LOCK);
        resultTextView = findViewById(R.id.textview_result);

        if (result.equals("Draw"))
            resultTextView.setText("Draw!");
        else
            resultTextView.setText("Player " + result + " Won!");
    }

    //To make the activity finish in case of clicking anywhere in the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            finish();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("widthPopUp", widthPopUp);
        outState.putInt("heightPopUp", heightPopUp);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        widthPopUp = savedInstanceState.getInt("widthPopUp");
        heightPopUp = savedInstanceState.getInt("heightPopUp");
    }
}


