package com.example.cardgame.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cardgame.Adapter.RecordAdapter;
import com.example.cardgame.CallBack.CallBack_List;
import com.example.cardgame.R;
import com.example.cardgame.objects.Record;
import com.example.cardgame.objects.TopTen;

import java.util.List;


public class ListFragment extends Fragment {

    private ListView list_LV_score;
    private TopTen scoreBoard = new TopTen();
    private CallBack_List callBack_list;

    //initialize Call Back
    public void setCallBack_list(CallBack_List callBack_list) {
        this.callBack_list = callBack_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        list_LV_score = view.findViewById(R.id.list_LV_score);

        Intent i = getActivity().getIntent();
        scoreBoard = (TopTen) i.getSerializableExtra("SCORE_BOARD");

        //create new adapter
        RecordAdapter recordAdapter = new RecordAdapter(this.getActivity(), R.layout.fragment_list_adapter, scoreBoard.getRecords());
        list_LV_score.setAdapter(recordAdapter);

         //zoom to map when click on item in score board
        list_LV_score.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callBack_list != null){
                    callBack_list.sendScoreBoardId(position);
                }
            }
        });

        return view;
    }
}

