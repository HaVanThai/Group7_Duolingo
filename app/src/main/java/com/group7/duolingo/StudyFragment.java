package com.group7.duolingo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import DAL.LessonManager;
import DAL.OnGetDataListener;
import entities.Lesson;
import utils.DpiUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {

    ArrayList<Lesson> lessons;
    LessonManager lessonManager;

    public StudyFragment() {
        // Required empty public constructor
        lessonManager = new LessonManager();
        lessons = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_study, container, false);

        lessonManager.getAllLessons(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(QuerySnapshot data) {
                for (QueryDocumentSnapshot document : data) {
                    lessons.add(
                            new Lesson(
                                    Integer.valueOf(document.getLong("id").toString()),
                                    Integer.valueOf(document.getLong("lesson_group_id").toString()),
                                    document.getString("name")
                            ));
                }
                drawLessonUI(view, lessons);
            }

            @Override
            public void onFailed(Exception error) {

            }
        });

        return view;
    }

    public void drawLessonUI(View view, ArrayList<Lesson> lessons){
        // Root LinearLayout in ScrollView
        LinearLayout scrollViewContainer = view.findViewById(R.id.scroll_view_container);

        int index = 0;
        int rowSize = 1;
        int itemLayoutPadding = DpiUtils.toPixels(20, getResources().getDisplayMetrics());
        int cardViewPadding = DpiUtils.toPixels(10, getResources().getDisplayMetrics());
        int parentCardViewRadius = DpiUtils.toPixels(40, getResources().getDisplayMetrics());
        int childCardViewRadius = DpiUtils.toPixels(30, getResources().getDisplayMetrics());
        int imageViewSize = DpiUtils.toPixels(40, getResources().getDisplayMetrics());
        while (index < lessons.size()) {

            // Row LinearLayout in scrollViewContainer
            LinearLayout rowLayout = new LinearLayout(getContext());
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);

            if(index == 0 || index == lessons.size() - 1) {
                rowSize = 1;
            } else {
                rowSize = 2;
            }
            for(int i = 0; i < rowSize && index < lessons.size(); i++) {
                // Item LinearLayout in rowLayout
                LinearLayout itemLayout = new LinearLayout(getContext());
                itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                itemLayout.setOrientation(LinearLayout.VERTICAL);
                itemLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                itemLayout.setPadding(itemLayoutPadding, itemLayoutPadding, itemLayoutPadding, 0);

                // Parent CardView
                CardView parentCardView = new CardView(getContext());
                parentCardView.setLayoutParams(new LinearLayout.LayoutParams(
                        2*parentCardViewRadius,
                        2*parentCardViewRadius
                ));
                parentCardView.setRadius(parentCardViewRadius);
                parentCardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightGray));
                parentCardView.setContentPadding(cardViewPadding, cardViewPadding, cardViewPadding, cardViewPadding);

                // Child CardView
                CardView childCardView = new CardView(getContext());
                childCardView.setLayoutParams(new LinearLayout.LayoutParams(
                        2 * childCardViewRadius,
                        2 * childCardViewRadius
                ));
                childCardView.setRadius(childCardViewRadius);
                childCardView.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                childCardView.setContentPadding(cardViewPadding, cardViewPadding, cardViewPadding, cardViewPadding);

                // ImageView inside child CardView
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        imageViewSize,
                        imageViewSize
                ));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(getResources().getIdentifier("ant", "drawable", getContext().getPackageName()));

                // TextView inside Item LinearLayout
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                textView.setText(lessons.get(index++).getName());

                // Add event
                parentCardView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent lessonItent = new Intent(getActivity(), LessonContentActivity.class);
                        startActivity(lessonItent);
                    }
                });

                // Constraints
                childCardView.addView(imageView);
                parentCardView.addView(childCardView);

                itemLayout.addView(parentCardView);
                itemLayout.addView(textView);

                rowLayout.addView(itemLayout);
            }

            scrollViewContainer.addView(rowLayout);
        }
    }

}
