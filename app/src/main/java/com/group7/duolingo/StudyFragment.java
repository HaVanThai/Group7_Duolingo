package com.group7.duolingo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import DAL.LessonManager;
import DAL.OnGetDataListener;
import entities.Lesson;
import utils.DpiUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {

//    ArrayList<Lesson> lessons;
    HashMap<String, ArrayList<Lesson>> hashMapLessons;
    ArrayList<String> groupLessons;
    LessonManager lessonManager;

    public StudyFragment() {
        // Required empty public constructor
        lessonManager = new LessonManager();
        hashMapLessons = new HashMap<>();
        groupLessons = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_study, container, false);

        final String progress = getArguments().getString("progress");
        final String[] progresses = progress.split("\\|");
        final String userDocId = getArguments().getString("userDocId");

        lessonManager.getAllLessons(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(QuerySnapshot data, DocumentReference docRef) {
                for (QueryDocumentSnapshot document : data) {
                    if(hashMapLessons.get(document.getString("lesson_group")) == null) {
                        groupLessons.add(document.getString("lesson_group"));
                        hashMapLessons.put(
                                document.getString("lesson_group"),
                                new ArrayList<Lesson>()
                        );
                    }
                    hashMapLessons.get(document.getString("lesson_group")).add(
                            new Lesson(
                                    Integer.valueOf(document.getLong("id").toString()),
                                    document.getString("lesson_group"),
                                    document.getString("name")
                            )
                    );
                }
                drawLessonUI(view, hashMapLessons, progresses, userDocId, progress);
            }

            @Override
            public void onFailed(Exception error) {
                Toast.makeText(getActivity(), "faild to connect to firebase", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void drawLessonUI(
            View view, final HashMap<String,
            ArrayList<Lesson>> hashMapLessons,
            String[] progresses,
            final String userDocId,
            final String progress) {
        int[] colors = {
                R.color.colorRed,
                R.color.colorPurple,
                R.color.colorGreen,
                R.color.colorBlue
        };
        // Root LinearLayout in ScrollView
        LinearLayout scrollViewContainer = view.findViewById(R.id.scroll_view_container);

        int rowSize;
        int itemLayoutPadding = DpiUtils.toPixels(20, getResources().getDisplayMetrics());
        int stroke = DpiUtils.toPixels(9, getResources().getDisplayMetrics());
        int imageSize = DpiUtils.toPixels(80, getResources().getDisplayMetrics());
        int imagePadding = DpiUtils.toPixels(20, getResources().getDisplayMetrics());
        int groupLessonMargin = DpiUtils.toPixels(30, getResources().getDisplayMetrics());

        for(String groupName : groupLessons) {
            // Text view group lessons
            TextView textViewGroupName = new TextView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, groupLessonMargin, 0, 0);
            textViewGroupName.setLayoutParams(layoutParams);
            textViewGroupName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            textViewGroupName.setTypeface(null, Typeface.BOLD);
            textViewGroupName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewGroupName.setText(groupName);

            // Add group's name text view to scrollViewContainer
            scrollViewContainer.addView(textViewGroupName);

            ArrayList<Lesson> lessons = hashMapLessons.get(groupName);
            int index = 0;
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
                    if(Arrays.asList(progresses).contains(String.valueOf(lessons.get(index).getId()))) {
                        gD.setStroke(stroke, getResources().getColor(R.color.colorYellow));
                    } else {
                        gD.setStroke(stroke, getResources().getColor(R.color.colorPrimaryDark));
                    }

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
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    final Lesson lesson = lessons.get(index);
                    textView.setText(lesson.getName());

                    // Add event
                    imageView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent lessonIntent = new Intent(getActivity(), LessonContentActivity.class);
                            lessonIntent.putExtra("lessonId", lesson.getId());
                            lessonIntent.putExtra("userDocId", userDocId);
                            lessonIntent.putExtra("progress", progress);
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

}
