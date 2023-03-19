package fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daa.R;

import org.w3c.dom.Text;

import java.util.Random;

import activity.MainActivity;
import modelz.DAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
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
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    int count = 0;
    Cursor cursor1;
    Cursor cursor;
    RelativeLayout relativeLayout;
    ProgressBar fg_progTimer;
    Button fg_btnStart;
    Button fg_retry;
    TextView fg_score;
    TextView fg_bottomMessage;
    TextView fg_gameOver;
    int score = 0;
    String m_Text = "";
    int examID = 1;
    MainActivity activity;
    String dragText;
    int dragID;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.fg_mainLayout);
        fg_progTimer = (ProgressBar) getActivity().findViewById(R.id.fg_progTimer);
        fg_btnStart = (Button) getActivity().findViewById(R.id.fg_btnStart);
        fg_retry = (Button) getActivity().findViewById(R.id.fg_retry);
        fg_score = (TextView) getActivity().findViewById(R.id.fg_score);
        fg_bottomMessage = (TextView) getActivity().findViewById(R.id.fg_bottomMessage);
        fg_gameOver = (TextView) getActivity().findViewById(R.id.fg_gameOver);
        fg_progTimer.setVisibility(View.INVISIBLE);
        fg_score.setVisibility(View.INVISIBLE);
        fg_gameOver.setVisibility(View.INVISIBLE);
        fg_retry.setVisibility(View.INVISIBLE);
        activity = (MainActivity)getActivity();
        m_Text = activity.getUserzName();
        examID = getArguments().getInt("examID");
        cursor = new DAO(getActivity()).findExam(examID);
        if (cursor.moveToFirst()) {
            cursor1 = new DAO(getActivity()).getAllQuestionByExamID(cursor.getInt(0));
            fg_progTimer.setMax(cursor1.getCount());
            cursor1.moveToNext();
            fg_btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fg_btnStart.setVisibility(View.INVISIBLE);
                    fg_progTimer.setVisibility(View.VISIBLE);
                    fg_score.setVisibility(View.VISIBLE);
                    fg_progTimer.setProgress(0);
                    fg_score.setText("Diem so: 0");
                    fg_bottomMessage.setText("Câu đúng: "+0 + " - Tổng:" + cursor1.getCount());
                    mathGame1();
                }
            });
            fg_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fg_gameOver.setVisibility(View.INVISIBLE);
                    fg_btnStart.setVisibility(View.VISIBLE);
                    fg_progTimer.setProgress(0);
                    fg_score.setText("Diem so: 0");
                    fg_bottomMessage.setText("Bấm nút \"Bắt đầu\" để chơi");
                    fg_retry.setVisibility(View.INVISIBLE);
                    cursor1.moveToFirst();
                    score = 0;
                }
            });
        }
    }

    @SuppressLint("ResourceType")
    private void mathGame1() {
        if (cursor1.getInt(7) == 1 && cursor.getInt(3) == 2) {
            if (cursor1.getString(1) != null) {
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setId(1);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(480, 500, 0, 0);
                linearLayout.setLayoutParams(lp);
                String[] separated = cursor1.getString(1).split(" ");
                Random random = new Random();
                int b = random.nextInt(separated.length);
                for (int j = 0; j < separated.length; j++) {

                    if (j != b) {
                        TextView textView = new TextView(getActivity());
                        textView.setText(separated[j] + " ");
                        textView.setTextSize(25);
                        linearLayout.addView(textView);
                    }
                    else {
                        EditText editText = new EditText(getActivity());
                        editText.setTextSize(25);
                        editText.setBackgroundColor(Color.GREEN);
                        editText.setEms(separated[j].length());
                        linearLayout.addView(editText);
                    }
                }
                Button button = new Button(getActivity());
                button.setText("Next");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String test = "";
                        for (int i = 0; i < linearLayout.getChildCount() - 1; i++) {
                            if (i == b) {
                                EditText editText = (EditText) linearLayout.getChildAt(i);
                                test += editText.getText().toString();
                                test += " ";
                            }
                            else {
                                TextView textView = (TextView) linearLayout.getChildAt(i);
                                test += textView.getText().toString();
                            }
                        }
                        if (cursor1.getString(1).equals(test.trim())) {
                            score++;
                            fg_score.setText("Diem so: "+ score);
                        }
                        fg_progTimer.setProgress(fg_progTimer.getProgress() + 1);
                        fg_bottomMessage.setText("Câu đúng: "+score + " - Tổng:" + cursor1.getCount());
                        if (cursor1.moveToNext()) {
                            relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
                            mathGame1();
                        }
                        else {
                            relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
                            fg_progTimer.setVisibility(View.INVISIBLE);
                            fg_score.setVisibility(View.INVISIBLE);
                            fg_retry.setVisibility(View.VISIBLE);
                            fg_score.setText("Diem so: 0");
                            fg_gameOver.setVisibility(View.VISIBLE);
                            if (m_Text.matches("")) {
                                m_Text = "Người chơi";
                            }
                            fg_gameOver.setText("Kết thúc\n\n" + m_Text + "\nĐiểm của bạn\n" + score);
                            Cursor cursor2 = new DAO(getActivity()).findScoreBoard2(Integer.parseInt(activity.getUserzID()), examID);
                            if (cursor2.getCount() == 0) {
                                int i = new DAO(getActivity()).insertScoreBoard(Integer.parseInt(activity.getUserzID()),examID, score);
                            }
                            else {
                                int i = new DAO(getActivity()).updateScoreBoard(Integer.parseInt(activity.getUserzID()),examID, score);
                            }
                        }
                    }
                });
                linearLayout.addView(button);
                relativeLayout.addView(linearLayout);
            }
        }
        else if (cursor1.getInt(7) == 2 && cursor.getInt(3) == 3) {
            if (cursor1.getString(1) != null) {
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setId(1);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(480, 500, 0, 0);
                linearLayout.setLayoutParams(lp);
                Random random = new Random();
                StringBuffer a = new StringBuffer();
                for (int j = 0; j < cursor1.getString(1).length(); j++) {
                    int b = random.nextInt(cursor1.getString(1).length());
                    if (a.indexOf(String.valueOf(b)) == -1) {
                        a.append(b);
                        TextView textView1 = new TextView(getActivity());
                        textView1.setId(j + 2);
                        textView1.setText(cursor1.getString(1).charAt(b) + " ");
                        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
                        textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        textView1.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                dragText = (String) textView1.getText();
                                dragID = (int) textView1.getId();
                                ClipData clipData = ClipData.newPlainText("","");
                                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(textView1);
                                view.startDrag(clipData,dragShadowBuilder, null, 0);
                                return false;
                            }
                        });
                        textView1.setOnDragListener(new View.OnDragListener() {
                            @Override
                            public boolean onDrag(View view, DragEvent dragEvent) {
                                final int action = dragEvent.getAction();
                                switch (action) {
                                    case DragEvent.ACTION_DRAG_STARTED:
                                        break;
                                    case DragEvent.ACTION_DRAG_ENTERED:
                                        break;
                                    case DragEvent.ACTION_DROP: {
                                        TextView view1 = (TextView)  getActivity().findViewById(dragID);
                                        view1.setText(textView1.getText());
                                        textView1.setText(dragText);
                                        return true;
                                    }
                                    case DragEvent.ACTION_DRAG_ENDED:
                                        break;
                                    case DragEvent.ACTION_DRAG_EXITED:
                                        break;
                                    default:
                                        break;
                                }
                                return true;
                            }
                        });
                        linearLayout.addView(textView1);
                    }
                    else {
                        j--;
                    }
                }
                Button button = new Button(getActivity());
                button.setText("Next");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String test = "";
                        for (int i = 0; i < linearLayout.getChildCount() - 1; i++) {
                                TextView textView = (TextView) linearLayout.getChildAt(i);
                                test += textView.getText().toString().trim();
                        }
                        if (cursor1.getString(2).equals(test) || cursor1.getString(3).equals(test)
                                || cursor1.getString(4).equals(test) || cursor1.getString(5).equals(test)) {
                            score++;
                            fg_score.setText("Diem so: "+ score);
                        }

                        fg_progTimer.setProgress(fg_progTimer.getProgress() + 1);
                        fg_bottomMessage.setText("Câu đúng: "+score + " - Tổng:" + cursor1.getCount());
                        if (cursor1.moveToNext()) {
                            relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
                            mathGame1();
                        }
                        else {
                            relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
                            fg_progTimer.setVisibility(View.INVISIBLE);
                            fg_score.setVisibility(View.INVISIBLE);
                            fg_retry.setVisibility(View.VISIBLE);
                            fg_score.setText("Diem so: 0");
                            fg_gameOver.setVisibility(View.VISIBLE);
                            if (m_Text.matches("")) {
                                m_Text = "Người chơi";
                            }
                            fg_gameOver.setText("Kết thúc\n\n" + m_Text + "\nĐiểm của bạn\n" + score);
                            Cursor cursor2 = new DAO(getActivity()).findScoreBoard2(Integer.parseInt(activity.getUserzID()), examID);
                            if (cursor2.getCount() == 0) {
                                int i = new DAO(getActivity()).insertScoreBoard(Integer.parseInt(activity.getUserzID()),examID, score);
                            }
                            else {
                                int i = new DAO(getActivity()).updateScoreBoard(Integer.parseInt(activity.getUserzID()),examID, score);
                            }
                        }
                    }
                });
                linearLayout.addView(button);
                relativeLayout.addView(linearLayout);
            }
        }
        else {
            if (cursor1.getString(1) != null) {
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setId(1);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(480, 500, 0, 0);
                linearLayout.setLayoutParams(lp);
                TextView textView = new TextView(getActivity());
                textView.setText(cursor1.getString(1));
                linearLayout.addView(textView);
                for (int j = 2; j < 6; j ++) {
                    Button button = new Button(getActivity());
                    button.setText(cursor1.getString(j));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (cursor1.getString(6).equals(button.getText().toString().trim())) {
                                score++;
                                fg_score.setText("Diem so: "+ score);
                            }
                            fg_progTimer.setProgress(fg_progTimer.getProgress() + 1);
                            fg_bottomMessage.setText("Câu đúng: "+score + " - Tổng:" + cursor1.getCount());
                            if (cursor1.moveToNext()) {
                                relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
                                mathGame1();
                            }
                            else {
                                relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
                                fg_progTimer.setVisibility(View.INVISIBLE);
                                fg_score.setVisibility(View.INVISIBLE);
                                fg_retry.setVisibility(View.VISIBLE);
                                fg_score.setText("Diem so: 0");
                                fg_gameOver.setVisibility(View.VISIBLE);
                                if (m_Text.matches("")) {
                                    m_Text = "Người chơi";
                                }
                                fg_gameOver.setText("Kết thúc\n\n" + m_Text + "\nĐiểm của bạn\n" + score);
                                Cursor cursor3 = new DAO(getActivity()).findScoreBoard2(Integer.parseInt(activity.getUserzID()), examID);
                                if (cursor3.getCount() == 0) {
                                    int i = new DAO(getActivity()).insertScoreBoard(Integer.parseInt(activity.getUserzID()),examID, score);
                                }
                                else {
                                    int i = new DAO(getActivity()).updateScoreBoard(Integer.parseInt(activity.getUserzID()),examID, score);
                                }
                            }
                        }
                    });
                    linearLayout.addView(button);
                }
                relativeLayout.addView(linearLayout);
            }
        }
    }
}