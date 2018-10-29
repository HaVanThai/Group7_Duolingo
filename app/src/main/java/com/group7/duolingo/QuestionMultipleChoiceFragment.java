package com.group7.duolingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import utils.StringUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionMultipleChoiceFragment extends Fragment
        implements RadioGroup.OnCheckedChangeListener{

    String[] questionParts;
    String[] answerOptions;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    TextView textViewQuestion;

    public String answer = "";


    public QuestionMultipleChoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_multiple_choice, container, false);
        questionParts = (String[]) getArguments().getSerializable("questionParts");
        answerOptions = questionParts[1].split(";");

        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton3 = view.findViewById(R.id.radioButton3);
        radioButton4 = view.findViewById(R.id.radioButton4);

        textViewQuestion.setText(StringUtils.upperCaseFirstLetter(questionParts[0]));
        radioButton1.setText(answerOptions[0]);
        radioButton2.setText(answerOptions[1]);
        radioButton3.setText(answerOptions[2]);
        radioButton4.setText(answerOptions[3]);

        radioGroup.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(((LessonContentActivity)getActivity()).getIsAnswered()) {
            return;
        }

        switch (checkedId) {
            case R.id.radioButton1:
                answer = answerOptions[0];
                break;
            case R.id.radioButton2:
                answer = answerOptions[1];
                break;
            case R.id.radioButton3:
                answer = answerOptions[2];
                break;
            case R.id.radioButton4:
                answer = answerOptions[3];
                break;
        }

        ((LessonContentActivity)getActivity()).setAnswer(answer);
    }
}
