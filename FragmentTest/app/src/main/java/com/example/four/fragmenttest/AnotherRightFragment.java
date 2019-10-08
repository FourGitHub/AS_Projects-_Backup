package com.example.four.fragmenttest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/5 0005.
 */

public class AnotherRightFragment extends Fragment {
    private static final String TAG = "AnotherRightFragment";

    @Override

    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        Log.i(TAG, "--> onInflate()");
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "--> onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "--> onCreate()");
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "--> onCreateView()");
        View view = inflater.inflate(R.layout.another_right_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "--> onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.button_in_another_right_frag);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) getActivity().findViewById(R.id.text_in_left_frag);
                Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "--> onViewStateRestored()");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "--> onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "--> onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "--> onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "--> onStop()");
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "--> onDestroyView()");
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "--> onDestroy()");
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        Log.i(TAG, "--> onDetach()");
        super.onDetach();

    }
}
