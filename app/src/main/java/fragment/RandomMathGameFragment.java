package fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.daa.Game;
import com.example.daa.R;

import activity.AdminActivity;
import activity.MainActivity;
import modelz.DAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RandomMathGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomMathGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RandomMathGameFragment() {
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
    public static RandomMathGameFragment newInstance(String param1, String param2) {
        RandomMathGameFragment fragment = new RandomMathGameFragment();
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
        return inflater.inflate(R.layout.fragment_random_math_game, container, false);
    }

    Button fg_btnStart, fg_answerOne, fg_answerTwo, fg_answerThree, fg_answerFour, fg_retry;
    TextView fg_score, fg_questions, fg_timer, fg_bottomMessage, fg_gameOver;
    ProgressBar fg_progTimer;
    ConstraintLayout layout;
    String m_Text = "";
    Game g = new Game();
    int secondsRemaining = 20;

    final CountDownTimer timer = new CountDownTimer(20000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining -= 1;
            fg_timer.setText(Integer.toString(secondsRemaining));
            fg_progTimer.setProgress(fg_progTimer.getProgress() + 3);
        }

        @Override
        public void onFinish() {
            viewInVisiblity();
            fg_retry.setVisibility(View.VISIBLE);
            fg_gameOver.setVisibility(View.VISIBLE);
            if (m_Text.matches("")) {
                m_Text = "Người chơi";
            }
            fg_gameOver.setText("Kết thúc\n\n" + m_Text + "\nĐiểm của bạn\n" + fg_score.getText());
            fg_bottomMessage.setText("Câu đúng: "+g.getNumberCorrect() + " - Tổng:" + (g.getTotalQuestions() - 1));
           MainActivity mainActivity = (MainActivity) getActivity();

           int userzID = Integer.parseInt(mainActivity.getUserzID());
           Cursor cursor = new DAO(getActivity()).findScoreBoard2(userzID, 1);
            if (cursor.getCount() == 0) {
                int i = new DAO(getActivity()).insertScoreBoard(userzID,1, g.getScore());
            }
            else {
                int i = new DAO(getActivity()).updateScoreBoard(userzID,1, g.getScore());
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fg_btnStart = (Button) getActivity().findViewById(R.id.frmg_btnStart);
        fg_answerOne = (Button) getActivity().findViewById(R.id.frmg_answerOne);
        fg_answerTwo = (Button) getActivity().findViewById(R.id.frmg_answerTwo);
        fg_answerThree = (Button) getActivity().findViewById(R.id.frmg_answerThree);
        fg_answerFour = (Button) getActivity().findViewById(R.id.frmg_answerFour);
        fg_gameOver = (TextView) getActivity().findViewById(R.id.frmg_gameOver);
        fg_score = (TextView) getActivity().findViewById(R.id.frmg_score);
        fg_bottomMessage = (TextView) getActivity().findViewById(R.id.frmg_bottomMessage);
        fg_questions = (TextView) getActivity().findViewById(R.id.frmg_questions);
        fg_timer = (TextView) getActivity().findViewById(R.id.frmg_timer);
        fg_progTimer = (ProgressBar) getActivity().findViewById(R.id.frmg_progTimer);
        fg_retry = (Button) getActivity().findViewById(R.id.frmg_retry);

        fg_bottomMessage.setText("Bấm nút \"Bắt đầu\" để chơi");
        fg_score.setText("Điểm: 0");
        viewInVisiblity();
        start();

        fg_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fg_retry.setVisibility(View.INVISIBLE);
                fg_gameOver.setVisibility(View.INVISIBLE);
                fg_btnStart.setVisibility(View.VISIBLE);
                viewInVisiblity();
                secondsRemaining = 10;
                g.setNumberCorrect(0);
                g.setTotalQuestions(0);
                fg_bottomMessage.setText("Bấm nút \"Bắt đầu\" để chơi");
                fg_score.setText("Điểm: 0");
                fg_progTimer.setProgress(0);
            }
        });
    }

    public void start() {
       MainActivity activity = (MainActivity)getActivity();
       m_Text = activity.getUserzName();

        View.OnClickListener startButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button) v;
                start_button.setVisibility(View.INVISIBLE);
                viewVisiblity();
                nextTurn();
                timer.start();
            }
        };

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;
                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());
                g.checkAnswer(answerSelected);
                fg_score.setText("Điểm: "+Integer.toString(g.getScore()));
                nextTurn();
            }
        };

        fg_btnStart.setOnClickListener(startButtonClickListener);
        fg_answerOne.setOnClickListener(answerButtonClickListener);
        fg_answerTwo.setOnClickListener(answerButtonClickListener);
        fg_answerThree.setOnClickListener(answerButtonClickListener);
        fg_answerFour.setOnClickListener(answerButtonClickListener);
    }

    private void nextTurn() {
        g.makeNewQuestion();
        int[] answer = g.getCurrentQuestion().getAnswerArray();
        fg_answerOne.setText(Integer.toString(answer[0]));
        fg_answerTwo.setText(Integer.toString(answer[1]));
        fg_answerThree.setText(Integer.toString(answer[2]));
        fg_answerFour.setText(Integer.toString(answer[3]));
        fg_questions.setText(g.getCurrentQuestion().getQuestionPhrase());
        fg_bottomMessage.setText("Câu đúng: "+g.getNumberCorrect() + " - Tổng:" + (g.getTotalQuestions() - 1));
    }

    private void viewInVisiblity () {
        fg_answerOne.setEnabled(false);
        fg_answerOne.setVisibility(View.INVISIBLE);
        fg_answerTwo.setEnabled(false);
        fg_answerTwo.setVisibility(View.INVISIBLE);
        fg_answerThree.setEnabled(false);
        fg_answerThree.setVisibility(View.INVISIBLE);
        fg_answerFour.setEnabled(false);
        fg_answerFour.setVisibility(View.INVISIBLE);
        fg_questions.setVisibility(View.INVISIBLE);
        fg_gameOver.setVisibility(View.INVISIBLE);
        fg_progTimer.setVisibility(View.INVISIBLE);
        fg_timer.setVisibility(View.INVISIBLE);
        fg_score.setVisibility(View.INVISIBLE);
        fg_retry.setVisibility(View.INVISIBLE);
    }

    private void viewVisiblity () {
        fg_answerOne.setEnabled(true);
        fg_answerOne.setVisibility(View.VISIBLE);
        fg_answerTwo.setEnabled(true);
        fg_answerTwo.setVisibility(View.VISIBLE);
        fg_answerThree.setEnabled(true);
        fg_answerThree.setVisibility(View.VISIBLE);
        fg_answerFour.setEnabled(true);
        fg_answerFour.setVisibility(View.VISIBLE);
        fg_questions.setVisibility(View.VISIBLE);
        fg_progTimer.setVisibility(View.VISIBLE);
        fg_timer.setVisibility(View.VISIBLE);
        fg_score.setVisibility(View.VISIBLE);
    }
}