package com.matji.sandwich.http.request;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

// import com.matji.android.v2.common.Constants;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.matji.sandwich.FileUploadProgressListener;

/**
 * 
 * @author meinside@skcomms.co.kr
 * @since 09.10.05.
 * 
 * last update 10.01.25.
 * 
 * 
 * ----
 * * synchronous example:
 * 
 * 	SimpleHttpResponse response = HttpUtility.getInstance().get("http://test.url/test.page", null, null);
 * 	if(response != null && response.getHttpStatusCode() > 0)
 * 	{
 * 		Log.d("test", "http status = " + response.getHttpStatusCode() + ", total " + response.getHttpResponseBody().length + " bytes");
 * 	}
 * 
 * ----
 * * asynchronous example:
 * 
 * 	Button button = (Button)findViewById(R.id.button_for_test);
 * 	button.setOnClickListener(new OnClickListener(){
 * 		public void onClick(View v)
 * 		{
 * 			HttpUtility.getInstance().getAsync(new Handler(){
 * 				public void handleMessage(Message msg)
 * 				{
 * 					if(msg.arg1 > 0)
 * 						//# see: HttpUtility.AsyncHttpTask.onPostExecute
 * 						Log.d("test", "http status = " msg.arg1 + ", total " + msg.arg2 + " bytes");
 * 				}
 * 			}, "http://test.url/test.page", null, null);
 * 		}
 * 	});
 */
final public class HttpUtility
{
	public static final int HTTP_STATUS_OK = 200;
	public static final int HTTP_STATUS_FAIL = 0;
	public static final int HTTP_STATUS_NOCONTENT 				= 204;
	public static final int HTTP_STATUS_FOUND                   = 302; // 서버로 부터의 redirection 요청, 정상일 수도 아닐 수도 있다.
	public static final int HTTP_STATUS_UNUSED 					= 306; // 모바일싸이월드, 블로그 등 각각의 서비스 인터페이스에서 정기점검, 임시점검 등 발생 시
	public static final int HTTP_STATUS_BAD_REQUEST 			= 400; // 파라미터 오류 등, 요청이 잘못됐을 때
	public static final int HTTP_STATUS_LOGIN_FAIL 				= 401; // 로그인 실패
	public static final int HTTP_STATUS_FORBIDDEN 				= 403; // 권한 오류 (게시물 공개여부, 댓글 권한 등등)
	public static final int HTTP_STATUS_CONFLICT 				= 409; // spam 
	public static final int HTTP_STATUS_SERVER_BUSY 			= 410; // 중복 (이미 존재하는 값에 대한 생성 요청 등등 시에 발생)
	public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR 	= 500 ; // 서버 오류 - 내부 오류 또는 유/무선 인터페이스에서 알 수 없는 오류 발생
	public static final int HTTP_STATUS_SERVICE_UNAVAILABLE 	= 503 ; // 게이트웨이 서버측 정기점검
	public static final int HTTP_STATUS_SERVER_BUSY_SUCESS 		= 411; // 중복 (이미 존재하는 값에 대한 생성 요청 등등 시에 발생)

	
	public static final int MAX_BUFFER_SIZE = 128 * 1024;	//4MB
	public static final int FILE_BUFFER_SIZE = 8 * 1024;
	public static final int READ_BUFFER_SIZE = 8 * 1024;

	private volatile static HttpUtility httpUtility = null;
	private static HashMap<String, AsyncHttpTask> asyncTaskPool = null;
        private FileUploadProgressListener progressListener;

	//default connection values
	private static int connectionTimeoutMillis = 10000;
	private static int readTimeoutMillis = 10000;
	private static boolean useCaches = false;

	public static final int ASYNC_METHOD_GET = 1;
	public static final int ASYNC_METHOD_POST = 1 << 1;
	public static final int ASYNC_METHOD_POSTBYTES = 1 << 2;

	public static final String ASYNC_RESULT_BUNDLE_KEY_HTTP_STATUS = "status";
	public static final String ASYNC_RESULT_BUNDLE_KEY_HTTP_CONTENTTYPE = "type";
	public static final String ASYNC_RESULT_BUNDLE_KEY_HTTP_BODY = "body";
	
	public static final String BASE_URL = "http://cyphone.nate.com/";
	
