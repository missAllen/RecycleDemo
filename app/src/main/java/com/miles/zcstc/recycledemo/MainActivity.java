package com.miles.zcstc.recycledemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;     //定义recycle view
    private WordAdapter mWordAdapter;       //定义适配器
    private WordItemDecoration mItemDecoration; //定义装饰
    private ArrayList<Word> mWords;     //定义数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRecyclerView = findViewById(R.id.dictionary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordAdapter = new WordAdapter(this,mWords);
        mRecyclerView.setAdapter(mWordAdapter);

        mItemDecoration = new WordItemDecoration(this,mWords);
        mRecyclerView.addItemDecoration(mItemDecoration);
    }

    private void initData(){
        mWords = new ArrayList<>();
        mWords.add(new Word("absorb","吸收;吸引"));
        mWords.add(new Word("absurd","荒唐的"));
        mWords.add(new Word("acceptable","可接受的"));
        mWords.add(new Word("admit","承认"));
        mWords.add(new Word("advise","建议"));
        mWords.add(new Word("advocate","提倡，倡导"));
        mWords.add(new Word("back","背面，后部"));
        mWords.add(new Word("bad","坏的，有害的"));
        mWords.add(new Word("balloon","气球"));
        mWords.add(new Word("cafe","咖啡馆"));
        mWords.add(new Word("cake","蛋糕"));
        mWords.add(new Word("calculation","计算，计算结果"));
        mWords.add(new Word("calendar","日历，历书"));
        mWords.add(new Word("cherish","希望"));
        mWords.add(new Word("damage","损害，毁坏"));
        mWords.add(new Word("dancer","舞者; 舞女"));
        mWords.add(new Word("danger","危险"));
        mWords.add(new Word("each","各，各自"));
        mWords.add(new Word("earphone","耳机"));
        mWords.add(new Word("east","东,东方"));
        mWords.add(new Word("factory","工厂，制造厂"));
        mWords.add(new Word("fake","假货，膺品"));
        mWords.add(new Word("garbage",".垃圾，污物，废料"));
        mWords.add(new Word("gasolene","汽油"));
        mWords.add(new Word("gather","推测，推断"));

    }
}


