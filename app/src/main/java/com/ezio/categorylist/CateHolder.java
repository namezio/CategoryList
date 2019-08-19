package com.ezio.categorylist;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CateHolder extends RecyclerView.ViewHolder {
    public RelativeLayout imgBack;
    public TextView tvContent,tvCount;
    public CateHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tvContent);
        tvCount = itemView.findViewById(R.id.tvCount);
        imgBack = itemView.findViewById(R.id.imgBack);
    }
}
