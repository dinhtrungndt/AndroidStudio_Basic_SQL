package com.example.asm_ht_nguyendinhtrung_pk02294.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asm_ht_nguyendinhtrung_pk02294.R;
import com.example.asm_ht_nguyendinhtrung_pk02294.activities.StudentActivity;
import com.example.asm_ht_nguyendinhtrung_pk02294.home_list.Student;

import java.util.ArrayList;

/**
 * Đưa dữ liệu vào trong layout item
 */

public class StudentAdapter extends BaseAdapter {
    private ArrayList<Student> list;
    public StudentAdapter(ArrayList<Student> list){
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
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_student_detail_item,null);
            TextView tvStudentName  = view.findViewById(R.id.tvStudentName);
            TextView tvStudentClazz = view.findViewById(R.id.tvStudentClazz);
            ImageButton imButtonEdit = view.findViewById(R.id.imButtonEdit) ;
            ImageButton imButtonTrash = view.findViewById(R.id.imButtonTrash) ;
            ViewHolder holder = new ViewHolder(tvStudentName, tvStudentClazz, imButtonEdit, imButtonTrash);
            view.setTag(holder);
        }
        Student student = (Student) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tvStudentClazz.setText(student.getClazz_id().toString());
        holder.tvStudentName.setText(student.getName());
        holder.imButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentActivity activity = (StudentActivity) _viewGroup.getContext();
                activity.onEditClick(student);
            }
        });
        holder.imButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentActivity activity = (StudentActivity) _viewGroup.getContext();
                activity.onDeleteClick(student);
            }
        });
        return view;
    }

    /**
     * Lấy danh sách bên TextView
     * static class : có thì dùng liền khi chưa có thì tạo mới tránh tình trạng lag
     */
    public static class ViewHolder{
        final TextView tvStudentName, tvStudentClazz;
        final ImageButton imButtonEdit, imButtonTrash;

        public ViewHolder(TextView tvStudentName, TextView tvStudentClazz, ImageButton imButtonEdit, ImageButton imButtonTrash) {
            this.tvStudentName = tvStudentName;
            this.tvStudentClazz = tvStudentClazz;
            this.imButtonEdit = imButtonEdit;
            this.imButtonTrash = imButtonTrash;


        }
    }
}

