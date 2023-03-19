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
import android.widget.CheckBox;
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

public class CustomListQuestionAdapter  extends BaseAdapter {
    private ArrayList<Question> listData;
    private LayoutInflater layoutInflater;
    Context context;

    public CustomListQuestionAdapter(Context aContext, ArrayList<Question> listData) {
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
        CustomListQuestionAdapter.ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_item_question, null);
            holder = new CustomListQuestionAdapter.ViewHolder();
            holder.uContent = (TextView) v.findViewById(R.id.liq_questionContent);
            holder.uType = (TextView) v.findViewById(R.id.liq_questionType);
            holder.uName = (TextView) v.findViewById(R.id.liq_examName);
            holder.button = (Button) v.findViewById(R.id.liq_updateQuestion);
            v.setTag(holder);
        } else {
            holder = (CustomListQuestionAdapter.ViewHolder) v.getTag();
        }
        holder.uContent.setText(listData.get(position).getQuestionContent());
        holder.uType.setText(String.valueOf(listData.get(position).getType()));
        Cursor cursor = new DAO(context).findExam(listData.get(position).getExamID());
        while (cursor.moveToNext()) {
            holder.uName.setText(cursor.getString(1));
        }
        holder.button.setContentDescription(String.valueOf(listData.get(position).getQuestionID()));
       holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(true);
                LayoutInflater inflater = layoutInflater;
                View dialogView = inflater.inflate(R.layout.inflater_question, null);
                dialog.setView(dialogView);
                TextView iq_questionID = (TextView) dialogView.findViewById(R.id.iq_questionID);
                TextView iq_questionContent = (TextView) dialogView.findViewById(R.id.iq_questionContent);
                AutoCompleteTextView iq_type = (AutoCompleteTextView) dialogView.findViewById(R.id.iq_type);
                TextView iq_answerOne = (TextView) dialogView.findViewById(R.id.iq_answerOne);
                TextView iq_answerTwo = (TextView) dialogView.findViewById(R.id.iq_answerTwo);
                TextView iq_answerThree = (TextView) dialogView.findViewById(R.id.iq_answerThree);
                TextView iq_answerFour = (TextView) dialogView.findViewById(R.id.iq_answerFour);
                TextView iq_type1 = (TextView) dialogView.findViewById(R.id.iq_type1);
                RadioButton iq_isAnswerOne = (RadioButton) dialogView.findViewById(R.id.iq_isAnswerOne);
                RadioButton iq_isAnswerTwo = (RadioButton) dialogView.findViewById(R.id.iq_isAnswerTwo);
                RadioButton iq_isAnswerThree = (RadioButton) dialogView.findViewById(R.id.iq_isAnswerThree);
                RadioButton iq_isAnswerFour = (RadioButton) dialogView.findViewById(R.id.iq_isAnswerFour);
                Activity activity = (Activity) context;
                TextView fe_examID = (TextView)activity.findViewById(R.id.fe_examID);
               Cursor cursor1 = new DAO(context).findQuestion(Integer.parseInt(holder.button.getContentDescription().toString()));
                while (cursor1.moveToNext()) {
                    iq_questionID.setText(String.valueOf(cursor1.getInt(0)));
                    iq_questionContent.setText(String.valueOf(cursor1.getString(1)));
                    iq_answerOne.setText(String.valueOf(cursor1.getString(2)));
                    iq_answerTwo.setText(String.valueOf(cursor1.getString(3)));
                    iq_answerThree.setText(String.valueOf(cursor1.getString(4)));
                    iq_answerFour.setText(String.valueOf(cursor1.getString(5)));
                    if (cursor1.getString(6).equals(cursor1.getString(2))) {
                        iq_isAnswerOne.setChecked(true);
                    }
                    else  if (cursor1.getString(6).equals(cursor1.getString(3))) {
                        iq_isAnswerTwo.setChecked(true);
                    }
                    else  if (cursor1.getString(6).equals(cursor1.getString(4))) {
                        iq_isAnswerThree.setChecked(true);
                    }
                    else {
                        iq_isAnswerFour.setChecked(true);
                    }
                    iq_type1.setText(String.valueOf(cursor1.getInt(7)));
                }
                String[] strings =  {"1", "2","3"};
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.custom_combobox, strings);
                iq_type.setAdapter(stringArrayAdapter);
                iq_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        iq_type1.setText(String.valueOf(position + 2));
                    }
                });
               Button iq_insertOrUpdate = (Button) dialogView.findViewById(R.id.iq_insertOrUpdate);
                iq_insertOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Question question;
                        if (iq_isAnswerOne.isChecked()) {
                            question = new Question(Integer.parseInt(iq_questionID.getText().toString()),iq_questionContent.getText().toString(), iq_answerOne.getText().toString()
                                    , iq_answerTwo.getText().toString(), iq_answerThree.getText().toString(), iq_answerFour.getText().toString()
                                    , iq_answerOne.getText().toString(), Integer.valueOf(iq_type1.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        } else if (iq_isAnswerOne.isChecked()) {
                            question = new Question(Integer.parseInt(iq_questionID.getText().toString()),iq_questionContent.getText().toString(), iq_answerOne.getText().toString()
                                    , iq_answerTwo.getText().toString(), iq_answerThree.getText().toString(), iq_answerFour.getText().toString()
                                    , iq_answerTwo.getText().toString(), Integer.valueOf(iq_type1.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        } else if (iq_isAnswerOne.isChecked()) {
                            question = new Question(Integer.parseInt(iq_questionID.getText().toString()),iq_questionContent.getText().toString(), iq_answerOne.getText().toString()
                                    , iq_answerTwo.getText().toString(), iq_answerThree.getText().toString(), iq_answerFour.getText().toString()
                                    , iq_answerThree.getText().toString(), Integer.valueOf(iq_type1.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        } else {
                            question = new Question(Integer.parseInt(iq_questionID.getText().toString()),iq_questionContent.getText().toString(), iq_answerOne.getText().toString()
                                    , iq_answerTwo.getText().toString(), iq_answerThree.getText().toString(), iq_answerFour.getText().toString()
                                    , iq_answerFour.getText().toString(), Integer.valueOf(iq_type1.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        }
                        int i = new DAO(context).updateQuestion(question);
                        if (i == 1) {
                            Toast.makeText(context, "Insert successful", Toast.LENGTH_LONG).show();
                            refreshQuestionList(Integer.valueOf(fe_examID.getText().toString()));
                        } else {
                            Toast.makeText(context, "Insert failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        return v;
    }

    public void refreshQuestionList(int examID){
        ArrayList<Question> arrayList = new ArrayList<>();
        Activity activity = (Activity) context;
        Cursor cursor = new DAO(context).getAllQuestionByExamID(examID);
        while (cursor.moveToNext()) {
            Question question = new Question(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7), cursor.getInt(8));
            arrayList.add(question);
        }
        final ListView lv = (ListView) activity.findViewById(R.id.fe_listView);
        lv.setAdapter(new CustomListQuestionAdapter(activity, arrayList));
    }

    static class ViewHolder {
        TextView uContent;
        TextView uType;
        TextView uName;
        Button button;
    }

}