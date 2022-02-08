package com.example.lovetogether.Setting;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.lovetogether.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Bitmap> listImage;

    public GalleryAdapter(Context context, int layout, ArrayList<Bitmap> listImage) {
        this.context = context;
        this.layout = layout;
        this.listImage = listImage;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        ImageView imageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_cell);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Bitmap image = listImage.get(i);
        viewHolder.imageView.setImageBitmap(image);
        return view;
    }
}
