package co.kr.mergepoint.aos_keeperbox;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import java.util.concurrent.Executor;

import co.kr.mergepoint.aos_keeperbox.databinding.ActivityRegisterBinding;

/**
 * Created by Alicia on 2020/10/06.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {



    ActivityRegisterBinding binding;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        // DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setOnClick(this);
        binding.toolbarRegister.setTitle("회원가입");
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.register_button) {
            checkBioAuth();
        } else if (view.getId() == R.id.ic_back_button) {
            finish();
        }
    }

    public void checkBioAuth() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(RegisterActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("생체 인증")
                .setNegativeButtonText("취소")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
            biometricPrompt.authenticate(promptInfo);
        }
}
