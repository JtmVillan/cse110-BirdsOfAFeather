package com.example.team7birdsofafeather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7birdsofafeather.models.IStudent;
import com.example.team7birdsofafeather.models.db.StudentWithCourses;

import java.util.List;

public class BoFListViewAdapter extends RecyclerView.Adapter<BoFListViewAdapter.ViewHolder> {
    private final List<IStudent> students;

    public BoFListViewAdapter(List<IStudent> students) {
        super();
        this.students = students;
    }

    public List<IStudent> getStudents() {
        return students;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.bof_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoFListViewAdapter.ViewHolder holder, int position) {
        holder.setStudent(students.get(position));
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    /*public void addStudent(Student s, List<String> courses) {
        IStudent ns = new StudentWithCourses(s, courses);
        this.students.add(ns);
        this.notifyItemInserted(this.students.size() - 1);
    } */


    public void addStudent(List<StudentWithCourses> newStudents) {

        int studentsSize = this.students.size();
        students.clear();
        for (int i = 0; i < studentsSize; i++) {
            this.notifyItemRemoved(0);
        }

        for (int j = 0; j < newStudents.size(); j++) {
            this.students.add(newStudents.get(j));
            this.notifyItemInserted(this.students.size() - 1);
        }
    }

    /*public void setList(List<? extends IStudent> students){
        this.students = (List<IStudent>) students;

    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvStudentRowName;
        private final TextView tvStudentNumMatched;

        private IStudent student;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvStudentRowName = itemView.findViewById(R.id.student_row_text);
            this.tvStudentNumMatched = itemView.findViewById(R.id.student_row_matched);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Context context = view.getContext();
            Intent intent = new Intent(context, BoFDetailActivity.class);
            intent.putExtra("student_id", this.student.getId());

            //intent.putExtra("student_name", this.student.getName());
            //intent.putExtra("student_courses", this.student.getCourses().toArray(new String[0]));
            context.startActivity(intent);
        }

        public void setStudent(IStudent student) {
            this.student = student;
            this.tvStudentRowName.setText(student.getName());

            this.tvStudentNumMatched.setText(Integer.toString(student.getNumMatchedCourses()));
        }
    }
}
