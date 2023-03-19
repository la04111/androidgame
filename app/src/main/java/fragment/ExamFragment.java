package fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daa.R;

import java.util.ArrayList;

import list.CustomListExamAdapter;
import list.CustomListQuestionAdapter;
import modelz.DAO;
import modelz.Exam;
import modelz.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance(String param1, String param2) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshFragment();
        Button button = (Button) view.findViewById(R.id.fe_insertExam);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.inflater_exam, null);
                dialog.setView(dialogView);
                TextView ie_examName = (TextView) dialogView.findViewById(R.id.ie_examName);
                RadioButton ie_active = (RadioButton) dialogView.findViewById(R.id.ie_active);
                Button ie_insertOrUpdate = (Button) dialogView.findViewById(R.id.ie_insertOrUpdate);
                TextView ie_subjectID = (TextView) dialogView.findViewById(R.id.ie_subjectID);
                 AutoCompleteTextView textView = dialogView.findViewById(R.id.ie_subjectName);
                ArrayList<String> strings = new ArrayList<String>();
                Cursor cursor = new DAO(getActivity()).getAllSubject();
                while (cursor.moveToNext()) {
                      if (cursor.getInt(0) != 1) {
                          strings.add(cursor.getString(1));
                      }
                }
                 ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_combobox, strings);
                 textView.setAdapter(stringArrayAdapter);
                 textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                             ie_subjectID.setText(String.valueOf(i + 2));
                     }
                 });
                ie_insertOrUpdate.setText("Them bai hoc");
                ie_insertOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       int i = 0;
                        if (ie_active.isChecked()) {
                            i  = new DAO(getActivity()).insertExam(ie_examName.getText().toString(),1, Integer.valueOf(ie_subjectID.getText().toString()));
                        }
                        else {
                            i  = new DAO(getActivity()).insertExam(ie_examName.getText().toString(),0, Integer.valueOf(ie_subjectID.getText().toString()));
                        }
                        if (i == 1) {
                            Toast.makeText(getActivity(), "Insert successful", Toast.LENGTH_LONG).show();
                            refreshFragment();
                        }
                        else {
                            Toast.makeText(getActivity(), "Insert failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        Button button1 = (Button) view.findViewById(R.id.fe_insertQuestion);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.inflater_question, null);
                dialog.setView(dialogView);
                TextView iq_questionContent = (TextView) dialogView.findViewById(R.id.iq_questionContent);
                AutoCompleteTextView iq_type = (AutoCompleteTextView) dialogView.findViewById(R.id.iq_type);
                TextView iq_answerOne = (TextView) dialogView.findViewById(R.id.iq_answerOne);
                TextView iq_answerTwo = (TextView) dialogView.findViewById(R.id.iq_answerTwo);
                TextView iq_answerThree = (TextView) dialogView.findViewById(R.id.iq_answerThree);
                TextView iq_answerFour = (TextView) dialogView.findViewById(R.id.iq_answerFour);
                TextView fe_examID = (TextView) getActivity().findViewById(R.id.fe_examID);
                RadioButton iq_isAnswerOne = (RadioButton) dialogView.findViewById(R.id.iq_isAnswerOne);
                RadioButton iq_isAnswerTwo = (RadioButton) dialogView.findViewById(R.id.iq_isAnswerTwo);
                RadioButton iq_isAnswerThree = (RadioButton) dialogView.findViewById(R.id.iq_isAnswerThree);
                Button iq_insertOrUpdate = (Button) dialogView.findViewById(R.id.iq_insertOrUpdate);
                String[] strings =  {"1", "2", "3"};
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_combobox, strings);
                iq_type.setAdapter(stringArrayAdapter);
                iq_insertOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Question question;
                        if (iq_isAnswerOne.isChecked()) {
                           question = new Question(iq_questionContent.getText().toString(),iq_answerOne.getText().toString()
                                    ,iq_answerTwo.getText().toString(),iq_answerThree.getText().toString(),iq_answerFour.getText().toString()
                                    ,iq_answerOne.getText().toString(),Integer.valueOf(iq_type.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        }
                        else  if (iq_isAnswerTwo.isChecked()){
                            question = new Question(iq_questionContent.getText().toString(),iq_answerOne.getText().toString()
                                    ,iq_answerTwo.getText().toString(),iq_answerThree.getText().toString(),iq_answerFour.getText().toString()
                                    ,iq_answerTwo.getText().toString(),Integer.valueOf(iq_type.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        }
                        else  if (iq_isAnswerThree.isChecked()) {
                            question = new Question(iq_questionContent.getText().toString(),iq_answerOne.getText().toString()
                                    ,iq_answerTwo.getText().toString(),iq_answerThree.getText().toString(),iq_answerFour.getText().toString()
                                    ,iq_answerThree.getText().toString(),Integer.valueOf(iq_type.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        }
                        else {
                            question = new Question(iq_questionContent.getText().toString(),iq_answerOne.getText().toString()
                                    ,iq_answerTwo.getText().toString(),iq_answerThree.getText().toString(),iq_answerFour.getText().toString()
                                    ,iq_answerFour.getText().toString(),Integer.valueOf(iq_type.getText().toString()),
                                    Integer.valueOf(fe_examID.getText().toString()));
                        }
                        int  i = new DAO(getActivity()).insertQuestion(question);
                        if (i == 1) {
                            Toast.makeText(getActivity(), "Insert successful", Toast.LENGTH_LONG).show();
                            refreshQuestionList(Integer.valueOf(fe_examID.getText().toString()));
                        }
                        else {
                            Toast.makeText(getActivity(), "Insert failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    public void refreshFragment(){
        ArrayList<Exam> arrayList = new ArrayList<>();
        Cursor cursor = new DAO(getActivity()).getAllExam();
        while (cursor.moveToNext()) {
            Exam exam = new Exam(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            arrayList.add(exam);
        }
        final ListView lv = (ListView) getActivity().findViewById(R.id.fe_listView);
        lv.setAdapter(new CustomListExamAdapter(getActivity(), arrayList));
    }

    public void refreshQuestionList(int examID){
        ArrayList<Question> arrayList = new ArrayList<>();
        Cursor cursor = new DAO(getActivity()).getAllQuestionByExamID(examID);
        while (cursor.moveToNext()) {
            Question question = new Question(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7), cursor.getInt(8));
            arrayList.add(question);
        }
        final ListView lv = (ListView) getActivity().findViewById(R.id.fe_listView);
        lv.setAdapter(new CustomListQuestionAdapter(getActivity(), arrayList));
    }
}