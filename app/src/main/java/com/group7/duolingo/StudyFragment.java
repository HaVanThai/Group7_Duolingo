package com.group7.duolingo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                                    document.getString("lesson_group"),
                                    document.getString("name")
                            ));
                }
                drawLessonUI(view, lessons);
            }

            @Override
            public void onFailed(Exception error) {
                Toast.makeText(getActivity(), "faild to connect to firebase", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void drawLessonUI(View view, final ArrayList<Lesson> lessons){
        int[] colors = {
                R.color.colorRed,
                R.color.colorYellow,
                R.color.colorGreen,
                R.color.colorBlue
        };
        // Root LinearLayout in ScrollView
        LinearLayout scrollViewContainer = view.findViewById(R.id.scroll_view_container);

        int index = 0;
        int rowSize;
        int itemLayoutPadding = DpiUtils.toPixels(20, getResources().getDisplayMetrics());
        int stroke = DpiUtils.toPixels(10, getResources().getDisplayMetrics());
        int imageSize = DpiUtils.toPixels(80, getResources().getDisplayMetrics());
        int imagePadding = DpiUtils.toPixels(20, getResources().getDisplayMetrics());

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

                // ImageView in Item LinearLayout
                GradientDrawable gD = new GradientDrawable();
                gD.setShape(GradientDrawable.OVAL);
                gD.setStroke(stroke, getResources().getColor(R.color.colorLightGray));
                int randColor = (int) Math.floor(Math.random()*4);
                gD.setColor(getResources().getColor(colors[randColor]));
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        imageSize,
                        imageSize
                ));
                imageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
                imageView.setImageResource(getResources().getIdentifier("duolingo", "drawable", getContext().getPackageName()));
                imageView.setBackground(gD);

                // TextView inside Item LinearLayout
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                final Lesson lesson = lessons.get(index);
                textView.setText(lesson.getName());

                // Add event
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent lessonIntent = new Intent(getActivity(), LessonContentActivity.class);
                        lessonIntent.putExtra("lessonId", lesson.getId());
                        startActivity(lessonIntent);
                    }
                });

                // Constraints
                itemLayout.addView(imageView);
                itemLayout.addView(textView);

                rowLayout.addView(itemLayout);
                index++;
            }

            scrollViewContainer.addView(rowLayout);
        }

    }

}