	public static int convertFoundToOk(int http_status) {
		return ((http_status == HTTP_STATUS_FOUND)? HTTP_STATUS_OK : http_status);
	}
	/**
	 * 
	 */
	private HttpUtility(){}
	
	/**
	 * 
	 * @return
	 */
	public static HttpUtility getInstance()
	{
	    if(httpUtility == null)
		{
			synchronized(HttpUtility.class)
			{
				if(httpUtility == null)
				{
					httpUtility = new HttpUtility();
					asyncTaskPool = new HashMap<String, AsyncHttpTask>();
				}
			}
		}
		
		return httpUtility;
	}

        public void setFileUploadProgressListener(FileUploadProgressListener listener) {
	    progressListener = listener;
	}

        public void removeFileUploadProgressListener() {
	    progressListener = null;
	}
	
	/**
	 * 
	 * @param newConnectionTimeoutMillis
	 */
	final public static void setConnectionTimeout(int newConnectionTimeoutMillis)
	{
		connectionTimeoutMillis = newConnectionTimeoutMillis;
	}
	
	/**
	 * 
	 * @return
	 */
	final public static int getConnectionTimeout()
	{
		return connectionTimeoutMillis;
	}
	
	/**
	 * 
	 * @param newReadTimeoutMillis
	 */
	final public static void setReadTimeout(int newReadTimeoutMillis)
	{
		readTimeoutMillis = newReadTimeoutMillis;
	}
	
	/**
	 * 
	 * @return
	 */
	final public static int getReadTimeout()
	{
		return readTimeoutMillis;
	}
	
	/**
	 * 
	 * @param useCashesOrNot
	 */
	final public static void setUseCaches(boolean useCashesOrNot)
	{
		useCaches = useCashesOrNot;
	}
	
	/**
	 * 
	 * @return
	 */
	final public static boolean getUseCaches()
	{
		return useCaches;
	}
	
	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 */
	
	public static String getUrlStringWithQuery(String url, Map<String, String> parameters)
	{
		if(url == null)
			return null;
		
		//add get parameters to url
		StringBuffer urlWithQuery = new StringBuffer(url);
		if(parameters != null)
		{
			boolean endsWithQuestion = false;
			if(url.indexOf("?") == -1)
			{
				urlWithQuery.append("?");
				endsWithQuestion = true;
			}
			for(String key: parameters.keySet())
			{
				if(!endsWithQuestion)
					urlWithQuery.append("&");
				endsWithQuestion = false;

				urlWithQuery.append(urlencode(key));
				urlWithQuery.append("=");
				urlWithQuery.append(urlencode(parameters.get(key)));
			}
		}
		
		// Log.d(Constants.DEBUG_TAG,urlWithQuery.toString());
		return urlWithQuery.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @param headerValues
	 * @param getParameters
	 * @return
	 */
	public SimpleHttpResponse get(String url, Map<String, String> headerValues, Map<String, String> getParameters)
	{
		//open connection
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection)new URL(getUrlStringWithQuery(url, getParameters)).openConnection();

			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(false);

			connection.setUseCaches(getUseCaches());
			connection.setConnectTimeout(getConnectionTimeout());
			connection.setReadTimeout(getReadTimeout());
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.get", e.toString());
			if(connection != null)
				connection.disconnect();
			return null;
		}

		//set header
		if(headerValues != null)
			for(String key: headerValues.keySet())
				connection.setRequestProperty(key, headerValues.get(key));

		//get response
		try
		{
			InputStream is = connection.getInputStream();
			byte[] responseBody = readBytesFromInputStream(is);
			is.close();
			return new SimpleHttpResponse(connection.getResponseCode(), responseBody, connection.getHeaderFields());
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.get", e.toString());
			try
			{
				int responseCode = connection.getResponseCode();
				if(responseCode != -1)
					return new SimpleHttpResponse(responseCode, connection.getResponseMessage());
			}
			catch(IOException ioe)
			{
				Log.e("HttpUtility.get", ioe.toString());
			}
		}
		finally
		{
			connection.disconnect();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param url
	 * @param headerValues
	 * @param getParameters
	 * @return
	 */
	public SimpleHttpResponse delete(String url, Map<String, String> headerValues, Map<String, String> getParameters)
	{
		//open connection
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection)new URL(getUrlStringWithQuery(url, getParameters)).openConnection();

			connection.setRequestMethod("DELETE");
			connection.setDoInput(true);
			connection.setDoOutput(false);

			connection.setUseCaches(getUseCaches());
			connection.setConnectTimeout(getConnectionTimeout());
			connection.setReadTimeout(getReadTimeout());
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.delete", e.toString());
			if(connection != null)
				connection.disconnect();
			return null;
		}

