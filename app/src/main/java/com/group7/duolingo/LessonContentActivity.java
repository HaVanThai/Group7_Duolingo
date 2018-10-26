package com.group7.duolingo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LessonContentActivity extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_content);

        fragment = new QuestionTextPictureFragment();
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.lessonContentActivity, fragment, "question_text_picture").commit();
    }
}
