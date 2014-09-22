package com.divino.imagesizeelite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdManager.ErrorCode;
import cn.domob.android.ads.DomobAdView;

import com.divino.imagesizeelite.R;



import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Bitmap mBitmap;
	private String mBitmapFileName;
	private Context mContext;
	private Button mShareButton;
	private File mFile;
	
	private int mLengthOption = -1;
	private int mSizeOption = -1;
	private int mRatioOption = -1;
	
	private RelativeLayout mAdContainer;
	private DomobAdView mAdview;
	
	private String PUBLISHER_ID = "56OJwEL4uNCSlHTBdO";
	private String InlinePPID = "16TLuVClApvawNU049Sv-2yk";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getOverflowMenu();
		mContext = this;
		
		Button selectImageButton = (Button)this.findViewById(R.id.selectImage_button);
		selectImageButton.setOnClickListener(selectImageButtonOnClick);
		
		Button optionButton = (Button)this.findViewById(R.id.optionButton);
		optionButton.setOnClickListener(optionButtonOnClick);
		
		Button actionButton = (Button)this.findViewById(R.id.actionButton);
		actionButton.setOnClickListener(actionButtonOnClick);
		
		mShareButton = (Button)this.findViewById(R.id.shareButton);
		mShareButton.setOnClickListener(shareButtonButtonOnClick);
		mShareButton.setVisibility(View.INVISIBLE);
		
		
		//直接打开发过来的图片
		Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();
	 
	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if (type.startsWith("image/")) {
	        	Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	            if (imageUri != null) {
	            	ContentResolver cr = this.getContentResolver();  
	            	mBitmapFileName = imageUri.getLastPathSegment();
		        	try {
		        		mBitmap = BitmapFactory.decodeStream(cr.openInputStream(imageUri));
			            ImageView imageView = (ImageView) findViewById(R.id.imageView);  
			            imageView.setImageBitmap(mBitmap);  
			            mShareButton.setVisibility(View.VISIBLE);			            
					}
					catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	            }
	        }
	    } 
		
		//多盟广告

		mAdContainer = (RelativeLayout)findViewById(R.id.adcontainer);
		mAdview = new DomobAdView(this, PUBLISHER_ID, InlinePPID);
		/*mAdview.setKeyword("game");
		mAdview.setUserGender("male");
		mAdview.setUserBirthdayStr("2000-01-01");
		mAdview.setUserPostcode("123456");
		*/
		mAdview.setAdEventListener(new DomobAdEventListener(){

			@Override
			public void onDomobAdClicked(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDomobAdFailed(DomobAdView arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDomobAdOverlayDismissed(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDomobAdOverlayPresented(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Context onDomobAdRequiresCurrentContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void onDomobAdReturned(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDomobLeaveApplication(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mAdview.setLayoutParams(layout);
		mAdview.setAdSize(DomobAdView.INLINE_SIZE_FLEXIBLE);
		mAdContainer.addView(mAdview);

	}
	
	
	Button.OnClickListener shareButtonButtonOnClick = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			if (mFile != null) {
				Intent intent = new Intent(Intent.ACTION_SEND);   
		        intent.setType("image/*");   
		        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFile)); 
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		        startActivity(Intent.createChooser(intent, getTitle()));
			}
		}
	};
	
	Button.OnClickListener optionButtonOnClick = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(mContext, OptionActivity.class);

            startActivityForResult(intent, 2);  
		}
	};
	
	Button.OnClickListener selectImageButtonOnClick = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			
			mShareButton.setVisibility(View.INVISIBLE);
			
			Intent intent = new Intent();  
            /* 开启Pictures画面Type设定为image */  
            intent.setType("image/*");  
            /* 使用Intent.ACTION_GET_CONTENT这个Action */  
            intent.setAction(Intent.ACTION_GET_CONTENT);   
            /* 取得相片后返回本画面 */  
            startActivityForResult(intent, 1);  
		}
	};
	
	Button.OnClickListener actionButtonOnClick = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			if (mBitmap != null) {
				Bitmap bitmap = null;
				
				ByteArrayOutputStream baos = null;
				//长度
				if (mLengthOption != -1) {
					bitmap = ImageUtil.resize(mBitmap, mLengthOption, mLengthOption);
				}
				else {
					bitmap = mBitmap;
				}
				
				//压缩
				if (mSizeOption != -1) {
					baos = ImageUtil.compressImageByLength(bitmap, mSizeOption);
				}
				else if (mRatioOption != -1) {
					baos = ImageUtil.compressImageByRatio(bitmap, mRatioOption);
				}
				else {
					baos = ImageUtil.convertToStream(bitmap);
				}

				try
				{
					//if (bitmap != null) {
						String fileName = Environment.getExternalStorageDirectory() + "/ImageResizeElite/" + mBitmapFileName + ".jpg";
						
						mFile = ImageUtil.saveBitmap(baos, fileName);
						mShareButton.setVisibility(View.VISIBLE);
						Toast.makeText(mContext, "图片压缩成功，另存为：" + fileName, Toast.LENGTH_LONG).show();
					//}
				}
				catch (Exception ex) {
					Toast.makeText(mContext, "图片压缩失败", Toast.LENGTH_SHORT).show();
					mShareButton.setVisibility(View.INVISIBLE);
				} 
		
				
			}
			
			else {
				
				Toast.makeText(mContext, "请先选择图片", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	 @Override  
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode == RESULT_OK) {
			 if (requestCode == 1) {
				 Uri uri = data.getData();  
		            //Log.e("uri", uri.toString());  
		         ContentResolver cr = this.getContentResolver();  
		         try {  
		        	 mBitmapFileName = uri.getLastPathSegment();
		        	 mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
		             ImageView imageView = (ImageView) findViewById(R.id.imageView);  
		                /* 将Bitmap设定到ImageView */  
		             imageView.setImageBitmap(mBitmap);  
		         }
		         catch (FileNotFoundException e) {  
		        	 Log.e("Exception", e.getMessage(),e);  
		         }
			 }
			 else if (requestCode == 2) {
				 //从设置页返回
				 
				 mLengthOption = Integer.parseInt(data.getStringExtra("length"));
				 mSizeOption = Integer.parseInt(data.getStringExtra("size"));
				 mRatioOption = Integer.parseInt(data.getStringExtra("ratio"));
				 
			 }
		 }  
	     super.onActivityResult(requestCode, resultCode, data);  
	 } 
	 
	//force to show overflow menu in actionbar
		private void getOverflowMenu() {
		     try {
		        ViewConfiguration config = ViewConfiguration.get(this);
		        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
		        if(menuKeyField != null) {
		            menuKeyField.setAccessible(true);
		            menuKeyField.setBoolean(config, false);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.activity_main, menu);
		 return super.onCreateOptionsMenu(menu);
	}

	//菜单项被选中时执行该方法
	 
	 private MediaScannerConnection conn;
	 private String SCAN_PATH;

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.about_menuitem:
				Intent intent = new Intent();
				intent.setClass(mContext, AboutActivity.class);
				startActivity(intent);  
				break;
			case R.id.gallary_menuitem:

				try {
					File folder = new File(Environment.getExternalStorageDirectory().toString()+"/ImageResizeElite/");
					String[] allFiles = folder.list();
					SCAN_PATH = Environment.getExternalStorageDirectory().toString()+"/ImageResizeElite/"+allFiles[0];
					
					Intent intent2 = new Intent();
					intent2.setAction(android.content.Intent.ACTION_VIEW);
					File file = new File(SCAN_PATH);
					intent2.setDataAndType(Uri.fromFile(file), "image/*");
					startActivity(intent2);
				}
				catch (Exception ex) {
					Toast.makeText(mContext, "打开压缩图库失败", Toast.LENGTH_LONG);
				}
				finally {
					
				}
				
				
				break;
	    }
	    return true;
	}

}
