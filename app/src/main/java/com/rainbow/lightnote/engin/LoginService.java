package com.rainbow.lightnote.engin;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.dd.CircularProgressButton;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.rainbow.lightnote.ui.activity.MainActivity;


public class LoginService {

	private static final String TAG = "LoginService";

	public static void login(final Context context,final CircularProgressButton Log_button,String username,String password){
		String path = "http://192.168.155.2:8080/wustassistance/servlet/PhoneLoginAction";
		boolean result;
		RequestParams params = new RequestParams();
		params.addHeader("header", "header");
		params.addQueryStringParameter("username", username);
		params.addQueryStringParameter("password", password);

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
						//////////////////////////////////测试
						Intent it=new Intent(context,MainActivity.class);
						context.startActivity(it);
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
						if(responseInfo.result.equals("登录成功")){
							simulateSuccessProgress(Log_button);
							Intent it=new Intent(context,MainActivity.class);
							context.startActivity(it);

						}else{
							simulateErrorProgress(Log_button);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					//	simulateErrorProgress(Log_button);
					}
				});
	}
	private static void simulateSuccessProgress(final CircularProgressButton button) {
		ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
		widthAnimation.setDuration(1500);
		widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Integer value = (Integer) animation.getAnimatedValue();
				button.setProgress(value);
			}
		});
		widthAnimation.start();
	}

	private static void simulateErrorProgress(final CircularProgressButton button) {
		ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
		widthAnimation.setDuration(1500);
		widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Integer value = (Integer) animation.getAnimatedValue();
				button.setProgress(value);
				if (value == 99) {
					button.setProgress(-1);
				}
			}
		});
		widthAnimation.start();
	}
}
