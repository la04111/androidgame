package list;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.daa.R;

import java.util.ArrayList;

import modelz.DAO;
import modelz.Exam;
import modelz.Question;

public class CustomListExamAdapter extends BaseAdapter {
    private ArrayList<Exam> listData;
    private LayoutInflater layoutInflater;
    Context context;

    public CustomListExamAdapter(Context aContext, ArrayList<Exam> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        this.context = aContext;
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_item_exam, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.lie_examName);
            holder.uState = (TextView) v.findViewById(R.id.lie_state);
            holder.uName2 = (TextView) v.findViewById(R.id.lie_subjectName);
            holder.button = (Button) v.findViewById(R.id.lie_updateExam);
            holder.button1 = (Button) v.findViewById(R.id.lie_viewQuestion);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getExamName());
        if (listData.get(position).getExamState() == 1) {
            holder.uState.setText("Dang");
        }
        else {
            holder.uState.setText("Ngung");
        }
        Cursor cursor = new DAO(context).findSubject(listData.get(position).getSubjectID());
        while (cursor.moveToNext()) {
            holder.uName2.setText(cursor.getString(1));
        }
        holder.button1.setContentDescription(String.valueOf(listData.get(position).getExamID()));
        if (listData.get(position).getExamID() == 1) {
            holder.button.setVisibility(View.INVISIBLE);
            holder.button1.setVisibility(View.INVISIBLE);
        }
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Activity activity = (Activity)context;
                 TextView textView = (TextView)activity.findViewById(R.id.fe_examID);
                 textView.setText(String.valueOf(listData.get(position).getExamID()));
                 refreshQuestionList(Integer.parseInt(textView.getText().toString()));
            }
        });
        holder.button.setContentDescription(String.valueOf(listData.get(position).getExamID()));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(true);
                LayoutInflater inflater = layoutInflater;
                View dialogView = inflater.inflate(R.layout.inflater_exam, null);
                dialog.setView(dialogView);
                TextView ie_examID = (TextView) dialogView.findViewById(R.id.ie_examID);
                TextView ie_examName = (TextView) dialogView.findViewById(R.id.ie_examName);
                RadioButton ie_active = (RadioButton) dialogView.findViewById(R.id.ie_active);
                RadioButton ie_noActive = (RadioButton) dialogView.findViewById(R.id.ie_noActive);
                TextView ie_subjectID = (TextView) dialogView.findViewById(R.id.ie_subjectID);
                AutoCompleteTextView textView = dialogView.findViewById(R.id.ie_subjectName);
                ArrayList<String> strings = new ArrayList<String>();
                DAO dao = new DAO(context);
                Cursor cursor = dao.getAllSubject();
                while (cursor.moveToNext()) {
                    if (cursor.getInt(0) != 1) {
                        strings.add(cursor.getString(1));
                    }
                }
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.custom_combobox, strings);
                textView.setAdapter(stringArrayAdapter);
                textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ie_subjectID.setText(String.valueOf(i + 2));
                    }
                });
                Cursor cursor1 = dao.findExam(Integer.parseInt(String.valueOf(holder.button.getContentDescription())));
                while (cursor1.moveToNext()) {
                    ie_examName.setText(cursor1.getString(1));
                    ie_examID.setText(String.valueOf(cursor1.getInt(0)));
                    if (cursor1.getInt(2) == 1) {
                        ie_active.setChecked(true);
                    }
                    else {
                        ie_noActive.setChecked(true);
                    }
                    ie_subjectID.setText(String.valueOf(cursor1.getInt(3)));
                }
                Button ie_insertOrUpdate = (Button) dialogView.findViewById(R.id.ie_insertOrUpdate);
                ie_insertOrUpdate.setText("Cap Nhat");
                ie_insertOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = 0;
                        if (ie_active.isChecked()) {
                           i =  new DAO(context).updateExam(Integer.parseInt(ie_examID.getText().toString()),ie_examName.getText().toString(), 1, Integer.parseInt(ie_subjectID.getText().toString()));
                        }
                        else {
                            i =  new DAO(context).updateExam(Integer.parseInt(ie_examID.getText().toString()),ie_examName.getText().toString(), 0, Integer.parseInt(ie_subjectID.getText().toString()));
                        }
                        if (i == 1) {
                            Toast.makeText(context, "Update successful", Toast.LENGTH_LONG).show();
                            refreshFragment();
                        }
                        else {
                            Toast.makeText(context, "Update failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        return v;
    }

    public void refreshFragment(){
        ArrayList<Exam> arrayList = new ArrayList<>();
        Cursor cursor = new DAO(context).getAllExam();
        while (cursor.moveToNext()) {
            Exam exam = new Exam(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            arrayList.add(exam);
        }
        Activity activity = (Activity) context;
        final ListView lv = (ListView) activity.findViewById(R.id.fe_listView);
        lv.setAdapter(new CustomListExamAdapter(activity, arrayList));
        Button button = (Button) activity.findViewById(R.id.fe_goBack);
        button.setVisibility(View.GONE);
        Button button1 = (Button) activity.findViewById(R.id.fe_insertExam);
        button1.setVisibility(View.VISIBLE);
        Button button2 = (Button) activity.findViewById(R.id.fe_insertQuestion);
        button2.setVisibility(View.GONE);
    }

    public void refreshQuestionList(int examID){
        ArrayList<Question> arrayList = new ArrayList<>();
        Cursor cursor = new DAO(context).getAllQuestionByExamID(examID);
        while (cursor.moveToNext()) {
            Question question = new Question(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7), cursor.getInt(8));
            arrayList.add(question);
        }
        Activity activity = (Activity) context;
        final ListView lv = (ListView) activity.findViewById(R.id.fe_listView);
        lv.setAdapter(new CustomListQuestionAdapter(activity, arrayList));
        NavController navController = Navigation.findNavController(activity, R.id.am_navHostFragment);
        TextView textView = (TextView) activity.findViewById(R.id.am_textTitle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                Cursor cursor1 = new DAO(context).findExam(examID);
                while (cursor1.moveToNext()) {
                    textView.setText("Cac cau hoi cua "+cursor1.getString(1));
                }
            }
        });
        Button button = (Button) activity.findViewById(R.id.fe_goBack);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshFragment();
                textView.setText("Exam");
            }
        });
        Button button1 = (Button) activity.findViewById(R.id.fe_insertExam);
        button1.setVisibility(View.GONE);
        Button button2 = (Button) activity.findViewById(R.id.fe_insertQuestion);
        button2.setVisibility(View.VISIBLE);
    }

    static class ViewHolder {
        TextView uName;
        TextView uState;
        TextView uName2;
        Button button;
        Button button1;
    }

}
