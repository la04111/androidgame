package activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daa.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import modelz.DAO;
import modelz.Userz;

public class LoginActivity extends AppCompatActivity {


    EditText al_mail, al_password, ir_name, ir_password,
            ir_mail, ir_confirmPass;
    Button login, signUp, ir_register;
    TextInputLayout al_til1, al_til2, ir_til1;
    CheckBox rememberMe;

    EditText ir_emailforget, ir_passwordcheck, ir_passwordcheckdone, ir_code;
    Button ir_sendcode, ir_resetpass;
    String otp = "000000";
    TextView forgetpass;


    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        al_mail = (EditText) findViewById(R.id.al_mail);
        al_password = (EditText) findViewById(R.id.al_password);
        login = findViewById(R.id.al_login);
        signUp = findViewById(R.id.al_signUp);
        al_til1 = findViewById(R.id.al_til1);
        al_til2 = findViewById(R.id.al_til2);
        rememberMe = findViewById(R.id.al_rememberMe);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            al_mail.setText(loginPreferences.getString("username", ""));
            al_password.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }
        ClickLogin();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });

        forgetpass = findViewById(R.id.forgetpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSendcode();
            }
        });
    }

    private void ClickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(al_mail.getWindowToken(), 0);
                if (al_mail.getText().toString().trim().isEmpty()) {
                    al_til1.setError("Vui lòng nhập tài khoản của bạn");
                } else {
                    al_til1.setError(null);
                    if (al_password.getText().toString().trim().isEmpty()) {
                        al_til2.setError("Vui lòng nhập mật khẩu của bạn");
                    } else {
                        al_til2.setError(null);
                        if (rememberMe.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("username", al_mail.getText().toString());
                            loginPrefsEditor.putString("password", al_password.getText().toString());
                            loginPrefsEditor.commit();

                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                        }
                        Cursor cursor = new DAO(LoginActivity.this).findUserz(al_mail.getText().toString(), al_password.getText().toString());
                        while (cursor.moveToNext()) {
                            if (cursor.getInt(4) == 0) {

                                customStartActivity(MainActivity.class, cursor.getInt(0), cursor.getString(1));
                            } else {
                                customStartActivity(AdminActivity.class, cursor.getInt(0), cursor.getString(1));
                            }
                        }
                    }
                }
            }
        });
    }

    private void customStartActivity(Class aClass, int userzID, String userzName) {
        Intent intent = new Intent(LoginActivity.this, aClass);
        intent.putExtra("userzID", String.valueOf(userzID));
        intent.putExtra("userzName", userzName);
        startActivity(intent);
    }

    private void ClickSendcode() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.inflater_forgetpassword, null);
        dialog.setView(dialogView);
        ir_emailforget = dialogView.findViewById(R.id.ir_emailforget);
        ir_passwordcheck = dialogView.findViewById(R.id.ir_passwordcheck1);
        ir_passwordcheckdone = dialogView.findViewById(R.id.ir_confirmPasscheck);
        ir_code = dialogView.findViewById(R.id.ir_checkcode);
        ir_sendcode = dialogView.findViewById(R.id.ir_sendcode);
        // ir_til1 = dialogView.findViewById(R.id.ir_til1);
        ir_resetpass = dialogView.findViewById(R.id.ir_resetpass);
        ir_resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp.equals(ir_code.getText().toString())) {
                    ir_code.setError(null);
                    if (ir_passwordcheck.getText().toString().isEmpty()) {
                        ir_passwordcheck.setError("Vui lòng nhập mật khẩu của bạn");
                    } else {
                        if (ir_passwordcheckdone.getText().toString().isEmpty()) {
                            ir_passwordcheck.setError(null);
                            ir_passwordcheckdone.setError("Vui lòng nhập mật khẩu xác nhận của bạn");
                        } else {
                            if (ir_passwordcheck.getText().toString().equals(ir_passwordcheckdone.getText().toString())) {
                                ir_passwordcheck.setError(null);
                                ir_passwordcheckdone.setError(null);
                                Cursor cursor1 = new DAO(LoginActivity.this).findUserz(ir_emailforget.getText().toString());
                                Userz use = new Userz();
                                while (cursor1.moveToNext()) {
                                    use.setUserzID(Integer.valueOf(cursor1.getInt(0)));
                                    use.setUserzName(String.valueOf(cursor1.getString(1)));
                                    use.setUserzPassword(ir_passwordcheckdone.getText().toString());
                                    use.setUserzMail(String.valueOf(cursor1.getString(3)));
                                    use.setUserzRole(Integer.valueOf(cursor1.getInt(4)));
                                }
                                int i = new DAO(LoginActivity.this).updateUsersz(use);
                                if (i == 1) {
                                    Toast.makeText(LoginActivity.this, "Thay doi mat khau thanh cong", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Thay doi mat khau khong thanh cong", Toast.LENGTH_LONG).show();
                                }

                            }else{
                                ir_passwordcheckdone.setError("Mật khẩu xác nhận không trùng khớp");
                            }
                        }
                    }
                } else {
                    ir_code.setError("Vui lòng nhập mã xác nhận của bạn");
                }
            }
        });

        ir_sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otps = new DecimalFormat("000000").format(new Random().nextInt(999999));
                otp = otps;
