package com.kiran.jvapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<UserDataModel> objects;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView student_name, roll_no;

        public ViewHolder(View itemView) {
            super(itemView);
            student_name = (TextView) itemView.findViewById(R.id.student_name);
            roll_no = (TextView) itemView.findViewById(R.id.roll_no);
        }
    }

    public ListAdapter(Context context, List<UserDataModel> objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserDataModel object = objects.get(position);

        holder.student_name.setText(object.getStudent_name());
        holder.roll_no.setText(Integer.toString(object.getRoll_no()));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


}