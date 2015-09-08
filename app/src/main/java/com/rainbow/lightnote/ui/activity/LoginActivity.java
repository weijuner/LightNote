package com.rainbow.lightnote.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.rainbow.lightnote.R;

import java.util.ArrayList;

public class LoginActivity extends Activity {
	private TextView log, regeist;
	private ViewPager viewPager;
	private View LOG, REGEIST;
	private LayoutInflater inflater;
	private ArrayList<View> Views = new ArrayList<View>();
	private Button Log_button, Regeist_button;
	private EditText Log_Username_edit, Log_Pwd_edit, Regeist_Username_edit,
			Regeist_Pwd_edit;
	private CheckBox Log_checkbox, Regeit_checkbox;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
	}

	void initView() {
		inflater = getLayoutInflater();
		LOG = inflater.inflate(R.layout.view_login, null);
		REGEIST = inflater.inflate(R.layout.view_regeist, null);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		Views.add(LOG);
		Views.add(REGEIST);
		viewPager.setAdapter(new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				super.destroyItem(container, position, object);
				((ViewPager) container).removeView(Views.get(position));

			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				((ViewPager) container).addView(Views.get(position));
				return Views.get(position);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return Views.size();
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 0) {
					regeist.setTextColor(Color.WHITE);
					regeist.setTextSize(20);
					log.setTextSize(30);

				} else {
					log.setTextColor(Color.WHITE);
					log.setTextSize(20);
					regeist.setTextSize(30);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		log = (TextView) findViewById(R.id.log);
		regeist = (TextView) findViewById(R.id.regeist);

		log.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(0);
			}
		});

		regeist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(1);
			}
		});

		Log_button = (Button) LOG.findViewById(R.id.log_button);
		Regeist_button = (Button) REGEIST.findViewById(R.id.regeist_button);
		Log_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(it);

			}
		});
		Regeist_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		
		Log_Username_edit=(EditText) LOG.findViewById(R.id.log_username_edit);
		Log_Pwd_edit=(EditText) LOG.findViewById(R.id.log_pwd_edit);
		Log_checkbox=(CheckBox) LOG.findViewById(R.id.log_showpwd_checkbox);
		Log_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					Log_Pwd_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else
				{
					Log_Pwd_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});
		
		Regeist_Username_edit=(EditText) REGEIST.findViewById(R.id.regeist_username_edit);
		Regeist_Pwd_edit=(EditText) REGEIST.findViewById(R.id.regeist_pwd_edit);
		Regeit_checkbox=(CheckBox) REGEIST.findViewById(R.id.regeist_showpwd_checkbox);
		Regeit_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					Regeist_Pwd_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else
				{
					Regeist_Pwd_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});
	}
}
