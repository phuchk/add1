package com.example.roomdb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    List<User> mlist;

    public interface IonClick {
        void updateUser(User u);
        void deleteUser(User u);
    }

    private IonClick ionClick;

    public Adapter(IonClick ionClick) {
        this.ionClick = ionClick;
        this.mlist = mlist;
    }

    public void setData(List<User> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvUser.setText(mlist.get(position).getName());
        holder.tvPass.setText(mlist.get(position).getPass());
        holder.tvYear.setText(mlist.get(position).getYear());
        holder.btn.setOnClickListener(view -> {
            ionClick.updateUser(mlist.get(position));
        });
        holder.btnDelete.setOnClickListener(view -> {
            ionClick.deleteUser(mlist.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUser, tvPass,tvYear;
        Button btn, btnDelete;

        public MyViewHolder(@NonNull View View) {
            super(View);
            tvUser = View.findViewById(R.id.item_name);
            tvPass = View.findViewById(R.id.item_pass);
            tvYear = View.findViewById(R.id.item_year);
            btn = View.findViewById(R.id.btnUpdate);
            btnDelete = View.findViewById(R.id.btnDelete);
        }
    }
}
