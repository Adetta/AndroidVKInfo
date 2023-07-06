package com.example.vkinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.InfoViewHolder>{
    private static int viewHolderCount;
    private List<PersonInfo> personInfo;

    public UserAdapter(List<PersonInfo> personInfo){
        viewHolderCount = 0;
        this.personInfo = personInfo;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.user_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        InfoViewHolder viewHolder = new InfoViewHolder(view);
        //viewHolder.viewHolderIndex.setText(viewHolderCount);
        viewHolderCount++;
        return viewHolder;
    }

    //устанавливаем данные для этой View (переиспользуем View, обновляем знач в item)
    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        PersonInfo info = personInfo.get(viewHolderCount-1);

        holder.setText("name: " + info.name,
                "lastname: " + info.lastname);
    }

    @Override
    public int getItemCount() {
        return personInfo.size();
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView nameItem;
        TextView lastnameItem;

        public InfoViewHolder(View itemView) {
            super(itemView);

            //дорогая операция. Будем делать только 14 раз здесь
            nameItem = itemView.findViewById(R.id.tv_name);
            lastnameItem = itemView.findViewById(R.id.tv_view_holder_user);
        }

        //переиспользуем item
        void  setText(String str1, String str2){
            nameItem.setText(String.valueOf(str1));
            lastnameItem.setText(String.valueOf(str2));
        }
    }
}
