package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOCK = "MainActivity";

    private Button[][] buttons = new Button[3][3];

    private TextView textViewPlayerOne;
    private TextView textViewPlayerTwo;

    private boolean playerOneTurn = true;

    private int playerOnePoints = 0;
    private int playerTwoPoints = 0;
    private int roundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerOne = findViewById(R.id.text_view_player1);
        textViewPlayerTwo = findViewById(R.id.text_view_player2);
        //To get references for all the buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int buttonIDRes = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(buttonIDRes);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonRestart = findViewById(R.id.restart_button);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                playerOnePoints = 0;
                playerTwoPoints = 0;
                textViewPlayerOne.setText("Player One: 0");
                textViewPlayerTwo.setText("Player Two: 0");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals(""))
            return;
        else {
            if (playerOneTurn)
                ((Button) v).setText("X");
            else
                ((Button) v).setText("O");

            roundCount++;

            if (checkForWin()) {
                if (playerOneTurn) {
                    playersWin("One");
                } else {
                    playersWin("Two");
                }
            } else if (roundCount == 9) {
                playersWin("Draw");
            } else {
                playerOneTurn = !playerOneTurn;
            }
        }
    }

    //This method gets called when the game reaches a result; win or draw.
    private void playersWin(String winner) {
        if (winner.equals("One"))
            playerOnePoints++;
        else if (winner.equals("Two"))
            playerTwoPoints++;

        //points need to be updated, results need to be shown, and the board needs to be reset regardless
        //of the state of the game after it's finished (win/draw)
        updatePointsText(winner);
        resultPopUp(winner);
        resetBoard();
    }

    //This method sets an intent up to launch an activity that looks like a popup to announce the results
    private void resultPopUp(String result) {
        Intent intent = new Intent(MainActivity.this, ResultPop.class);
        String message = result;
        intent.putExtra(LOCK, message);
        startActivity(intent);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        playerOneTurn = true;
    }

    private void updatePointsText(String result) {
        if (result.equals("One"))
            textViewPlayerOne.setText("Player One: " + playerOnePoints);
        else if (result.equals("Two"))
            textViewPlayerTwo.setText("Player Two: " + playerTwoPoints);
        else
            return;
    }

    //checks the state of the game after each play.
    //accounts for all the possible scenarios for winning a game.
    private boolean checkForWin() {
        //extracts the state of each button (X/O)
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    //These two methods handle the state of the game in case of the rotation of the device.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerOnePoints",playerOnePoints);
        outState.putInt("playerTwoPoints", playerTwoPoints);
        outState.putBoolean("playerOneTurn",playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }
}