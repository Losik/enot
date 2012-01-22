/*
 * ManualActivity класс
 * 
 * Запуск процесса учебника
 * 
 * Copyright 2012 hexonxons
 * 
 * :mailto killgamesh666@gmail.com
 * 
 */

package app.tascact.manual.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import app.tascact.manual.CResources;
import app.tascact.manual.TaskActivity;
import app.tascact.manual.view.ManualControlView;
import app.tascact.manual.view.ManualView;

public class ManualActivity extends Activity
{
	// View раскладки элементов
	private LinearLayout mMainLayout = null;
	// View страниц учебника
	private ManualView mManualView = null;
	// View элемента управления
	private ManualControlView mControl = null;
	// Номер страницы, которую отображаем
	private int mPageToDisplay = 0;
	// Ресурсы для построения учебника
	private CResources mResources = new CResources();
	// Время предыдущего касания
	private long mPrevTouchTime = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mMainLayout = new LinearLayout(this);
		mManualView = new ManualView(this, mClickListener);
		mControl = new ManualControlView(this);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Ориентируем View вертикально
		mMainLayout.setOrientation(1);
		// Добавляем View страницы учебника и View управления
		mMainLayout.addView(mManualView, new LayoutParams(LayoutParams.MATCH_PARENT, 1040));
		mMainLayout.addView(mControl, new LayoutParams(LayoutParams.MATCH_PARENT, 167));
		
		// Задаем обработчики касаний 
		mControl.mNextButton.setOnTouchListener(mNextTouchListener);
		mControl.mPrevButton.setOnTouchListener(mPrevTouchListener);
		mControl.mContentsButton.setOnTouchListener(mContentsTouchListener);
		
		// Загружаем настройки
		LoadPreferences();
		// Задаем отображаемую страницу
		mManualView.SetPage(mPageToDisplay);
		// Отображаем
		setContentView(mMainLayout);
	}
	
	private OnTouchListener mNextTouchListener = new OnTouchListener()
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if(event.getEventTime() - mPrevTouchTime > 250)
		    {
				mPrevTouchTime = event.getEventTime();
				mPageToDisplay++;
				if(mPageToDisplay >= mResources.TotalPages)
					mPageToDisplay = mResources.TotalPages - 1;
				
				SavePreferences();
				mManualView.SetPage(mPageToDisplay);
				mManualView.invalidate();
		    }
			return true;
		}
	};
	
	private OnTouchListener mPrevTouchListener = new OnTouchListener()
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if(event.getEventTime() - mPrevTouchTime > 250)
		    {
				mPrevTouchTime = event.getEventTime();
				mPageToDisplay--;
				if(mPageToDisplay < 0)
					mPageToDisplay = 0;
				
				SavePreferences();
				mManualView.SetPage(mPageToDisplay);
		    }
			return true;
		}
	};
	
	private OnTouchListener mContentsTouchListener = new OnTouchListener()
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if(event.getEventTime() - mPrevTouchTime > 250)
		    {
				mPrevTouchTime = event.getEventTime();
				Intent intent = new Intent(v.getContext(), ContentActivity.class);
				intent.putExtra("PageCount", mResources.TotalPages);
				startActivityForResult(intent, 0);
		    }
			return true;
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 0)
		{
			mPageToDisplay = data.getIntExtra("page", -1);
			SavePreferences();
			mManualView.SetPage(mPageToDisplay);
		}
	}
	
	private OnClickListener mClickListener = new OnClickListener()
   	{
   		@Override
   		public void onClick(View v)
   		{
   			int taskResources[] = mResources.GetTaskResources(mPageToDisplay, v.getId());
   			if(taskResources != null)
   			{
   				Intent intent = new Intent(v.getContext(), TaskActivity.class);
	   			intent.putExtra("task", taskResources);
	   			startActivity(intent);
   			}
   		}
   	};

	@Override
    protected void onStop()
	{
		super.onStop();
		SavePreferences();
    }
	
	private void SavePreferences()
	{
		SharedPreferences settings = getSharedPreferences("ManualPrefs", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("page", mPageToDisplay);
		editor.commit();
	}
	
	private void LoadPreferences()
	{
		SharedPreferences settings = getSharedPreferences("ManualPrefs", 0);
		mPageToDisplay = settings.getInt("page", 0);
	}
}