package com.matji.sandwich.http.request;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;
import android.webkit.MimeTypeMap;

import com.matji.sandwich.util.ImageUtil;
import com.matji.sandwich.listener.ProgressListener;

/**
 * @author meinside@skcomms.co.kr
 * @since 09.10.05.
 * 
 * last update 10.01.25.
 */
final public class HttpUtility
{
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_FAIL = 0;
    public static final int HTTP_STATUS_NOCONTENT = 204;
    public static final int HTTP_STATUS_FOUND = 302; // 서버로 부터의 redirection 요청, 정상일 수도 아닐 수도 있다.
    public static final int HTTP_STATUS_UNUSED = 306; // 모바일싸이월드, 블로그 등 각각의 서비스 인터페이스에서 정기점검, 임시점검 등 발생 시
    public static final int HTTP_STATUS_BAD_REQUEST = 400; // 파라미터 오류 등, 요청이 잘못됐을 때
    public static final int HTTP_STATUS_LOGIN_FAIL = 401; // 로그인 실패
    public static final int HTTP_STATUS_FORBIDDEN = 403; // 권한 오류 (게시물 공개여부, 댓글 권한 등등)
    public static final int HTTP_STATUS_NOT_ACCEPTABLE = 406; // 클라이언트 파일 받기 오류
    public static final int HTTP_STATUS_CONFLICT = 409; // spam 
    public static final int HTTP_STATUS_SERVER_BUSY = 410; // 중복 (이미 존재하는 값에 대한 생성 요청 등등 시에 발생)
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500 ; // 서버 오류 - 내부 오류 또는 유/무선 인터페이스에서 알 수 없는 오류 발생
    public static final int HTTP_STATUS_INVALID_DATA = 501;
    public static final int HTTP_STATUS_SERVICE_UNAVAILABLE = 503 ; // 게이트웨이 서버측 정기점검
    public static final int HTTP_STATUS_SERVER_BUSY_SUCESS = 411; // 중복 (이미 존재하는 값에 대한 생성 요청 등등 시에 발생)

	
    public static final int MAX_BUFFER_SIZE = 128 * 1024;	//4MB
    public static final int FILE_BUFFER_SIZE = 8 * 1024;
    public static final int READ_BUFFER_SIZE = 8 * 1024;

    private volatile static HttpUtility httpUtility = null;
    private ProgressListener progressListener;
    private int progressTag;
    
    //default connection values
    private static int connectionTimeoutMillis = 10000;
    private static int readTimeoutMillis = 5000;
    private static boolean useCaches = false;

    public static final int ASYNC_METHOD_GET = 1;
    public static final int ASYNC_METHOD_POST = 1 << 1;
    public static final int ASYNC_METHOD_POSTBYTES = 1 << 2;

    public static final String ASYNC_RESULT_BUNDLE_KEY_HTTP_STATUS = "status";
    public static final String ASYNC_RESULT_BUNDLE_KEY_HTTP_CONTENTTYPE = "type";
    public static final String ASYNC_RESULT_BUNDLE_KEY_HTTP_BODY = "body";
	
    public static final String BASE_URL = "http://cyphone.nate.com/";

    private HashMap<HttpRequest, HttpURLConnection> connectionPool;
	
