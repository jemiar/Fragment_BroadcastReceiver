package com.project.hoangminh.app3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/*---------------------------------------------
Project 3 - App 3
TextAdapter class

To display name and address
----------------------------------------------*/

public class TextAdapter extends BaseAdapter {

    private ArrayList<String> name;
    private ArrayList<String> address;
    private Context context;

    //constructor
    public TextAdapter(Context c, ArrayList<String> n, ArrayList<String> addr){
        context = c;
        name = n;
        address = addr;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //inflate and return view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        TextView nameText = (TextView) convertView.findViewById(R.id.name);
        nameText.setText(name.get(position));

        TextView addText = (TextView) convertView.findViewById(R.id.address);
        addText.setText(address.get(position));

        return convertView;
    }
}
