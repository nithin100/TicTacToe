package com.example.nithinreddyganji.tictactoetr1g8app;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ComputerActivity extends AppCompatActivity {
    Button squares[][];
    TextView winnerDeclare;
    char me_move = 'O';
    char computer_move = 'X';
    char squareText[][];
    char empty_slot = ' ';
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        layout=(RelativeLayout) findViewById(R.id.layout);
        squares = new Button[3][3];
        squares[0][0] = (Button) findViewById(R.id.b11);
        squares[0][1] = (Button) findViewById(R.id.b12);
        squares[0][2] = (Button) findViewById(R.id.b13);
        squares[1][0] = (Button) findViewById(R.id.b21);
        squares[1][1] = (Button) findViewById(R.id.b22);
        squares[1][2] = (Button) findViewById(R.id.b23);
        squares[2][0] = (Button) findViewById(R.id.b31);
        squares[2][1] = (Button) findViewById(R.id.b32);
        squares[2][2] = (Button) findViewById(R.id.b33);
        Button restart = (Button) findViewById(R.id.restartButton);
        winnerDeclare = (TextView) findViewById(R.id.winnerTextView);
        squareText = new char[][]{{' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squares[i][j].setOnClickListener(new MyButtonClickListener(i, j));
                if (!squares[i][j].isEnabled()) {
                    squares[i][j].setText("");
                    squares[i][j].setEnabled(true);
                }
            }
        }
        play();
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareText = new char[][]{{' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {' ', ' ', ' '}};
                winnerDeclare.setText("");

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        squares[i][j].setOnClickListener(new MyButtonClickListener(i, j));
                        if (!squares[i][j].isEnabled()) {
                            squares[i][j].setText("");
                            squares[i][j].setEnabled(true);
                            layout.setBackgroundColor(Color.WHITE);
                           // char player='X';
                            //squares[0][0].setText('X');
                        }
                    }
                }
                play();
            }
        });

    }

    class MyButtonClickListener implements View.OnClickListener {
        int x;
        int y;

        public MyButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            if (squares[x][y].isEnabled()) {
                squares[x][y].setEnabled(false);
                squares[x][y].setText("O");
                squares[x][y].setTextColor(Color.GREEN);

                squareText[x][y] = 'O';
                play();

                int result = score();
                if (result == -1) {
                    winnerDeclare.setText("You win...!!");
                }
                if (result == 1) {
                    winnerDeclare.setText("Booo! Loser..!!!!");
                    winnerDeclare.setTextColor(Color.CYAN);
                    for (int row = 0; row < 3; row++) {
                        for (int coloumn = 0; coloumn < 3; coloumn++)
                            squares[row][coloumn].setEnabled(false);
                        layout.setBackgroundColor(Color.RED);
                    }
                } else if (score() == 0 && gameOver()) {
                    winnerDeclare.setText("It's a tie brother...!!");
                    layout.setBackgroundColor(Color.GREEN);
                    winnerDeclare.setTextColor(Color.WHITE);
                }
            }

        }
    }

    public boolean gameOver() {
        if (score() != 0) {
            return true;
        }
        for (int row = 0; row < 3; row++) {
            for (int coloumn = 0; coloumn < 3; coloumn++) {
                if (squareText[row][coloumn] == ' ') {
                    return false;
                }
            }
        }
        return true;


    }

    public int score() {
        int lineScore;
        //Horizontal 3 possibilities
        lineScore = scoreLine(squareText[0][0], squareText[0][1], squareText[0][2]);
        if (lineScore != 0) {
            return lineScore;
        }
        lineScore = scoreLine(squareText[1][0], squareText[1][1], squareText[1][2]);
        if (lineScore != 0) {
            return lineScore;
        }
        lineScore = scoreLine(squareText[2][0], squareText[2][1], squareText[2][2]);
        if (lineScore != 0) {
            return lineScore;
        }
        //Vertical 3 possibilities
        lineScore = scoreLine(squareText[0][0], squareText[1][0], squareText[2][0]);
        if (lineScore != 0) {
            return lineScore;
        }
        lineScore = scoreLine(squareText[0][1], squareText[1][1], squareText[2][1]);
        if (lineScore != 0) {
            return lineScore;
        }
        lineScore = scoreLine(squareText[0][2], squareText[1][2], squareText[2][2]);
        if (lineScore != 0) {
            return lineScore;
        }
        //Diagonal 2 possibilities
        lineScore = scoreLine(squareText[0][0], squareText[1][1], squareText[2][2]);
        if (lineScore != 0) {
            return lineScore;
        }
        return scoreLine(squareText[0][2], squareText[1][1], squareText[2][0]);


    }

    public int scoreLine(char a, char b, char c) {
        if (a == 'X' && b == 'X' && c == 'X') {
            return 1;
        }
        if (a == 'O' && b == 'O' && c == 'O') {
            return -1;
        }
        return 0;
    }

    public void play() {
        char player = 'X';
        if (gameOver()) {
            return;
        }
        if (player == 'X') {
            playBestMove();
            // player = 'O';
        }
    }

    protected void playBestMove() {
        int score;
        int bestScore = -2;
        int bestRow = -1;
        int bestColoumn = -1;
        for (int row = 0; row < 3; row++)
            for (int coloumn = 0; coloumn < 3; coloumn++) {
                if (squareText[row][coloumn] == ' ') {
                    squares[row][coloumn].setText("X");
                    squareText[row][coloumn] = 'X';
                    score = minMaxO();
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = row;
                        bestColoumn = coloumn;
                    }
                    squareText[row][coloumn] = ' ';
                    squares[row][coloumn].setText("");
                }
            }
        squareText[bestRow][bestColoumn] = 'X';
        squares[bestRow][bestColoumn].setText("X");
        squares[bestRow][bestColoumn].setTextColor(Color.RED);
        squares[bestRow][bestColoumn].setEnabled(false);
    }

    protected int minMaxO() {
        int score = score();
        if (gameOver()) {
            return score;
        }
        int bestScore = 2;
        for (int row = 0; row < 3; row++) {
            for (int coloumn = 0; coloumn < 3; coloumn++) {
                if (squareText[row][coloumn] == ' ') {
                    squares[row][coloumn].setText("O");
                    squareText[row][coloumn] = 'O';
                    score = minMaxX();
                    if (score < bestScore) {
                        bestScore = score;
                    }
                    squares[row][coloumn].setText("");
                    squareText[row][coloumn] = ' ';
                }
            }
        }
        return bestScore;
    }

    protected int minMaxX() {
        int score = score();
        if (gameOver()) {
            return score;
        }
        int bestScore = -2;
        for (int row = 0; row < 3; row++) {
            for (int coloumn = 0; coloumn < 3; coloumn++) {
                if (squareText[row][coloumn] == ' ') {
                    squares[row][coloumn].setText("X");
                    squareText[row][coloumn] = 'X';
                    score = minMaxO();
                    if (score > bestScore) {
                        bestScore = score;
                    }
                    squares[row][coloumn].setText("");
                    squareText[row][coloumn] = ' ';
                }
            }
        }
        return bestScore;
    }


}



