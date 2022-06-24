package com.abl.breachthebarrier.ui.main.courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abl.breachthebarrier.R;
import com.abl.breachthebarrier.data.Course;
import com.abl.breachthebarrier.databinding.RvItemCourseBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    interface OnItemClickListener{
        void onItemClick(Course course, int position);
    }

    private List<Course> courses;
    private Context context;
    private final OnItemClickListener onItemClickListener;

    public CoursesAdapter(Context context, List<Course> courses, OnItemClickListener onItemClickListener){
        this.context = context;
        this.courses = courses;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_item_course, parent, false);
        return new CoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        Course course = courses.get(position);

        Picasso.get()
                .load(course.getImage())
                .into(holder.imageCourse);

        holder.textViewCourseName.setText(course.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(course, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class CoursesViewHolder extends RecyclerView.ViewHolder{

        public final View mView;

        ImageView imageCourse;
        TextView textViewCourseName;

        public CoursesViewHolder(View view){
            super(view);
            mView = view;

            imageCourse = mView.findViewById(R.id.image_course);
            textViewCourseName = mView.findViewById(R.id.textViewCourseName);
        }
    }
}
