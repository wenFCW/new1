package com.example.new1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<RecordBean> data;
    private Listener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecord;
        TextView tvTime;

        public ViewHolder(View view) {
            super(view);
            tvRecord = view.findViewById(R.id.item_content);
            tvTime = view.findViewById(R.id.item_time);
        }

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public NoteAdapter(List<RecordBean> data) {
        this.data = data;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final RecordBean item = data.get(position);
        //赋值给条目控件
        holder.tvRecord.setText(item.getRecord());
        holder.tvTime.setText(item.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onClick(item);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (listener != null) {
                    listener.onLongClick(item);
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface Listener {

        void onClick(RecordBean contract);

        void onLongClick(RecordBean contract);
    }
}