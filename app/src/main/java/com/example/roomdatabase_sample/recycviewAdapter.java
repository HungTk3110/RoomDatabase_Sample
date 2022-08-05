package com.example.roomdatabase_sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recycviewAdapter extends RecyclerView.Adapter<recycviewAdapter.recycviewAdapter_Holder>{

    private List<user> list ;
    private onClickItems onClickItems;

    public recycviewAdapter(recycviewAdapter.onClickItems onClickItems) {
        this.onClickItems = onClickItems;
    }

    public interface onClickItems{
        void updateUser(user user);
        void deleteUser(user user);
    }

    public void setData(List<user> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public recycviewAdapter_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new recycviewAdapter_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycviewAdapter_Holder holder, int position) {
        user user = list.get(position);
        if (user == null)
            return;

        holder.tv_name.setText(user.getName());
        holder.tv_address.setText(user.getAddress());
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItems.updateUser(user);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItems.deleteUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    public class recycviewAdapter_Holder extends RecyclerView.ViewHolder {

        private TextView tv_name , tv_address ;
        private Button btn_update , btn_delete;

        public recycviewAdapter_Holder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.textView);
            tv_address = itemView.findViewById(R.id.textView2);
            btn_update = itemView.findViewById(R.id.btn_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
