package com.example.barclaysfresh;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class HttpUtility {
	
	public static int getHttpPostResponse(String serverBaseUrl,String operation,String data) throws Exception {
		URL url;
		
		
		HttpURLConnection connection = null;
		JSONObject jsonObject;
		try{

			String targetURL = serverBaseUrl + operation;
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(data.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(35000);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			List<String> resplist = new ArrayList<String>();
			String line;
			while ((line = rd.readLine()) != null) {

				resplist.add(line);
			}
			rd.close();


			JSONParser parser = new JSONParser();
			Object obj = parser.parse(resplist.get(0));

			jsonObject = (JSONObject) obj;
		} catch(SocketTimeoutException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getLocalizedMessage());
		}
		finally{
			connection.disconnect();
		}
		if (jsonObject.get("result").equals("Success")) {


			long longindex = (Long) jsonObject.get("Id");
			int index = (int) longindex;

			return index;
		}
		else if(jsonObject.get("result").equals("Error")) {

			long longcode = (Long) jsonObject.get("code");
			int code = (int) longcode;
			System.out.println("Error code : " + code);
			System.out.println("Error message : " + jsonObject.get("message"));
			throw new Exception((String) jsonObject.get("message"));
		}
		else{
			throw new IllegalStateException("Recived Json Data Not in Correct format");
		}

	}

	public static Object getHttpPostResponseInObject(String serverBaseUrl,String operation,String data,String secret_key) throws Exception {
		URL url;
		HttpURLConnection connection = null;
		try{

			String targetURL = serverBaseUrl + operation;
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");


			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(data.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(35000);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			List<String> resplist = new ArrayList<String>();
			String line;
			while ((line = rd.readLine()) != null) {

				resplist.add(line);
			}
			rd.close();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(resplist.get(0));


			return obj;  


		} catch(SocketTimeoutException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally{
			connection.disconnect();
		}
	}

	public static Object getHttpGetResponse(String serverBaseUrl,String webPage){
		HttpURLConnection urlConnection =null;

		try{

			URL url1 = new URL(serverBaseUrl+webPage);
			String protocol = url1.getProtocol();
			String host =url1.getHost();
			int port = url1.getPort();
			if((port == -1) && (host.contains(":"))) {
				StringTokenizer st2 = new StringTokenizer(host,":");
				host = st2.nextElement().toString();
				port = Integer.parseInt(st2.nextElement().toString());
			}
			String path = url1.getFile();

			URL url = new URL(protocol,host,port,path);
//			System.out.println("The url is"+url);

			urlConnection = (HttpURLConnection)url.openConnection();


			urlConnection.setConnectTimeout(35000);
			urlConnection.setReadTimeout(35000);
			urlConnection.setInstanceFollowRedirects(true);

			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			System.out.println("before dispatch" + result);

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(result);

			is.close();
			isr.close();
			return obj;

		} catch(SocketTimeoutException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ParseException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		finally {
			urlConnection.disconnect();
		}
	}

}
