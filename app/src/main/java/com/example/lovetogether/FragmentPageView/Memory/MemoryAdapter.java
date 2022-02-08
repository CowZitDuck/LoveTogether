package com.example.lovetogether.FragmentPageView.Memory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lovetogether.R;

import java.util.ArrayList;

public class MemoryAdapter extends BaseAdapter {

    private int layout;
    private Context context;
    private ArrayList<Memory> list_memory;

    public MemoryAdapter(int layout, Context context, ArrayList<Memory> list_memory) {
        this.layout = layout;
        this.context = context;
        this.list_memory = list_memory;
    }

    @Override
    public int getCount() {
        return list_memory.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        private TextView tv_name;
        private TextView tv_date;
        private TextView tv_content;
        private ImageView img_image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.img_image = (ImageView) view.findViewById(R.id.img_memory);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content_memory);
            viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_date_memory);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_title_memory);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        byte[] image = list_memory.get(i).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        viewHolder.img_image.setImageBitmap(bitmap);
        viewHolder.tv_name.setText(list_memory.get(i).getName());
        viewHolder.tv_content.setText(list_memory.get(i).getContent());
        viewHolder.tv_date.setText(list_memory.get(i).getDate());
        return view;
    }
}
