package com.example.lenovo.myapplication;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * created by Qin yiyi 16301087@bjtu.edu.cn
 */

public class Main_Fragment extends Fragment {

    private String username = "";

    public Main_Fragment(){
    }

    @SuppressLint("ValidFragment")
    public Main_Fragment(String username){
        this.username = username;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        //不能使用View view=inflater.inflate(R.layout.main_fragment,null); 否则会错位
        if(!username.equals("")){
            TextView username_text = view.findViewById(R.id.name);
            username_text.setText(username);
        }

        return view;
    }

    //在fragment不能直接进行点击事件，需要放到oncreatActivity中
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OnClick onclick = new OnClick();
        final ImageButton feed = getActivity().findViewById(R.id.imageButton_feed);
        feed.setOnClickListener(onclick);
    }
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.imageButton_feed:
                    intent = new Intent(getActivity(),VideoList.class);
                    break;
            }
            startActivity(intent);
        }
    }

}
