package com.rainbow.lightnote.engin;


import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.rainbow.lightnote.model.Note;


public class NoteService {

	private static final String TAG = "LoginService";

	public static void addNote(final Context context,Note note){
		String path = "http://192.168.191.1:8080/wustassistance/servlet/PhoneLoginAction";
		boolean result;
		RequestParams params = new RequestParams();
		params.addHeader("header", "header");
		params.addQueryStringParameter("userid", note.getUserId() + "");
		params.addQueryStringParameter("category", note.getCategory());
		params.addQueryStringParameter("title", note.getTitle());
		params.addQueryStringParameter("content", note.getContent());
		params.addQueryStringParameter("time", note.getTime());

		// 只包含字符串参数时默认使用BodyParamsEntity，
		// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
		params.addBodyParameter("name", "value");

		// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
		// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
		// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
		// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
		// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
//		params.addBodyParameter("file", new File("path"));

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				path,
				params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {

					}

					@Override
					public void onLoading(long total, long current, boolean isUploading) {
						if (isUploading) {
						//	int progress = (int)(current/total)*100;
						} else {

						}
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

}
