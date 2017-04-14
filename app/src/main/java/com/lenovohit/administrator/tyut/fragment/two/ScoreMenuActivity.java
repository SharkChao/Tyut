package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.views.WaveView;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/3/16.
 */

public class ScoreMenuActivity extends BaseActivity implements View.OnClickListener{

//    private CircleMenuLayout mCircleMenuLayout;
    @Bind(R.id.suo)
    TextView suo;
    @Bind(R.id.ben)
    TextView ben;
    @Bind(R.id.shang)
    TextView shang;
    @Bind(R.id.bu)
    TextView bu;
    private LinearLayout linearLayout;
    private String[] mItemTexts = new String[] { "本学期成绩 ", "所有成绩", "上学期成绩",
            "不及格成绩" ,"特殊服务"};
    private int[] mItemImgs = new int[] { R.mipmap.buscore,
            R.mipmap.score1,R.mipmap.score2,R.mipmap.score3,R.mipmap.score4};
    private WaveView waveView;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_scoremenu);
//        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
//        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        linearLayout= (LinearLayout) findViewById(R.id.linearlayout);
        waveView = (WaveView) findViewById(R.id.wave);
        waveView.startAnim();
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
       suo.setOnClickListener(this);
        ben.setOnClickListener(this);
        shang.setOnClickListener(this);
        bu.setOnClickListener(this);

    }
    public static void startScoreMenuActivity(Context context){
        context.startActivity(new Intent(context,ScoreMenuActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //本学期成绩
            case R.id.ben:
                CurrentScoreActivity.StartCurrentScoreActivity(ScoreMenuActivity.this);
                break;
            //所有成绩
            case R.id.suo:
                AllScoreActivity.StartCurrentScoreActivity(ScoreMenuActivity.this);
                break;
            //上学期成绩
            case R.id.shang:
                LastScoreActivity.StartCurrentScoreActivity(ScoreMenuActivity.this);
                break;
            //不及格成绩
            case R.id.bu:
                BuScoreActivity.StartCurrentScoreActivity(ScoreMenuActivity.this);
                break;
            //特殊服务
            case 4:
                TeShuActivity.startTeShuActivity(ScoreMenuActivity.this);
                break;
        }
    }
}
