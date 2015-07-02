package com.example.ivelinrusev.jsonhttpget.adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ivelinrusev.jsonhttpget.R;

import java.util.ArrayList;
import java.util.HashMap;


public class CustomJokeAdapter extends ArrayAdapter<String>{

    private HashMap<Integer, Boolean> selectedItems;
    private final Context context;

    public CustomJokeAdapter(Context context, int layout, ArrayList<String> list, HashMap<Integer, Boolean> selectedItems){
        super(context, layout, list);
        this.selectedItems = selectedItems;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        String currentJoke = getItem(position);
        TextView theTextView = (TextView)rowView.findViewById(R.id.textView1);
        theTextView.setText(currentJoke);


        if(selectedItems.size() > 0 && selectedItems.containsKey(position)){
            if(selectedItems.get(position)){
                rowView.setBackgroundColor(Color.GRAY);
            }else{
                rowView.setBackgroundColor(Color.TRANSPARENT);
            }
        }


        return  rowView;
    }



}
