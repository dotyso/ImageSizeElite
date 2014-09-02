package com.divino.imagesizeelite;

import com.divino.imagesizeelite.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
			
			setResult(RESULT_OK, intent);  
			finish();
		}
	};
}
