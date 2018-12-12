package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by Qin yiyi 16301087@bjtu.edu.cn
 */

public class Find_Fragment extends Fragment {
    RecyclerView recyclerView;
    LinearAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_fragment,container,false);
        recyclerView = view.findViewById(R.id.RecyclerView);
        adapter = new LinearAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        // recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        /*recyclerView.setOnItemClickListener(new adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view) {
                //此处进行监听事件的业务处理
                Toast.makeText(getActivity(),"Class",Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;
    }


}
