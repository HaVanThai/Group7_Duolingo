package com.group7.duolingo;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import utils.StringUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListenPictureFragment extends Fragment
        implements View.OnClickListener{

    String[] questionParts;
    String[] pictures;
    ImageView questionImage1;
    ImageView questionImage2;
    ImageView questionImage3;
    ImageView questionImage4;
    ImageView imageViewVolume;



    public String answer = "";

    public QuestionListenPictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_listen_picture, container, false);

        questionParts = (String[]) getArguments().getSerializable("questionParts");
        pictures = questionParts[1].split(";");

        imageViewVolume = view.findViewById(R.id.imageViewVolume);
        questionImage1 = view.findViewById(R.id.questionImage1);
        questionImage2 = view.findViewById(R.id.questionImage2);
        questionImage3 = view.findViewById(R.id.questionImage3);
        questionImage4 = view.findViewById(R.id.questionImage4);

        imageViewVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LessonContentActivity)getActivity()).ttsSpeak(questionParts[0]);
            }
        });
        questionImage1.setImageResource(getResources().getIdentifier(
                pictures[0],
                "drawable",
                getActivity().getPackageName()
        ));
        questionImage2.setImageResource(getResources().getIdentifier(
                pictures[1],
                "drawable",
                getActivity().getPackageName()
        ));
        questionImage3.setImageResource(getResources().getIdentifier(
                pictures[2],
                "drawable",
                getActivity().getPackageName()
        ));
        questionImage4.setImageResource(getResources().getIdentifier(
                pictures[3],
                "drawable",
                getActivity().getPackageName()
        ));

        questionImage1.setOnClickListener(this);
        questionImage2.setOnClickListener(this);
        questionImage3.setOnClickListener(this);
        questionImage4.setOnClickListener(this);

        // Speak right after load question
        ((LessonContentActivity)getActivity()).ttsSpeak(questionParts[0]);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(((LessonContentActivity)getActivity()).getIsAnswered()) {
            return;
        }
        questionImage1.setBackground(getResources().getDrawable(R.drawable.question_image_view_border));
        questionImage2.setBackground(getResources().getDrawable(R.drawable.question_image_view_border));
        questionImage3.setBackground(getResources().getDrawable(R.drawable.question_image_view_border));
        questionImage4.setBackground(getResources().getDrawable(R.drawable.question_image_view_border));
        v.setBackground(getResources().getDrawable(R.drawable.question_image_view_border_selected));
        switch (v.getId()) {
            case R.id.questionImage1:
                answer = pictures[0];
                break;
            case R.id.questionImage2:
                answer = pictures[1];
                break;
            case R.id.questionImage3:
                answer = pictures[2];
                break;
            case R.id.questionImage4:
                answer = pictures[3];
                break;
        }

        ((LessonContentActivity)getActivity()).setAnswer(answer);
    }

}
