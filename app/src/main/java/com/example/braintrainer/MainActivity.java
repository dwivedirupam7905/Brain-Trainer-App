package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // To start the game
    Button goButton;

    // To play again
    Button playAgainButton;

    // Defining answer buttons
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    // Defining new constraint layout (which holds the whole game state)
    ConstraintLayout gameLayout;

    // To store the correct and wrong answers
    ArrayList<Integer> answers = new ArrayList<>();

    int locationOfCorrectAnswer;
    TextView resultTextView, scoreTextView, sumTextView, timerTextView, hiScoreTextView;
    GridLayout gridLayout;
    String correct = "Correct!";
    String wrong = "Wrong :(";
    String done = "Done!";
    String scoreRatio = "0/0";
    String timer = "30s";
    String highScore;
    int score = 0, hiScore = 0;
    int numberOfQuestions = 0;

    // Function to begin the game
    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(timerTextView);
    }

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        timer = "30s";
        timerTextView.setText(timer);
        resultTextView.setVisibility(View.INVISIBLE);
        makeButtonsEnabled();

        // Updating the score ratio
        scoreRatio = score + "/" + numberOfQuestions;
        scoreTextView.setText(scoreRatio);

        // Resetting new question
        getNewQuestion();

        // As the game starts, making playAgain button invisible
        playAgainButton.setVisibility(View.INVISIBLE);

        // Setting-up the timer of 30 seconds
        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long l) {
                timer = (l / 1000) + "s";
                timerTextView.setText(timer);
            }

            @Override
            public void onFinish() {
                String endTime = "0s";
                timerTextView.setText(endTime);
                resultTextView.setText(done);

                // Setting hiScore at the end of timer
                if(score == 0){
                    highScore = "HI SCORE: " + score;
                    hiScoreTextView.setText(highScore);
                }
                if(score > hiScore){
                    hiScore = score;
                    highScore = "HI SCORE: " + hiScore;
                    hiScoreTextView.setText(highScore);
                }

                Toast.makeText(MainActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timer);
                mediaPlayer.start();

                // As the game ends, making playAgain button and hiScoreTextView visible
                playAgainButton.setVisibility(View.VISIBLE);
                hiScoreTextView.setVisibility(View.VISIBLE);

                makeButtonsDisabled();
            }
        }.start();
    }

    public void chooseAnswer(View view){
        // Checking whether the answer is correct or not
        if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            resultTextView.setText(correct);
            score++;
        }
        else
            resultTextView.setText(wrong);

        numberOfQuestions++;

        // Making the result textView visible when any answer is given
        resultTextView.setVisibility(View.VISIBLE);

        // Updating the score ratio
        scoreRatio = score + "/" + numberOfQuestions;
        scoreTextView.setText(scoreRatio);

        // Creating new question after each click
        getNewQuestion();
    }

    public void getNewQuestion(){
        Random rand = new Random();
        int a = rand.nextInt(51);
        int b = rand.nextInt(51);
        int correctAnswer = (a+b);

        String newSum = a + " + " + b;
        sumTextView.setText(newSum);

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        // Storing the correct and wrong answers in the arrayList
        for(int i=0 ; i<4 ; i++){
            if(i == locationOfCorrectAnswer)
                answers.add(correctAnswer);
            else {
                int wrongAnswer = rand.nextInt(101);
                while(wrongAnswer == (a+b))
                    wrongAnswer = rand.nextInt(101);
                answers.add(wrongAnswer);
            }
        }
        button0.setText(String.valueOf(answers.get(0)));
        button1.setText(String.valueOf(answers.get(1)));
        button2.setText(String.valueOf(answers.get(2)));
        button3.setText(String.valueOf(answers.get(3)));
    }

    public void makeButtonsDisabled(){
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
    }

    public void makeButtonsEnabled(){
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);

        // Making go button visible initially
        goButton.setVisibility(View.VISIBLE);

        playAgainButton = findViewById(R.id.playAgainButton);

        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        hiScoreTextView = findViewById(R.id.hiScoreTextView);
        gridLayout = findViewById(R.id.gridLayout);

        gameLayout = findViewById(R.id.gameLayout);

        // Making gameLayout and hiScoreTextView invisible initially
        gameLayout.setVisibility(View.INVISIBLE);
        hiScoreTextView.setVisibility(View.INVISIBLE);

        // Accessing all the answer buttons
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
    }
}