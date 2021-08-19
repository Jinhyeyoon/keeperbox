package co.kr.mergepoint.aos_keeperbox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import co.kr.mergepoint.aos_keeperbox.databinding.ActivityLoginBinding;

/**
 * Created by Alicia on 2020/10/06.
 */
public class LoginActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener, View.OnFocusChangeListener {

    ActivityLoginBinding binding;

    ArrayList<String> idList = new ArrayList<String>();
    ArrayList<String> pwList = new ArrayList<String>();

    boolean isLogin = false;
    boolean isUserId = false;
    boolean isPassword = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setTitle("로그인");
        binding.setOnClick(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_back_button:
                finish();
                break;

            case R.id.login_button:
                checkLogin();
                break;

            case R.id.register_button:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
        }

    }

    public void checkLogin() {
        try {
            String isUser = new login_data_paser().execute().get();
            if (isUser.equals("ok")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "회원 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            switch (v.getId()) {
                case R.id.insert_id: //아이디
                    binding.insertId.clearFocus();
                    break;
                case R.id.insert_password: //패스워드
                    binding.insertPassword.clearFocus();
                    break;
            }
        }
        hideKeyboard();
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean focus) {
        if (!focus) {
            switch (v.getId()) {
                case R.id.insert_id: //이메일
                    isUserId = !binding.insertId.getText().toString().isEmpty();
                    checkContent(isUserId, isPassword);
                    break;
                case R.id.insert_password: //비밀번호
                    isPassword = !binding.insertPassword.getText().toString().isEmpty();
                    checkContent(isUserId, isPassword);
                    break;
            }
        }

    }

    public class login_data_paser extends AsyncTask<String, Void, String> {
        String userId = binding.insertId.getText().toString().trim();
        String userPW = binding.insertPassword.toString();

        @Override
        protected void onPreExecute() {
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            InputStream is = null;
            String parse_data = "";
            String json_data = "";
            StringBuffer buffer;
            StringBuffer id_data = new StringBuffer();
            StringBuffer pass_data = new StringBuffer();

            try {
                is = new URL("https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1IHIr8XrSREqIggabyFmO3zF-sVoJvfkVocqacWYtCCI&sheet=mem_info").openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                buffer = new StringBuffer();
                while ((parse_data = rd.readLine()) != null) {
                    buffer.append(parse_data);
                }
                json_data = buffer.toString();

                JSONObject jsonObject = new JSONObject(json_data);

                JSONArray jsonArray = jsonObject.getJSONArray("mem_info");
                for (int i = 0; i < jsonArray.length(); i++) {
                    idList.add(jsonArray.getJSONObject(i).getString("user_id"));
                    pwList.add(jsonArray.getJSONObject(i).getString("password"));
                }

                for (int i = 0; i < idList.size(); i++) {
                    if (userId.equals(idList.get(i)) && userPW.equals(pwList.get(i))) {
                        isLogin = true;
                        return "ok";
                    }
                }
                return "no";

            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && imm != null)
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void checkContent(boolean isUserId, boolean isPassword) {
        if (isUserId && isPassword) {
            binding.loginButton.setEnabled(true);
        } else {
            binding.loginButton.setEnabled(false);
        }
    }


}
