package com.divino.imagesizeelite;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

public class ImageUtil {
	
	public static File saveBitmap(ByteArrayOutputStream bytes, String _file) throws IOException {
        BufferedOutputStream os = null;
        File file = null;
        try {
            file = new File(_file);
            // String _filePath_file.replace(File.separatorChar +
            // file.getName(), "");
            int end = _file.lastIndexOf(File.separator);
            String _filePath = _file.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
          
            FileOutputStream fo = new FileOutputStream(_file);
            fo.write(bytes.toByteArray());
            fo.close();
        } 
        catch (IOException e){
        	Log.e("ERROR", e.getMessage());
        }
        
        finally {
        	return file;
        }
    }
	
	public static ByteArrayOutputStream convertToStream(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);			//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  

        return baos;  
	}
	
	public static ByteArrayOutputStream compressImageByLength(Bitmap image, int maxLength) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);			//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
        int options = 100;  
        while ( baos.toByteArray().length / 1024 > maxLength) {  				//ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
            baos.reset();//����baos�����baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);	//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
            options -= 5;//ÿ�ζ�����10  
        }  
        
        return baos;  
    } 
	
	public static ByteArrayOutputStream compressImageByRatio(Bitmap image, int ratio) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, ratio, baos);			//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  

        return baos;  
    } 
	
	public static Bitmap resize(Bitmap originalBitmap, int reqHeight, int reqWidth) {  
		      
		int originalWidth = originalBitmap.getWidth();
		int originalHeight = originalBitmap.getHeight();

		float scale = 1;
		if (originalHeight > originalWidth) {
			scale = (float)reqHeight / originalHeight;
		}
		else {
			scale = (float)reqWidth / originalWidth;
		}
		
		float scaledWidth = scale;
		float scaledHeight = scale;

		// �O�� transform matrix
		Matrix scaleMatrix = new Matrix();
		scaleMatrix.postScale(scaledWidth, scaledHeight);

		// �a���s�D��� bitmap
		return Bitmap.createBitmap(originalBitmap, 0, 0, originalWidth, originalHeight, scaleMatrix, true);

	}  
	
	public static synchronized Bitmap decodeSampledBitmapFromStream(InputStream in, int reqWidth, int reqHeight) {  
	  
	    // First decode with inJustDecodeBounds=true to check dimensions  
	    final BitmapFactory.Options options = new BitmapFactory.Options();  
	    options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeStream(in, null, options);  
	  
	    // Calculate inSampleSize  
	    options.inSampleSize = 8; calculateInSampleSize(options, reqWidth, reqHeight);  
	  
	    // Decode bitmap with inSampleSize set  
	    options.inJustDecodeBounds = false;  
	    return BitmapFactory.decodeStream(in, null, options);  
	}  
	  
	 
	public static int calculateInSampleSize(BitmapFactory.Options options,  
	        int reqWidth, int reqHeight) {  
	    // Raw height and width of image  
	    final int height = options.outHeight;  
	    final int width = options.outWidth;  
	    int inSampleSize = 1;  
	  
	    //�ȸ��ݿ�Ƚ�����С  
	    while (width / inSampleSize > reqWidth) {  
	        inSampleSize++;  
	    }  
	    //Ȼ����ݸ߶Ƚ�����С  
	    while (height / inSampleSize > reqHeight) {  
	        inSampleSize++;  
	    }  
	    return inSampleSize;  
	} 
}
