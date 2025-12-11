package com.example.kockajatek;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView dobas1, dobas2;
    TextView eredmeny, currentPlayerText;
    Button dobasGomb;

    String player1, player2;
    boolean player1Turn = true;
    boolean gameOver = false;

    int[] diceImages = {
            R.drawable.d1,
            R.drawable.d2,
            R.drawable.d3,
            R.drawable.d4,
            R.drawable.d5,
            R.drawable.d6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        dobas1 = findViewById(R.id.dobas1);
        dobas2 = findViewById(R.id.dobas2);
        eredmeny = findViewById(R.id.eredmeny);
        currentPlayerText = findViewById(R.id.currentPlayer);
        dobasGomb = findViewById(R.id.dobas_gomb);

        showNameDialog();

        dobasGomb.setOnClickListener(v -> {
            if (gameOver) {
                resetGame();
            } else {
                dob();
            }
        });
    }

    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add meg a két játékos nevét:");

        EditText input1 = new EditText(this);
        input1.setHint("1. játékos neve");

        EditText input2 = new EditText(this);
        input2.setHint("2. játékos neve");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);
        layout.addView(input1);
        layout.addView(input2);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            player1 = input1.getText().toString();
            player2 = input2.getText().toString();

            if (player1.isEmpty()) player1 = "Játékos 1";
            if (player2.isEmpty()) player2 = "Játékos 2";

            updateCurrentPlayerText();
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void dob() {
        Random random = new Random();

        int roll1 = random.nextInt(6) + 1;
        int roll2 = random.nextInt(6) + 1;

        dobas1.setImageResource(diceImages[roll1 - 1]);
        dobas2.setImageResource(diceImages[roll2 - 1]);

        int osszeg = roll1 + roll2;

        String currentPlayer = player1Turn ? player1 : player2;
        eredmeny.setText(currentPlayer + " dobása: " + osszeg);

        // vesztes ha 6
        if (osszeg == 6) {
            eredmeny.setText("Vesztett: " + currentPlayer + " (összeg: 6)");
            gameOver = true;
            dobasGomb.setText("Új játék");
            return;
        }

        // váltás
        player1Turn = !player1Turn;
        updateCurrentPlayerText();
    }

    private void updateCurrentPlayerText() {
        currentPlayerText.setText("Következő: " + (player1Turn ? player1 : player2));
    }

    private void resetGame() {
        gameOver = false;
        player1Turn = true;
        eredmeny.setText("");
        dobasGomb.setText("Dobás");
        updateCurrentPlayerText();
    }
}
