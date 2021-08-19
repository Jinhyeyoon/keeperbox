package co.kr.mergepoint.aos_keeperbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import co.kr.mergepoint.aos_keeperbox.databinding.ActivityMenuBinding;

/**
 * Created by Alicia on 2020/10/06.
 */
public class MenuActivity extends Activity implements View.OnClickListener {

    ActivityMenuBinding binding;
    MenuActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setOnClick(this);
        binding.toolbarMypage.title.setText("마이페이지");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ic_back_button:
                activity.finish();
                break;

            case R.id.login_button:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.show_history:
                break;

            case R.id.show_location:
                break;
        }
    }
}