		//set header
		if(headerValues != null)
			for(String key: headerValues.keySet())
				connection.setRequestProperty(key, headerValues.get(key));

		//get response
		try
		{
			InputStream is = connection.getInputStream();
			byte[] responseBody = readBytesFromInputStream(is);
			is.close();
			return new SimpleHttpResponse(connection.getResponseCode(), responseBody, connection.getHeaderFields());
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.delete", e.toString());
			try
			{
				int responseCode = connection.getResponseCode();
				if(responseCode != -1)
					return new SimpleHttpResponse(responseCode, connection.getResponseMessage());
			}
			catch(IOException ioe)
			{
				Log.e("HttpUtility.delete", ioe.toString());
			}
		}
		finally
		{
			connection.disconnect();
		}
		
		return null;
	}

    public SimpleHttpResponse post(String url, Map<String, String> headerValues, Map<String, Object> postParameters) {
	return post(url, headerValues, postParameters, 0);
    }
	/**
	 * 
	 * @param url
	 * @param headerValues
	 * @param postParameters
	 * @return
	 */
    public SimpleHttpResponse post(String url, Map<String, String> headerValues, Map<String, Object> postParameters, int tag)
	{
		//open connection
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection)new URL(url).openConnection();
			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true); 
			connection.setDoInput(true);

			connection.setUseCaches(getUseCaches());
			connection.setConnectTimeout(getConnectionTimeout());
			connection.setReadTimeout(getReadTimeout());
			connection.setRequestProperty("Accept", "text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.post", e.toString());
			if(connection != null)
				connection.disconnect();
			return null;
		}

		//set header
		if(headerValues != null)
			for(String key: headerValues.keySet())
				connection.setRequestProperty(key, headerValues.get(key));

		//set post parameters
		if(postParameters != null)
		{				
			//check file existence
			boolean fileExists = false;
			for(String key: postParameters.keySet())
			{
				if(postParameters.get(key).getClass() == (Object)File.class)
				{
					fileExists = true;
					break;
				}
			}

			try
			{
				DataOutputStream dos = null;
				if(fileExists)
				{
					String boundary = "01asfasf290481209";
					String startBoundary = "--" + boundary + "\r\n";
					String endBoundary = "\r\n";
					String finalEndBoundary = "\r\n--" + boundary + "--\r\n";
					
					
					connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
 					
 					
					dos = new DataOutputStream(connection.getOutputStream());
	
					for(String key: postParameters.keySet())
					{

						//dos.writeBytes("\r\n");
						dos.writeBytes(startBoundary);
						
						Object value = postParameters.get(key);
						if(value.getClass() == File.class)
						{
							
							// File send
							File file = (File)value;							
							dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + file.getName() + "\"\r\n");
							dos.writeBytes("Content-Type: " + getMimeType(file) + "\r\n");
							dos.writeBytes("Content-Transfer-Encoding: binary\r\n\r\n");
							
							FileInputStream fis = new FileInputStream((File)value);
							
							int totalSize = fis.available();
							byte[] buffer = new byte[FILE_BUFFER_SIZE];
							
							int bytesRead = 0;
							while((bytesRead = fis.read(buffer, 0, FILE_BUFFER_SIZE)) > 0)
							{
								dos.write(buffer, 0, bytesRead);
								if (progressListener != null)
								    progressListener.onFileWritten(tag, totalSize, bytesRead);
							}
							fis.close();
						}
						else
						{
							// String data send
							dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
							dos.writeBytes(value.toString());
						}
						
						dos.writeBytes(endBoundary);
						
					}
					
					dos.writeBytes(finalEndBoundary);
					
				}
				else
				{

					connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					
					dos = new DataOutputStream(connection.getOutputStream());
					
					StringBuffer buffer = new StringBuffer();
					for(String key: postParameters.keySet())
					{
						if(buffer.length() > 0)
							buffer.append("&");
						buffer.append(urlencode(key));
						buffer.append("=");
						buffer.append(urlencode(postParameters.get(key).toString()));
					}
					
					dos.writeBytes(buffer.toString());
				}
				
				
				dos.flush();
				dos.close();
			}
			catch(Exception e)
			{
				Log.d("Test", "post error " + e.toString());
				Log.e("HttpUtility.post", e.toString());
				connection.disconnect();
				return null;
			}
		}
		else
		{			
			// By cozyme
			// [Error]
			// setRequestProperty("Content-Length", "0")이 동작 안함.
			// HTTP STATUS 411 에러가 발생함. (Android 1.6만의 버그인지는 모르겠음.)
			//
			// [Workaround]
			// 빈 OutputStream을 만들어 Content-Length가 0임을 알림.			
			DataOutputStream dos = null;
			try
			{
				dos = new DataOutputStream(connection.getOutputStream());
				dos.flush();
				dos.close();
			}
			catch(Exception e)
			{
				Log.e("HttpUtility.post", e.toString());
				connection.disconnect();
				return null;
			}	
		}

		//get response
		try
		{
			InputStream is = connection.getInputStream();
			byte[] responseBody = readBytesFromInputStream(is);
			is.close();
			return new SimpleHttpResponse(connection.getResponseCode(), responseBody, connection.getHeaderFields());
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.post", e.toString());
			try
			{
				int responseCode = connection.getResponseCode();
				if(responseCode != -1)
					return new SimpleHttpResponse(responseCode, connection.getResponseMessage());
			}
			catch(IOException ioe)
			{
				Log.e("HttpUtility.post", ioe.toString());
			}
		}
		finally
		{
			connection.disconnect();
		}
		return null;
	}
	
	/**
	 * 
	 * @param url
	 * @param headerValues
	 * @param bytes
	 * @param contentType
	 * @return
	 */
	public SimpleHttpResponse postBytes(String url, Map<String, String> headerValues, byte[] bytes, String contentType)
	{
		//open connection
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection)new URL(url).openConnection();
			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			connection.setUseCaches(getUseCaches());
			connection.setConnectTimeout(getConnectionTimeout());
			connection.setReadTimeout(getReadTimeout());
			
			connection.setRequestProperty("Content-Type", contentType);
			connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.postBytes", e.toString());
			if(connection != null)
				connection.disconnect();
			return null;
		}

		//set header
		if(headerValues != null)
			for(String key: headerValues.keySet())
				connection.setRequestProperty(key, headerValues.get(key));

		//write post data
		try
		{
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			dos.write(bytes);
			dos.flush();
			dos.close();
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.postBytes", e.toString());
			connection.disconnect();
			return null;
		}

		//get response
		try
		{
			InputStream is = connection.getInputStream();
			byte[] responseBody = readBytesFromInputStream(is);
			is.close();
			return new SimpleHttpResponse(connection.getResponseCode(), responseBody, connection.getHeaderFields());
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.postBytes", e.toString());
			try
			{
				int responseCode = connection.getResponseCode();
				if(responseCode != -1)
					return new SimpleHttpResponse(responseCode, connection.getResponseMessage());
			}
			catch(IOException ioe)
			{
				Log.e("HttpUtility.postBytes", ioe.toString());
			}
		}
		finally
		{
			connection.disconnect();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param resultHandler that will handle result message
	 * @param url
	 * @param headerValues
	 * @param getParameters
	 * @return id of AsyncHttpTask that is assigned to this GET job
	 */
	public String getAsync(Handler resultHandler, String url, Map<String, String> headerValues, Map<String, String> getParameters)
	{
		AsyncHttpTask task = new AsyncHttpTask();
		task.execute(ASYNC_METHOD_GET, resultHandler, url, headerValues, getParameters);
		return task.getId();
	}
	
	/**
	 * 
	 * @param resultHandler that will handle result message
	 * @param url
	 * @param headerValues
	 * @param postParameters
	 * @return id of AsyncHttpTask that is assigned to this POST job
	 */
	public String postAsync(Handler resultHandler, String url, Map<String, String> headerValues, Map<String, Object> postParameters)
	{
		AsyncHttpTask task = new AsyncHttpTask();
		task.execute(ASYNC_METHOD_POST, resultHandler, url, headerValues, postParameters);
		return task.getId();
	}

	/**
	 * 
	 * @param resultHandler that will handle result message
	 * @param url
	 * @param headerValues
	 * @param bytes
	 * @param contentType
	 * @return id of AsyncHttpTask that is assigned to this POST job
	 */
	public String postBytesAsync(Handler resultHandler, String url, Map<String, String> headerValues, byte[] bytes, String contentType)
	{
		AsyncHttpTask task = new AsyncHttpTask();
		task.execute(ASYNC_METHOD_POSTBYTES, resultHandler, url, headerValues, bytes, contentType);
		return task.getId();
	}
	
	/**
	 * Read up bytes from given InputStream instance and return
	 * 
	 * @param is (given InputStream instance is not closed by this function)
	 * @return
	 */
	private byte[] readBytesFromInputStream(InputStream is)
	{
		try
		{
			ByteArrayBuffer buffer = new ByteArrayBuffer(MAX_BUFFER_SIZE);
			byte[] bytes = new byte[READ_BUFFER_SIZE];
			int bytesRead, startPos, length;
			boolean firstRead = true;
			while((bytesRead = is.read(bytes, 0, READ_BUFFER_SIZE)) > 0)
			{
				startPos = 0;
				length = bytesRead;
				if(firstRead)
				{
					//remove first occurrence of '0xEF 0xBB 0xBF' (UTF-8 BOM)
					if(bytesRead >= 3 && (bytes[0] & 0xFF) == 0xEF && (bytes[1] & 0xFF) == 0xBB && (bytes[2] & 0xFF) == 0xBF)
					{
						startPos += 3;
						length -= 3;
					}
					firstRead = false;
				}
				buffer.append(bytes, startPos, length);
			}
			return buffer.toByteArray();
		}
		catch(Exception e)
		{
			Log.e("HttpUtility.readBytesFromInputStream", e.toString());
		}
		
		return null;
	}
	
	/**
	 * return mime type of given file
	 * @param file
	 * @return when mime type is unknown, it simply returns "application/octet-stream"
	 */
	final public static String getMimeType(File file)
	{
		String mimeType = null;
		try
		{
			mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.getCanonicalPath()));
		}
		catch(IOException e)
		{
			Log.e("HttpUtility.getMimeType", e.toString());
		}
		if(mimeType == null)
			mimeType = "application/octet-stream";
		
		return mimeType;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	final public AsyncHttpTask getAsyncHttpTask(String id)
	{
		return asyncTaskPool.get(id);
	}

	/**
	 * 
	 * @author meinside@skcomms.co.kr
	 * @since 09.10.08.
	 * 
	 * last update 10.01.22.
	 *
	 */
	final public class SimpleHttpResponse
	{
		private int httpStatusCode;
		private byte[] httpResponseBody;
		private Map<String, List<String>> httpHeaders = null;
		
		/**
		 * 
		 * @param httpStatusCode
		 * @param httpResponseBody
		 * @param httpHeaders
		 */
		public SimpleHttpResponse(int httpStatusCode, byte[] httpResponseBody, Map<String, List<String>> httpHeaders)
		{
			this(httpStatusCode, httpResponseBody);
			
			if(httpHeaders != null)
				this.httpHeaders = httpHeaders;
		}
		
		/**
		 * 
		 * @param httpStatusCode
		 * @param httpResponseBody
		 */
		public SimpleHttpResponse(int httpStatusCode, byte[] httpResponseBody)
		{
			this.httpStatusCode = httpStatusCode;
			this.httpResponseBody = httpResponseBody;
		}
		
		/**
		 * 
		 * @param httpStatusCode
		 * @param httpResponseBody
		 * @param contentTypes
		 */
		public SimpleHttpResponse(int httpStatusCode, String httpResponseBody, Map<String, List<String>> httpHeaders)
		{
			this(httpStatusCode, httpResponseBody);
			
			if(httpHeaders != null)
				this.httpHeaders = httpHeaders;
		}
		
		/**
		 * 
		 * @param httpStatusCode
		 * @param httpResponseBody
		 */
		public SimpleHttpResponse(int httpStatusCode, String httpResponseBody)
		{
			this.httpStatusCode = httpStatusCode;
			this.httpResponseBody = httpResponseBody.getBytes();
		}

		/**
		 * 
		 * @return
		 */
		public int getHttpStatusCode()
	    {
	    	return httpStatusCode;
	    }
		
		/**
		 * 
		 * @return
		 */
		public byte[] getHttpResponseBody()
		{
			return httpResponseBody;
		}

		/**
		 * 
		 * @return
		 */
		public String getHttpResponseBodyAsString()
	    {
	    	return new String(httpResponseBody);
	    }
		
		/**
		 * 
		 * @return
		 */
		public ByteArrayInputStream getHttpResponseBodyAsInputStream()
		{
			return new ByteArrayInputStream(httpResponseBody);
		}
		
		/**
		 * 
		 * @return
		 */
		public Map<String, List<String>> getHeaders()
		{
			return httpHeaders;
		}
		
		/**
		 * 
		 * @param key
		 * @return
		 */
		public String getHeaderForKey(String key)
		{
			if(httpHeaders == null)
				return null;
			
			List<String> values = httpHeaders.get(key);
			if(values == null)
				values = httpHeaders.get(key.toLowerCase());	//check once more with lower case key
			if(values == null)
				return null;
			
			StringBuffer buffer = new StringBuffer();
			for(String value: values)
			{
				if(buffer.length() > 0)
					buffer.append(";");
				buffer.append(value);
			}
			
			return buffer.toString();
		}
	}

	/**
	 * 
	 * AsyncTask for executing HTTP methods
	 * (result Bundle object will carry http status code, and body content)
	 * 
	 * @author meinside@skcomms.co.kr
	 * @since 10.01.15.
	 * 
	 * last update 10.01.18.
	 *
	 */
	public class AsyncHttpTask extends AsyncTask<Object, Void, SimpleHttpResponse>
	{
		private String _id;
		private Handler resultHandler;
		
		public AsyncHttpTask()
		{
			_id = new StringBuffer().append(System.currentTimeMillis()).append("_").append(this.hashCode()).toString();
		}
		
		/**
		 * 
		 * @return
		 */
		public String getId()
		{
			return _id;
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			
			//add to pool
			asyncTaskPool.put(_id, this);
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(SimpleHttpResponse result)
		{
			super.onPostExecute(result);

			Message msg = Message.obtain();
			if(result != null)
			{
				msg.arg1 = result.getHttpStatusCode();
				msg.arg2 = result.getHttpResponseBody().length;

				Bundle data = new Bundle();
				data.putInt(ASYNC_RESULT_BUNDLE_KEY_HTTP_STATUS, msg.arg1);
				data.putString(ASYNC_RESULT_BUNDLE_KEY_HTTP_CONTENTTYPE, result.getHeaderForKey("Content-Type"));
				data.putByteArray(ASYNC_RESULT_BUNDLE_KEY_HTTP_BODY, result.getHttpResponseBody());
				msg.setData(data);
			}
			else
			{
				msg.arg1 = -1;
				msg.arg2 = -1;
			}
			resultHandler.dispatchMessage(msg);

			//remove from pool
			asyncTaskPool.remove(_id);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected SimpleHttpResponse doInBackground(Object... params)
		{
			resultHandler = (Handler)params[1];
			SimpleHttpResponse response = null;
			switch(((Integer)params[0]).intValue())
			{
			case ASYNC_METHOD_GET:
				response = HttpUtility.this.get((String)params[2], (Map<String, String>)params[3], (Map<String, String>)params[4]);
				break;
			case ASYNC_METHOD_POST:
			    response = HttpUtility.this.post((String)params[2], (Map<String, String>)params[3], (Map<String, Object>)params[4]);
				break;
			case ASYNC_METHOD_POSTBYTES:
				response = HttpUtility.this.postBytes((String)params[2], (Map<String, String>)params[3], (byte[])params[4], (String)params[5]);
				break;
			}
			return response;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled()
		{
			//remove from pool
			asyncTaskPool.remove(_id);

			super.onCancelled();
		}
	}
	
	/**
	 * 
	 * @param original
	 * @return null if fails
	 */
	public static String urlencode(String original)
	{
		try
		{
			//return URLEncoder.encode(original, "utf-8");
			//fixed: to comply with RFC-3986
			return URLEncoder.encode(original, "utf-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		}
		catch(UnsupportedEncodingException e)
		{
			Log.e("HttpUtility.urlencode", e.toString());
		}
		return null;
	}
	
	/**
	 * 
	 * @param encoded
	 * @return null if fails
	 */
	public static String urldecode(String encoded)
	{
		try
		{
			return URLDecoder.decode(encoded, "utf-8");
		}
		catch(UnsupportedEncodingException e)
		{
			Log.e("HttpUtility.urldecode", e.toString());
		}
		return null;
	}
}