//
                if (ir_emailforget.getText().toString().isEmpty()) {
                    ir_emailforget.setError("Vui lòng nhập Email của bạn");
                } else {
                    ir_emailforget.setError(null);
                    try {
                        String stringSenderEmail = "tiennguyennaraka@gmail.com";
                        String stringReceiverEmail = ir_emailforget.getText().toString();
                        String stringPasswordSenderEmail = "ejvtoerwvhxgrbng";

                        String stringHost = "smtp.gmail.com";

                        Properties properties = System.getProperties();

                        properties.put("mail.smtp.host", stringHost);
                        properties.put("mail.smtp.port", "465");
                        properties.put("mail.smtp.ssl.enable", "true");
                        properties.put("mail.smtp.auth", "true");

                        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                            }
                        });

                        MimeMessage mimeMessage = new MimeMessage(session);
                        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));
                        mimeMessage.setSubject("Mã xác nhận của bạn");
                        //  mimeMessage.setText("Hello Programmer, \n\nProgrammer World has sent you this 2nd email. \n\n Cheers!\nProgrammer World");
                        String body = "<div id=':33' class='ii gt' jslog='20277; u014N:xr6bB; 4:W251bGwsbnVsbCxbXV0.'>" + "<div id=':32' class='a3s aiL '>" + "<u>" + "</u>" +
                                "<div>" +
                                "<div style='width:800px;text-align:center;margin:0 auto'>" +
                                "<table align='center' width='800' height='1000' cellpadding='0' cellspacing='0' border='0' style='background:#A77979'>" +
                                "<tbody>" + "<tr>" +
                                "<td align='center' valign='top' style='background:url(https://wallpapercave.com/uwp/uwp2052576.jpeg)'>" +
                                "<table width='576' cellpadding='0' cellspacing='0' border='0'>" +
                                "<tbody>" + "<tr>" +
                                "<td height='250'>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td align='left' valign='top' style='color:#fff'>" +
                                "<font color='#e35b5b' style='font-size:26px'>" +
                                "<strong>" +
                                "Gửi khách hàng:" +
                                "<br>" +
                                "Đây là mã xác nhận của bạn, vui lòng nhập vào:" +
                                "</strong>" +
                                "</font>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td height='40' valign='top'>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td align='center' height='54' style='background:#202121;letter-spacing:15px;color:#ffffff'>" +
                                "<font size='6' color='#FFFFFF'>" +
                                // code here
                                otp +

                                "</font>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td height='40' valign='top'>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td valign='top' style='color:#e35b5b'>" +
                                "<font size='4' color='#e35b5b'>" +
                                "Mọi thắc mắc vui lòng gửi email " +
                                "<a href='mailto:longan04111@gmail.com ' target='_blank'>" + "longan04111@gmail.com" +
                                "</a>" + "để biết thêm thông tin." +

                                "Nếu bạn không có thắc mắc nào, vui lòng đừng gửi thư rác!." +
                                "</font>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td height='40' valign='top'>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td height='60' valign='top' style='color:#e35b5b'>" +
                                "<font size='4' color='#e35b5b'>" + "Một ngày tốt lành！" +
                                "</font>" + "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td height='50' valign='top' style='color:#e35b5b'>" +
                                "<font size='5' color='#e35b5b'>" +
                                "<strong>" + "TN" + "</strong>" +
                                "</font>" +
                                "</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td height='100%'>" + "</td>" +
                                "</tr>" +
                                "</tbody>" + "</table>" +
                                "</td>" +
                                "</tr>" +
                                "</tbody>" + "</table>" +
                                "</div>" +
                                "</div>" + "</div>" + "<div class='yj6qo'>" + "</div>" + "</div>";
                        mimeMessage.setContent(body, "text/html; charset=utf-8");
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Transport.send(mimeMessage);
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        //Toast.makeText(getApplication(), otp, Toast.LENGTH_LONG).show();
                    } catch (AddressException e) {
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
                //
            }
        });
        dialog.show();
    }


    //
    private void ClickSignUp() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.inflater_register, null);
        dialog.setView(dialogView);
        ir_name = dialogView.findViewById(R.id.ir_name);
        ir_mail = dialogView.findViewById(R.id.ir_mail);
        ir_password = dialogView.findViewById(R.id.ir_password);
        ir_confirmPass = dialogView.findViewById(R.id.ir_confirmPass);
        ir_register = dialogView.findViewById(R.id.ir_register);
        ir_til1 = dialogView.findViewById(R.id.ir_til1);
        ir_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ir_name.getText().toString().trim().isEmpty()) {
                    ir_name.setError("Vui lòng nhập tên tài khoản");
                } else {
                    ir_name.setError(null);
                    if (ir_mail.getText().toString().trim().isEmpty()) {
                        ir_mail.setError("Vui lòng nhập Email của bạn");
                    } else {
                        ir_mail.setError(null);
                        if (ir_password.getText().toString().trim().isEmpty()) {
                            ir_til1.setPasswordVisibilityToggleEnabled(false);
                            ir_password.setError("Vui lòng nhập mật khẩu của bạn");
                        } else {
                            ir_password.setError(null);
                            ir_til1.setPasswordVisibilityToggleEnabled(true);
                            if (ir_confirmPass.getText().toString().trim().isEmpty()) {
                                ir_confirmPass.setError("Vui lòng nhập lại mật khẩu của bạn");
                            } else {
                                if (ir_password.getText().toString().equals(ir_confirmPass.getText().toString()) == false) {
                                    ir_confirmPass.setError("Mật khẩu xác nhận không đúng");
                                } else {
                                    ir_confirmPass.setError(null);
                                    Userz userz = new Userz(ir_name.getText().toString(),
                                            ir_mail.getText().toString(), ir_password.getText().toString());
                                    int i = new DAO(LoginActivity.this).signUp(userz);
                                    if (i == 1) {
                                        Toast.makeText(getApplication(), "Sign up successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplication(), "Sign up failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        dialog.show();
    }
}