package com.rainbow.lightnote.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LogRequestForUser
{
	private HttpURLConnection conn;
	public String Msg="";
	public InputStream IS;
	
	public String LogIn(String httpUrl,String username,String pwd) {
		conn = null;
		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url != null) {
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000); //
				conn.setRequestMethod("POST"); //
				conn.setDoOutput(true); //
				conn.setDoInput(true); //
				conn.setUseCaches(false); // ��ʹ�û���
				conn.setRequestProperty("Charset", "UTF-8"); //
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					String User= "username="+ URLEncoder.encode(username, "utf-8");
					String Pwd="password="+URLEncoder.encode(pwd, "utf-8");
					
					byte[] data = User.getBytes();
					
					DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
					
					outputStream.write(data);
					outputStream.write("&".getBytes());
					outputStream.write(Pwd.getBytes());
					
					
					outputStream.flush(); //
					outputStream.close(); //
					//
					IS = conn.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(IS));
					String line = null;
					//
					while ((line = reader.readLine()) != null) {
						Msg += line + "\n";
					}
					reader.close(); //
				} else {
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Msg;
	}

}
