package com.example.team7birdsofafeather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7birdsofafeather.models.db.Course;

import java.util.List;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.ViewHolder> {
    private final List<Course> courses;

    public CoursesViewAdapter(List<Course> courses) {
        super();
        this.courses = courses;
    }

    @NonNull
    @Override
    public CoursesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.courses_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewAdapter.ViewHolder holder, int position) {
        holder.setCourse(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return this.courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseTextView;
        private Course course;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.courseTextView = itemView.findViewById(R.id.courses_row_text);
        }

        public void setCourse(Course course) {
            try {
                this.course = course;
                this.courseTextView.setText(course.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        this.notifyItemInserted(this.courses.size() - 1);
    }
}
