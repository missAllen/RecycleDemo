package com.miles.zcstc.recycledemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private Context mContext;//上下文对象
    private ArrayList<Word> mWords;
    private LayoutInflater mInflater;

    public WordAdapter(Context context, ArrayList<Word> words) {
        mContext = context;
        mWords = words;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.word_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Word word = mWords.get(position);
        holder.mTextViewWordEnglish.setText(word.getEnglish());
        holder.mTextViewWordChinese.setText("释义:"+word.getChinese());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击的单词是:"+ mWords.get(position).getEnglish()+",中文是:"+mWords.get(position).getChinese(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewWordEnglish;
        public TextView mTextViewWordChinese;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewWordEnglish = itemView.findViewById(R.id.word_english);
            mTextViewWordChinese = itemView.findViewById(R.id.word_chinese);

        }
    }
}

