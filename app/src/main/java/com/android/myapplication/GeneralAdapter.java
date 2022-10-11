package com.android.myapplication;

import java.util.List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class GeneralAdapter extends ArrayAdapter<ParameterSettingListOption> {
	private Context context;
	private int selectOptionPosition = -1;
	
	public GeneralAdapter(Context context, int resource, List<ParameterSettingListOption> list) {
		super(context, resource,list);
		this.context = context;
	}
	
	public void setSelectOptionPosition(int selectOptionPosition) {
		this.selectOptionPosition = selectOptionPosition;
	}
	
	public int getSelectOptionPosition() {
		return selectOptionPosition;
	}
	
	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ParameterSettingListOption listOption = getItem(position);
		View view = null;
		ViewHolder viewHolder = null;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.general_option_list_item,null);
			viewHolder = new ViewHolder();
			viewHolder.optionName = view.findViewById(R.id.tv_item_general_list);
			viewHolder.select = view.findViewById(R.id.rb_item_general_list);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.optionName.setText(listOption.getOptionName());
		if (selectOptionPosition == position) {
			viewHolder.select.setChecked(true);
		} else {
			viewHolder.select.setChecked(false);
		}
		
		return view;
	}
	
	private class ViewHolder {
		TextView optionName;
		RadioButton select;
	}
}
