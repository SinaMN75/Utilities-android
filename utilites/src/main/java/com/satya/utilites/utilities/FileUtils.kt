package com.satya.utilites.utilities

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

@SuppressLint("Recycle") object FileUtils {
	fun realPathFromURI(context: Context, uri: Uri): String? {
		when {
			DocumentsContract.isDocumentUri(context, uri) -> when {
				isExternalStorageDocument(uri) -> {
					val docId = DocumentsContract.getDocumentId(uri)
					val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
					return if ("primary".equals(split[0], ignoreCase = true)) {
						if (split.size > 1) Environment.getExternalStorageDirectory().toString() + "/" + split[1]
						else Environment.getExternalStorageDirectory().toString() + "/"
					} else "storage" + "/" + docId.replace(":", "/")
				}
				isDownloadsDocument(uri) -> {
					val fileName = getFilePath(context, uri)
					if (fileName != null) return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
					return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(DocumentsContract.getDocumentId(uri))), null, null)
				}
				isMediaDocument(uri) -> {
					val split = DocumentsContract.getDocumentId(uri).split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
					var contentUri: Uri? = null
					when (split[0]) {
						"image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
						"video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
						"audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
					}
					return getDataColumn(context, contentUri, "_id=?", arrayOf(split[1]))
				}
			}
			"content".equals(uri.scheme!!, ignoreCase = true) -> return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
			"file".equals(uri.scheme!!, ignoreCase = true) -> return uri.path
		}
		return null
	}
	
	private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
		val column = "_data"
		context.contentResolver.query(uri!!, arrayOf(column), selection, selectionArgs, null)!!.use { cursor -> if (cursor.moveToFirst()) return cursor.getString(cursor.getColumnIndexOrThrow(column)) }
		return null
	}
	
	private fun getFilePath(context: Context, uri: Uri): String? {
		context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null)!!.use { if (it.moveToFirst()) return it.getString(it.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)) }
		return null
	}
	
	private fun isExternalStorageDocument(uri: Uri): Boolean = "com.android.externalstorage.documents" == uri.authority
	private fun isDownloadsDocument(uri: Uri): Boolean = "com.android.providers.downloads.documents" == uri.authority
	private fun isMediaDocument(uri: Uri): Boolean = "com.android.providers.media.documents" == uri.authority
	private fun isGooglePhotosUri(uri: Uri): Boolean = "com.google.android.apps.photos.content" == uri.authority
}