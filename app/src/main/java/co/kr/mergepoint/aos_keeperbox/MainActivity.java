package co.kr.mergepoint.aos_keeperbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import co.kr.mergepoint.aos_keeperbox.databinding.ActivityMainBinding;


public class MainActivity extends Activity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setOnClick(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_bar:
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;

            case R.id.save_box:
                Intent intent1 = new Intent(this, SaveBoxActivity.class);
                startActivity(intent1);
                break;
        }
    }
}