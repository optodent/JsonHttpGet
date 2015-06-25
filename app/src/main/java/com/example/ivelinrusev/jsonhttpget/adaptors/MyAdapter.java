package com.example.ivelinrusev.jsonhttpget.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ivelinrusev.jsonhttpget.R;


public class MyAdapter extends ArrayAdapter<String>{

    public MyAdapter(Context context,String[] list) {


        super(context, R.layout.adaptor_layout, list);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.adaptor_layout, parent, false);

        String tvShow = getItem(position);
        TextView theTextView = (TextView)theView.findViewById(R.id.textView1);

        theTextView.setText(tvShow);
        return theView;
    }

}
