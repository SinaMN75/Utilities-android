package com.developersian.util.Utilities;
import android.annotation.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
public class FileUtils{
	public static String getRealPath(Context context,Uri fileUri){
		String realPath;
		realPath=FileUtils.getRealPathFromURI_API19(context,fileUri);
		return realPath;
	}
	@SuppressLint("NewApi") public static String getRealPathFromURI_API11to18(Context context,Uri contentUri){
		String[] proj={MediaStore.Images.Media.DATA};
		String result=null;
		CursorLoader cursorLoader=new CursorLoader(context,contentUri,proj,null,null,null);
		Cursor cursor=cursorLoader.loadInBackground();
		if(cursor!=null){
			int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result=cursor.getString(column_index);
			cursor.close();
		}
		return result;
	}
	public static String getRealPathFromURI_BelowAPI11(Context context,Uri contentUri){
		String[] proj={MediaStore.Images.Media.DATA};
		Cursor cursor=context.getContentResolver().query(contentUri,proj,null,null,null);
		int column_index=0;
		String result="";
		if(cursor!=null){
			column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result=cursor.getString(column_index);
			cursor.close();
			return result;
		}
		return result;
	}
	@SuppressLint("NewApi") public static String getRealPathFromURI_API19(final Context context,final Uri uri){
		if(DocumentsContract.isDocumentUri(context,uri)){
			if(isExternalStorageDocument(uri)){
				final String docId=DocumentsContract.getDocumentId(uri);
				final String[] split=docId.split(":");
				final String type=split[0];
				if("primary".equalsIgnoreCase(type)){
					if(split.length>1){
						return Environment.getExternalStorageDirectory()+"/"+split[1];
					}else{
						return Environment.getExternalStorageDirectory()+"/";
					}
				}else{
					return "storage"+"/"+docId.replace(":","/");
				}
			}else
				if(isDownloadsDocument(uri)){
					String fileName=getFilePath(context,uri);
					if(fileName!=null){
						return Environment.getExternalStorageDirectory().toString()+"/Download/"+fileName;
					}
					final String id=DocumentsContract.getDocumentId(uri);
					final Uri contentUri=ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
					return getDataColumn(context,contentUri,null,null);
				}else
					if(isMediaDocument(uri)){
						final String docId=DocumentsContract.getDocumentId(uri);
						final String[] split=docId.split(":");
						final String type=split[0];
						Uri contentUri=null;
						if("image".equals(type)){
							contentUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
						}else
							if("video".equals(type)){
								contentUri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
							}else
								if("audio".equals(type)){
									contentUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
								}
						final String selection="_id=?";
						final String[] selectionArgs=new String[]{split[1]};
						return getDataColumn(context,contentUri,selection,selectionArgs);
					}
		}else
			if("content".equalsIgnoreCase(uri.getScheme())){
				if(isGooglePhotosUri(uri))
					return uri.getLastPathSegment();
				return getDataColumn(context,uri,null,null);
			}else
				if("file".equalsIgnoreCase(uri.getScheme())){
					return uri.getPath();
				}
		return null;
	}
	public static String getDataColumn(Context context,Uri uri,String selection,String[] selectionArgs){
		final String column="_data";
		final String[] projection={column};
		try(Cursor cursor=context.getContentResolver().query(uri,projection,selection,selectionArgs,null)){
			if(cursor!=null&&cursor.moveToFirst()){
				final int index=cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		}
		return null;
	}
	public static String getFilePath(Context context,Uri uri){
		final String[] projection={MediaStore.MediaColumns.DISPLAY_NAME};
		try(Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null)){
			if(cursor!=null&&cursor.moveToFirst()){
				final int index=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
				return cursor.getString(index);
			}
		}
		return null;
	}
	public static boolean isExternalStorageDocument(Uri uri){
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}
	public static boolean isDownloadsDocument(Uri uri){
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}
	public static boolean isMediaDocument(Uri uri){
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}
	public static boolean isGooglePhotosUri(Uri uri){
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}