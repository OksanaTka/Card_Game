package com.example.cardgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cardgame.R;
import com.example.cardgame.objects.Record;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {
    private static final String RecordListAdapter = "RecordListAdapter";

    private Context context;
    private int resource;

    private String name;
    private int score;
    private String date;
    private Record record;
    private int lastPosition = -1;


    //Holds variables in a View
    private static class ViewHolder {
        TextView adapter_LBL_name;
        TextView adapter_LBL_score;
        TextView adapter_LBL_date;
    }

    public RecordAdapter(@NonNull Context context, int resource, @NonNull List<Record> record) {
        super(context, resource, record);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        name = getItem(position).getName();
        score = getItem(position).getScore();
        date = getItem(position).getDate();

        record = new Record(name, score, date);

        //create the view result for showing the animation
        final View result;
        //ViewHolder object
        ViewHolder holder;



        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.adapter_LBL_name = (TextView) convertView.findViewById(R.id.adapter_LBL_name);
            holder.adapter_LBL_score = (TextView) convertView.findViewById(R.id.adapter_LBL_score);
            holder.adapter_LBL_date = (TextView) convertView.findViewById(R.id.adapter_LBL_date);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        //Create a loading animation that activates when scroll up/down
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.adapter_LBL_name.setText(record.getName());
        holder.adapter_LBL_score.setText(String.valueOf(record.getScore()));
        holder.adapter_LBL_date.setText(record.getDate());

        return convertView;
    }
}
