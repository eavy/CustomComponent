package com.android.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

public class General extends RelativeLayout {
    private static final String TAG = "General";

    private TextView title;
    private TextView select;
    private PopupWindow mPopupWindow;
    private Button mCompleteButton;
    private ListView mOptionListView;
    private List<ParameterSettingListOption> data;
    private String titleText;
    private int idx;
    CompleteBtnListener listener;
    public General(Context context) {
        super(context);
        Log.i(TAG, "General: 1");
    }

    public General(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "General: 3");
    }

    public General(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.i(TAG, "General: 4");
    }

    public void setData(List<ParameterSettingListOption> data){
        this.data = data;
    }

    public General(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "General: 2");
        LayoutInflater.from(context).inflate(R.layout.duration, this, true);
        title = findViewById(R.id.tv_title);
        select = findViewById(R.id.tv_select);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomItem);
        if (attributes != null) {
            //title
            titleText = attributes.getString(R.styleable.CustomItem_item_title);
            if (!TextUtils.isEmpty(titleText)) {
                title.setText(titleText);
            }

            String selectText = attributes.getString(R.styleable.CustomItem_item_select);
            if (!TextUtils.isEmpty(selectText)) {
                select.setText(selectText);
            }
        }

        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: " + attributes.getString(R.styleable.CustomItem_item_title));
                initPopupWindow();
            }
        });
    }

    private void initPopupWindow(){
        Log.i(TAG, "initPopupWindow: ");
        View mDialogView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow,null);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mDialogView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        }
        Log.i(TAG, "initPopupWindow: mPopupWindow " + mPopupWindow);

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(getRootView(), Gravity.RIGHT, 0, 0);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        TextView popup_title = mDialogView.findViewById(R.id.popup_title);
        popup_title.setText(titleText);
        mCompleteButton = mDialogView.findViewById(R.id.btn_complete);
        mOptionListView = mDialogView.findViewById(R.id.lv_option);
        initOptionList();
    }

    private void initOptionList(){
        Log.i(TAG, "initOptionList: data " + data);
        if (data == null) {
            return;
        }
        final GeneralAdapter generalAdapter = new GeneralAdapter(getContext(), R.layout.general_option_list_item, data);
        if(idx < 0 || mOptionListView == null || idx >= data.size())
        {
            idx = 0;
        }
        generalAdapter.setSelectOptionPosition(idx);
        mOptionListView.setAdapter(generalAdapter);
        mOptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Log.i(TAG, "onItemClick: position " + position);
                idx = position;
                generalAdapter.setSelectOptionPosition(position);
                generalAdapter.notifyDataSetChanged();
            }
        });
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i(TAG, "onClick: complete listener" + listener);
                showSetting(idx);

                if (listener!= null ) {
                    listener.complete(idx);
                }

//                setVideoDurationSetting(mVideoDurationIdx);
                mPopupWindow.dismiss();
            }
        });
    }

    private void showSetting(int index)
    {
        Log.i(TAG, "showSetting: index" + index);
        Log.i(TAG, "showSetting: data" + data);
        if (index<0||index>=data.size()) {
            return;
        }
        String selectOption = data.get(index).getOptionName();
        Log.d(TAG,"showVideoDurationSetting,selectOption:"+selectOption);
        select.setText(selectOption);
    }

    public void setIdx(int idx) {
        this.idx = idx;
        showSetting(idx);
    }

    interface CompleteBtnListener {
        void complete(int index);
    }

    public void setListener( CompleteBtnListener listener){
        this.listener = listener;
    }
}
