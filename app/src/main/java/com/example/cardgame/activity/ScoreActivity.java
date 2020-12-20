package com.example.cardgame.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.cardgame.callBack.CallBack_List;
import com.example.cardgame.R;
import com.example.cardgame.fragments.ListFragment;
import com.example.cardgame.fragments.MapFragment;
import com.example.cardgame.objects.TopTen;
import com.example.cardgame.utils.MySPV;
import com.google.gson.Gson;

public class ScoreActivity extends BaseActivity {
    public static final String SCORE_BOARD = "SCORE_BOARD";
    private FrameLayout score_LAY_list;
    private FrameLayout score_LAY_map;
    private ListFragment fragment_list;
    private MapFragment fragment_map;
    private Button score_BTN_backMenu;

    private CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void sendScoreBoardId(int id) {
            fragment_map.showMarker(id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        findViews();

        fragment_list = new ListFragment();
        fragment_list.setCallBack_list(callBack_list);

        getSupportFragmentManager().beginTransaction().add(R.id.score_LAY_list, fragment_list).commit();

        //send score board to list and map fragment
        getIntent().putExtra(SCORE_BOARD, openScoreBoard());


        fragment_map = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.score_LAY_map, fragment_map).commit();


        score_BTN_backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        score_LAY_list = findViewById(R.id.score_LAY_list);
        score_LAY_map = findViewById(R.id.score_LAY_map);
        score_BTN_backMenu = findViewById(R.id.score_BTN_backMenu);
    }

    /**
     * get saved score board, if score board is null - create new
     *
     * @return Score Board
     */
    public TopTen openScoreBoard() {
        TopTen score_board;
        Gson gson = new Gson();

        MySPV.init(this);
        String topTen = MySPV.getInstance().getString(MySPV.KEYS.KEY_SCORE_BOARD, null);
        if (topTen == null) {
            score_board = new TopTen();
        } else {
            score_board = gson.fromJson(topTen, TopTen.class);
        }
        return score_board;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}