    public static int convertFoundToOk(int http_status) {
	return ((http_status == HTTP_STATUS_FOUND)? HTTP_STATUS_OK : http_status);
    }
    /**
     * 
     */
    private HttpUtility() {
	connectionPool = new HashMap<HttpRequest, HttpURLConnection>();
	System.setProperty("http.keepAlive", "false");
    }
	
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
			    }
		    }
	    }
		
	return httpUtility;
    }

    public synchronized void setProgressListener(int progressTag, ProgressListener listener) {
	this.progressTag = progressTag;
	progressListener = listener;
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

    public void disconnectConnection(HttpRequest request) {
	HttpURLConnection connection = connectionPool.get(request);
	if (connection != null) {
	    // Log.d("=====", "disconnect!!");
	    connection.disconnect();
	    connectionPool.remove(request);
	}
    }

    /**
     * 
     * @param url
     * @param headerValues
     * @param getParameters
     * @return
     */
    public SimpleHttpResponse get(String url, Map<String, String> headerValues, Map<String, String> getParameters, HttpRequest request)
    {
	// Log.d("=====", "=====================================");
    	//open connection
    	HttpURLConnection connection = null;
    	try
    	    {
    		// Log.d("=====", "1");
    		connection = (HttpURLConnection)new URL(getUrlStringWithQuery(url, getParameters)).openConnection();
		Log.d("Matji", getUrlStringWithQuery(url, getParameters));

    		connection.setRequestProperty("Connection","close");
    		connection.setRequestMethod("GET");
    		connection.setDoInput(true);
    		connection.setDoOutput(false);

    		connection.setUseCaches(getUseCaches());
    		connection.setConnectTimeout(getConnectionTimeout());
    		connection.setReadTimeout(getReadTimeout());
    		connectionPool.put(request, connection);
    	    }
    	catch(Exception e)
    	    {
    		// Log.d("=====", "2");
    		Log.e("HttpUtility.get", e.toString());
    		if(connection != null)
    		    disconnectConnection(request);
    		return null;
    	    }

    	// Log.d("=====", "3");
    	//set header
    	if(headerValues != null) {
    	    // Log.d("=====", "4");
    	    for(String key: headerValues.keySet())
    		connection.setRequestProperty(key, headerValues.get(key));
    	}

    	//get response
    	try
    	    {
    		// Log.d("=====", "5");
		
    		// Log.d("=====", "5-1");
    		// BufferedReader dis = new BufferedReader(new
    		// InputStreamReader(conn.getInputStream()));
		long start = System.currentTimeMillis();
    		InputStream is = new BufferedInputStream(connection.getInputStream());
    		// Log.d("=====", "5-2");
    		byte[] responseBody = readBytesFromInputStream(is);
		long timeGap = System.currentTimeMillis() - start;
		// Log.d("=====", "get   : " + timeGap);
		
    		// Log.d("=====", "5-3");
    		// is.close();
    		// Log.d("=====", "5-4");
    		SimpleHttpResponse result = new SimpleHttpResponse(connection.getResponseCode(), responseBody, connection.getHeaderFields());
    		// Log.d("=====", "5-5");
		
    		disconnectConnection(request);
    		return result;
    	    }
    	catch(SocketException e) {
    	    // Log.d("=====", "exception");
    	    return null;
    	}
    	catch(Exception e)
    	    {
    		// Log.d("=====", "6");
    		Log.e("HttpUtility.get", e.toString());
    		try
    		    {
    			// Log.d("=====", "7");
    			int responseCode = connection.getResponseCode();
    			if(responseCode != -1) {
    			    // Log.d("=====", "8");
    			    SimpleHttpResponse result = new SimpleHttpResponse(responseCode, connection.getResponseMessage());
    			    disconnectConnection(request);
    			    return result;
    			}
    		    }
    		catch(IOException ioe)
    		    {
    			Log.e("HttpUtility.get", ioe.toString());
    		    }
    	    }
    	finally
    	    {
    		// Log.d("=====", "9");
    		disconnectConnection(request);
    	    }
		
    	return null;
    }

    public SimpleHttpResponse post(String url, Map<String, String> headerValues, Map<String, Object> postParameters, HttpRequest request) {
	
	// return post(url, headerValues, postParameters, 0);
	// 	return post(url, headerValues, postParameters);
	// }
	/**
	 * 
	 * @param url
	 * @param headerValues
	 * @param postParameters
	 * @return
	 */
	// public SimpleHttpResponse post(String url, Map<String, String> headerValues, Map<String, Object> postParameters)
	// {
	//open connection
	HttpURLConnection connection = null;
	connectionPool.put(request, connection);
	try
	    {
		connection = (HttpURLConnection)new URL(url).openConnection();
			
		connection.setRequestMethod("POST");
		connection.setDoOutput(true); 
		connection.setDoInput(true);

		connection.setUseCaches(getUseCaches());
		connection.setConnectTimeout(getConnectionTimeout());
		connection.setReadTimeout(getReadTimeout());
		// connection.setRequestProperty("Accept", "text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// connection.setRequestProperty("Accept", "*");
		// connection.setRequestProperty("Accept-Encoding", "*");
		connectionPool.put(request, connection);
	    }
	catch(Exception e)
	    {
		Log.e("HttpUtility.post", e.toString());
		if(connection != null)
		    disconnectConnection(request);
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
						File compressedFile = ImageUtil.decodeFileToFile(file, 80, false);
						if (compressedFile != null) {
						    file = compressedFile;
						}
						
						dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + file.getName() + "\"\r\n");
						dos.writeBytes("Content-Type: " + getMimeType((File)value) + "\r\n");
						dos.writeBytes("Content-Transfer-Encoding: binary\r\n\r\n");
						
						FileInputStream fis = new FileInputStream(file);
						
						int totalSize = fis.available();
						byte[] buffer = new byte[FILE_BUFFER_SIZE];
							
						int bytesRead = 0;
						// int accumulatedBytes = 0;

						while((bytesRead = fis.read(buffer, 0, FILE_BUFFER_SIZE)) > 0)
						    {
							// accumulatedBytes += bytesRead;
							dos.write(buffer, 0, bytesRead);
							if (progressListener != null) {
							    // progressListener.onWritten(progressTag, totalSize, accumulatedBytes);
							    progressListener.onWritten(progressTag, totalSize, bytesRead);
							}
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
			disconnectConnection(request);
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
			disconnectConnection(request);
			return null;
		    }	
	    }

	//get response
	try
	    {
		InputStream is = connection.getInputStream();
		byte[] responseBody = readBytesFromInputStream(is);
		is.close();
		SimpleHttpResponse result = new SimpleHttpResponse(connection.getResponseCode(), responseBody, connection.getHeaderFields());
		disconnectConnection(request);
		return result;
	    }
	catch(Exception e)
	    {
		Log.e("HttpUtility.post", e.toString());
		try
		    {
			int responseCode = connection.getResponseCode();
			if(responseCode != -1) {
			    SimpleHttpResponse result = new SimpleHttpResponse(responseCode, connection.getResponseMessage());
			    disconnectConnection(request);
			    return result;
			}
		    }
		catch(IOException ioe)
		    {
			Log.e("HttpUtility.post", ioe.toString());
		    }
	    }
	finally
	    {
		disconnectConnection(request);
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