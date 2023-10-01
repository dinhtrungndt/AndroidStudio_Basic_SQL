package com.example.asm_ht_nguyendinhtrung_pk02294.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asm_ht_nguyendinhtrung_pk02294.R;
import com.example.asm_ht_nguyendinhtrung_pk02294.activities.ClazzActivity;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Clazz;

import java.util.ArrayList;

public class ClazzAdapter  extends BaseAdapter {
    private ArrayList<Clazz> list;
    public ClazzAdapter(ArrayList<Clazz> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (view==null){
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_clazz_detail_item,null);
            TextView tvClazzName  = view.findViewById(R.id.tvClazzName);
            TextView tvClazzStudent = view.findViewById(R.id.tvClazzStudent);
            ImageButton imButtonEdit = view.findViewById(R.id.imButtonEdit) ;
            ImageButton imButtonTrash = view.findViewById(R.id.imButtonTrash) ;

            ClazzAdapter.ViewHolder holder = new ClazzAdapter.ViewHolder(tvClazzName, tvClazzStudent, imButtonEdit, imButtonTrash);
            view.setTag(holder);
        }
        Clazz clazz = (Clazz) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tvClazzStudent.setText(clazz.getId().toString());
        holder.tvClazzName.setText(clazz.getName());
        holder.imButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClazzActivity activity = (ClazzActivity) _viewGroup.getContext();
                activity.onEditItemClick(clazz);
            }
        });
        holder.imButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClazzActivity activity = (ClazzActivity) _viewGroup.getContext();
                activity.onDeleteItemClick(clazz);
            }
        });
        return view;
    }

    /**
     * Lấy danh sách bên TextView
     * static class : có thì dùng liền khi chưa có thì tạo mới tránh tình trạng lag
     */
    public static class ViewHolder{
        final TextView tvClazzName, tvClazzStudent;
        final ImageButton imButtonEdit, imButtonTrash;

        public ViewHolder(TextView tvClazzName, TextView tvClazzStudent, ImageButton imButtonEdit, ImageButton imButtonTrash) {
            this.tvClazzName = tvClazzName;
            this.tvClazzStudent = tvClazzStudent;
            this.imButtonEdit = imButtonEdit;
            this.imButtonTrash = imButtonTrash;

        }
    }
}
