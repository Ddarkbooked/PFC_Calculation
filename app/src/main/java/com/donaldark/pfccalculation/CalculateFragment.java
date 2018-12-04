package com.donaldark.pfccalculation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CalculateFragment extends Fragment {

    private static final String TAG = "CalculateFragment";
    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private EditText growthType;
    private EditText weightType;
    private EditText ageType;
    private TextView resultView;
    private LinearLayout getResultButton;
    private LinearLayout resultViewButton;
    private ConstraintLayout constraintLayout;

    private LinearLayout tab1;


    private OnFragmentInteractionListener mListener;




    public CalculateFragment() {
    }

    public static CalculateFragment newInstance(String param1, String param2) {
        CalculateFragment fragment = new CalculateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_calculate, container, false);

        growthType = (EditText) view.findViewById(R.id.growth_type);
        weightType = (EditText) view.findViewById(R.id.weight_type);
        ageType = (EditText) view.findViewById(R.id.age_type);
        resultView = (TextView) view.findViewById(R.id.result_view);
        resultViewButton = (LinearLayout) view.findViewById(R.id.result_view_button);
        getResultButton = (LinearLayout) view.findViewById(R.id.get_result_button);
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraint_layout);
        tab1 = (LinearLayout) view.findViewById(R.id.tab_1);




        //Вызов метода из MainActivity//
     final MainActivity activity = (MainActivity) getActivity();
     activity.closeKeyboard();

     resultViewButton.setVisibility(View.GONE);

        getResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionToCalculate(v);
                activity.closeKeyboard();

                String checkGrowth = growthType.getText().toString();
                String checkWeight = weightType.getText().toString();
                String checkAge = ageType.getText().toString();

                if (checkGrowth.isEmpty() || checkWeight.isEmpty() || checkAge.isEmpty()) {
                    resultViewButton.setVisibility(View.GONE);
                    return;
                } else if(checkGrowth.matches(".") || checkWeight.matches(".") || checkAge.matches(".")) {
                    Snackbar.make(growthType, "Введите ваши правильные данные", Snackbar.LENGTH_LONG).show();
                    return;
                }
                calculatePFC();
                resultViewButton.setVisibility(View.VISIBLE);
            }


        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.closeKeyboard();
            }
        });

//        tab1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:{
//                        ObjectAnimator colorFade = ObjectAnimator.ofObject(tab1, "backgroundColor" /*view attribute name*/, new ArgbEvaluator(), activity.getApplicationContext().getResources().getColor(R.color.white) /*from color*/, activity.getApplicationContext().getResources().getColor(R.color.blue) /*to color*/);
//                        colorFade.setDuration(500);
//                        colorFade.setStartDelay(200);
//                        colorFade.start();
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP:{
//                        ObjectAnimator colorFade = ObjectAnimator.ofObject(tab1, "backgroundColor" /*view attribute name*/, new ArgbEvaluator(), activity.getApplicationContext().getResources().getColor(R.color.blue) /*from color*/, activity.getApplicationContext().getResources().getColor(R.color.white) /*to color*/);
//                        colorFade.setDuration(500);
//                        colorFade.setStartDelay(200);
//                        colorFade.start();
//                        break;
//                    }
//                }
//
//
//                return false;
//            }
//        });



        return view;
    }





    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void calculatePFC() {
        double growth, weight, age, result;

        String growthString = growthType.getText().toString();
        String weightString = weightType.getText().toString();
        String ageString = ageType.getText().toString();


        // Преобразуем текстовые переменные в числовые значения
        growth = Double.parseDouble(growthString);
        weight = Double.parseDouble(weightString);
        age = Double.parseDouble(ageString);

        result = 88.362 + (13.397 * weight) + (4.799 * growth) - (5.677 * age);

        String resultInString = Double.toString((int) result);
        int finalResult = (int) result;

        resultView.setText("Ваш БЖУ равен " + finalResult + " кал");
    }

    public void conditionToCalculate(View v) {

        String checkGrowth = growthType.getText().toString();
        String checkWeight = weightType.getText().toString();
        String checkAge = ageType.getText().toString();

        if (checkGrowth.isEmpty() && checkWeight.isEmpty() && checkAge.isEmpty()) {
            Snackbar.make(growthType, "Введите ваши данные", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (checkGrowth.isEmpty() && checkWeight.isEmpty()) {
            Snackbar.make(growthType, "Введите ваш рост и вес", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (checkAge.isEmpty() && checkWeight.isEmpty()) {
            Snackbar.make(growthType, "Введите ваш вес и возраст", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (checkGrowth.isEmpty() && checkAge.isEmpty()) {
            Snackbar.make(growthType, "Введите ваш рост и возраст", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (checkGrowth.isEmpty()) {
            Snackbar.make(growthType, "Введите ваш рост", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (checkWeight.isEmpty()) {
            Snackbar.make(growthType, "Введите ваш вес", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (checkAge.isEmpty()) {
            Snackbar.make(growthType, "Введите ваш возраст", Snackbar.LENGTH_LONG).show();
            return;
        }
    }
}
