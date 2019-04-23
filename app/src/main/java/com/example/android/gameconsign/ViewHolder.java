package com.example.android.gameconsign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by TOSHIBA on 4/23/2019.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder (View itemVIew){
        super(itemVIew);

        mView = itemVIew;
    }

    public void setDetails(Context ctx, String title, String image){

        TextView mTitleView = mView.findViewById(R.id.judul);
        ImageView mImage = mView.findViewById(R.id.imageV);

        mTitleView.setText(title);
        Picasso.with(ctx).load(image).into(mImage);
    }

}
