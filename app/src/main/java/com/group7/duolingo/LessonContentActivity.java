package com.group7.duolingo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import DAL.LessonManager;
import DAL.OnGetDataListener;
import entities.Question;

public class LessonContentActivity extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fm;
    LessonManager lessonManager;
    Queue<Question> questionsQueue;
    Question question;
    Button buttonCheck;
    String answer = "";
    Boolean isAnswered = false;
    String[] questionParts;
    Boolean showNextQuestion = false;
    ProgressBar progressBar;
    int correctAnswers = 0;

    TextToSpeech tts;
    AlertDialog.Builder quitLessonAlertDialogBuilder;
    AlertDialog quitLessonAlertDialog;
    MediaPlayer correctSound;
    MediaPlayer incorrectSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_content);

        fm = getSupportFragmentManager();
        lessonManager = new LessonManager();

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                } else {
                    Toast.makeText(getApplicationContext(), "Init Text-to-speech fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        correctSound = MediaPlayer.create(getApplicationContext(), R.raw.correct_answer);
        incorrectSound = MediaPlayer.create(getApplicationContext(), R.raw.incorrect_answer);

        buttonCheck = findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showNextQuestion) {
                    startQuiz(questionsQueue);
                    showNextQuestion = false;
                    return;
                }
                if(answer.isEmpty()) {
                    return;
                } else if(answer.equalsIgnoreCase(questionParts[questionParts.length - 1])) {
                    correctSound.start();
                    correctAnswers++;
                    progressBar.setProgress(correctAnswers*100/(correctAnswers + questionsQueue.size()));
                    buttonCheck.setText("Correct. Next!");
                    isAnswered = true;
                    showNextQuestion = true;
                } else {
                    incorrectSound.start();
                    questionsQueue.add(question);
                    progressBar.setProgress(correctAnswers*100/(correctAnswers + questionsQueue.size()));
                    buttonCheck.setText("Incorrect. Try again!");
                    buttonCheck.setBackground(getResources().getDrawable(R.drawable.button_border_corner_red));
                    isAnswered = true;
                    showNextQuestion = true;
                }
            }
        });
        // progressbar
        progressBar = findViewById(R.id.progressBar);

        // quitLessonAlertDialog
        quitLessonAlertDialogBuilder = new AlertDialog.Builder(this);
        quitLessonAlertDialogBuilder.setTitle("Are you sure?");
        quitLessonAlertDialogBuilder.setMessage("You will lose your progress in this lesson");
        quitLessonAlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                quitLessonAlertDialog.dismiss();
            }
        });
        quitLessonAlertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quitLessonAlertDialog.dismiss();
            }
        });
        quitLessonAlertDialog = quitLessonAlertDialogBuilder.create();

        final int lessonId = getIntent().getExtras().getInt("lessonId");
        lessonManager.getAllQuestions(lessonId, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(QuerySnapshot data) {
                questionsQueue = new LinkedList<>();
                for (QueryDocumentSnapshot document : data) {
                    questionsQueue.add(new Question(
                            Integer.valueOf(document.getLong("id").toString()),
                            lessonId,
                            document.getString("question_type"),
                            document.getString("content")
                    ));
                }

                startQuiz(questionsQueue);
            }

            @Override
            public void onFailed(Exception error) {

            }
        });

    }

    public void setAnswer(String answer) {
        buttonCheck.setText("Check");
        buttonCheck.setBackground(getResources().getDrawable(R.drawable.button_border_corner_green));
        this.answer = answer;
    }

    public Boolean getIsAnswered(){
        return isAnswered;
    }

    public void startQuiz(Queue<Question> questionsQueue){
        if(questionsQueue.size() == 0) {
            if(tts != null) {
                tts.stop();
                tts.shutdown();
            }
            Intent congratsIntent = new Intent(this, CongratsActivity.class);
            startActivity(congratsIntent);
        } else {
            resetActivity();
            if(fragment != null) {
                fm.beginTransaction().hide(fragment).commit();
            }
            // Get first question from queue and remove it from queue
            question = questionsQueue.poll();
            questionParts = question.getContent().split("\\|");
            Bundle bundle = new Bundle();
            switch (question.getQuestionType().toLowerCase()) {
                case "text_picture":
                    bundle.putSerializable("questionParts", questionParts);
                    fragment = new QuestionTextPictureFragment();
                    fragment.setArguments(bundle);
                    fm.beginTransaction().add(R.id.questionContainer, fragment, "question_text_picture").commit();
                    break;
                case "listen_picture":
                    bundle.putSerializable("questionParts", questionParts);
                    fragment = new QuestionListenPictureFragment();
                    fragment.setArguments(bundle);
                    fm.beginTransaction().add(R.id.questionContainer, fragment, "question_listen_picture").commit();
                    break;
                case "multiple_choice":
                    bundle.putSerializable("questionParts", questionParts);
                    fragment = new QuestionMultipleChoiceFragment();
                    fragment.setArguments(bundle);
                    fm.beginTransaction().add(R.id.questionContainer, fragment, "question_multiple_choice").commit();
                    break;
            }
        }
    }

    public void resetActivity() {
        answer = "";
        isAnswered = false;
        buttonCheck.setText("Check");
        buttonCheck.setBackground(getResources().getDrawable(R.drawable.button_border_corner_gray));
    }

    public void ttsSpeak(String input) {
        tts.speak(input, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void showQuitLessonAlertDialog(View view) {
        quitLessonAlertDialog.show();

    }
}
