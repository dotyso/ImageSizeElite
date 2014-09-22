package com.divino.imagesizeelite;

import com.divino.imagesizeelite.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class OptionActivity extends Activity {

	CheckBox mLengthCheckBox;
	EditText mLengthEditBox;
	
	RadioButton mSizeRadio;
	RadioButton mRatioRadio;
	
	EditText mSizeEditText;
	EditText mRatioEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		
		Button okButton = (Button)this.findViewById(R.id.okButton);
		okButton.setOnClickListener(okButtonOnClick);
		
		mLengthCheckBox = (CheckBox)this.findViewById(R.id.lengthCheckBox);
		mLengthEditBox = (EditText)this.findViewById(R.id.lengthEditText);
		
		mSizeRadio = (RadioButton)this.findViewById(R.id.sizeRadio);
		mRatioRadio = (RadioButton)this.findViewById(R.id.ratioRadio);
		
		mSizeEditText = (EditText)this.findViewById(R.id.sizeEditText);
		mRatioEditText = (EditText)this.findViewById(R.id.ratioEditText);
		
		SharedPreferences sharedPreferences = getSharedPreferences("ImageSizeElite", Context.MODE_PRIVATE); //私有数据
		mLengthCheckBox.setChecked(Boolean.valueOf(sharedPreferences.getString("lengthEnabled", "false")));
		mLengthEditBox.setText(sharedPreferences.getString("length", "800"));
		
		boolean sizeEnabled = Boolean.valueOf(sharedPreferences.getString("sizeEnabled", "true"));
		if (sizeEnabled)
			mSizeRadio.setChecked(true);
		else
			mRatioRadio.setChecked(true);
			
		mSizeEditText.setText(sharedPreferences.getString("size", "512"));
		mRatioEditText.setText(sharedPreferences.getString("ratio", "80"));

	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_option, menu);
		return true;
	}
	*/
	
	Button.OnClickListener okButtonOnClick = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			
			if (mLengthCheckBox.isChecked())
				intent.putExtra("length", mLengthEditBox.getText().toString());
			else
				intent.putExtra("length", "-1");

			if (mSizeRadio.isChecked())
				intent.putExtra("size", mSizeEditText.getText().toString());
			else
				intent.putExtra("size", "-1");
			
			if (mRatioRadio.isChecked())
				intent.putExtra("ratio", mRatioEditText.getText().toString());
			else
				intent.putExtra("ratio", "-1");
			
			
			SharedPreferences sharedPreferences = getSharedPreferences("ImageSizeElite", Context.MODE_PRIVATE); //私有数据
			Editor editor = sharedPreferences.edit();															//获取编辑器
			editor.putString("lengthEnabled", String.valueOf(mLengthCheckBox.isChecked()));
			editor.putString("sizeEnabled", String.valueOf(mSizeRadio.isChecked()));
			editor.putString("length", mLengthEditBox.getText().toString());
			editor.putString("size", mSizeEditText.getText().toString());
			editor.putString("ratio", mRatioEditText.getText().toString());			
			editor.commit();
			
			setResult(RESULT_OK, intent);  
			finish();
		}
	};
}
