package com.group7.duolingo;


import android.media.Image;
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

import utils.DpiUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {


    public StudyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study, container, false);

        // Root LinearLayout in ScrollView
        LinearLayout scrollViewContainer = view.findViewById(R.id.scroll_view_container);

        // Row LinearLayout in scrollViewContainer
        LinearLayout rowLayout = new LinearLayout(getContext());
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Item LinearLayout in rowLayout
        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Parent CardView
        CardView parentCardView = new CardView(getContext());
        parentCardView.setLayoutParams(new LinearLayout.LayoutParams(
                DpiUtils.toPixels(80, getResources().getDisplayMetrics()),
                DpiUtils.toPixels(80, getResources().getDisplayMetrics())
        ));
        parentCardView.setRadius(DpiUtils.toPixels(40, getResources().getDisplayMetrics()));
        parentCardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightGray));
        int paddingDpi = DpiUtils.toPixels(10, getResources().getDisplayMetrics());
        parentCardView.setContentPadding(paddingDpi, paddingDpi, paddingDpi, paddingDpi);

        // Child CardView
        CardView childCardView = new CardView(getContext());
        childCardView.setLayoutParams(new LinearLayout.LayoutParams(
                DpiUtils.toPixels(60, getResources().getDisplayMetrics()),
                DpiUtils.toPixels(60, getResources().getDisplayMetrics())
        ));
        childCardView.setRadius(DpiUtils.toPixels(30, getResources().getDisplayMetrics()));
        childCardView.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
        childCardView.setContentPadding(paddingDpi, paddingDpi, paddingDpi, paddingDpi);

        // ImageView inside child CardView
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                DpiUtils.toPixels(40, getResources().getDisplayMetrics()),
                DpiUtils.toPixels(40, getResources().getDisplayMetrics())
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
        textView.setText("First Lesson");

        // Constraints
        childCardView.addView(imageView);
        parentCardView.addView(childCardView);

        itemLayout.addView(parentCardView);
        itemLayout.addView(textView);

        rowLayout.addView(itemLayout);

        scrollViewContainer.addView(rowLayout);

        return view;
    }

}
