package com.developersian.webService.retrofit;

import android.os.*;

import java.io.*;

import okhttp3.*;
import okio.*;

public class ProgressRequestBody extends RequestBody {
	private static final int DEFAULT_BUFFER_SIZE = 2048;
	private File file;
	private UploadCallbacks listener;
	private String content_type;
	
	public ProgressRequestBody(final File file, String content_type, final UploadCallbacks listener) {
		this.content_type = content_type;
		this.file = file;
		this.listener = listener;
	}
	
	@Override
	public long contentLength() {
		return file.length();
	}
	
	@Override
	public MediaType contentType() {
		return MediaType.parse(content_type + "/*");
	}
	
	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		long fileLength = file.length();
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		try (FileInputStream in = new FileInputStream(file)) {
			long uploaded = 0;
			int read;
			Handler handler = new Handler(Looper.getMainLooper());
			while ((read = in.read(buffer)) != -1) {
				handler.post(new ProgressUpdater(uploaded, fileLength));
				uploaded += read;
				sink.write(buffer, 0, read);
			}
		}
	}
	
	public interface UploadCallbacks {
		void onProgressUpdate(int percentage);
	}
	
	private class ProgressUpdater implements Runnable {
		private long uploaded;
		private long total;
		
		public ProgressUpdater(long uploaded, long total) {
			this.uploaded = uploaded;
			this.total = total;
		}
		
		@Override
		public void run() {
			listener.onProgressUpdate((int) (100 * uploaded / total));
		}
	}
}
