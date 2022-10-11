package com.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<ParameterSettingListOption> mVideoDurationData = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadVideoDurationData();
        General general = findViewById(R.id.general);
        general.setData(mVideoDurationData);
        general.setIdx(2);

        general.setListener(new General.CompleteBtnListener() {
            @Override
            public void complete(int index) {
                Log.i(TAG, "complete: Main msg" + index);
                setVideoDurationSetting(index);
            }
        });
    }

    private void loadVideoDurationData() {
        if (mVideoDurationData == null) {
            mVideoDurationData = new ArrayList<ParameterSettingListOption>();
        }
        mVideoDurationData.clear();
        // 5 minutes
        ParameterSettingListOption parameter1 = new ParameterSettingListOption(getString(R.string.video_duration_para1));
        mVideoDurationData.add(parameter1);
        // 10 minutes
        ParameterSettingListOption parameter2 = new ParameterSettingListOption(getString(R.string.video_duration_para2));
        mVideoDurationData.add(parameter2);
        // 30 minutes
        ParameterSettingListOption parameter3 = new ParameterSettingListOption(getString(R.string.video_duration_para3));
        mVideoDurationData.add(parameter3);

//        initVideoDurationIndex();
    }

    private void setVideoDurationSetting(int index)
    {
        Log.d(TAG,"setVideoDurationSetting,index:"+index);
        Toast.makeText(this,"选择了第"+(index+1)+"项",Toast.LENGTH_SHORT).show();
    }
}