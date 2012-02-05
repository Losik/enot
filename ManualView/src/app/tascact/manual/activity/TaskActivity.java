package app.tascact.manual.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import app.tascact.manual.task.CompleteTableTaskView;
import app.tascact.manual.task.ConnectElementsTaskView;
import app.tascact.manual.view.TaskControlView;
import app.tascact.manual.view.TaskView;

public class TaskActivity extends Activity
{
	// View ��������� ���������
	private LinearLayout mMainLayout = null;
	// View ������� ��������
	private TaskView mTaskView = null;
	// View �������� ����������
	private TaskControlView mTaskControl = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        
        if(extras != null)
		{        	
	    	mMainLayout = new LinearLayout(this);
	    	
	    	//mTaskView = new ConnectElementsTaskView(this, extras.getInt("PageNumber"), extras.getInt("TaskNumber"));
	    	switch (extras.getInt("TaskType"))
	    	{
	    	  case 1:
	    		  mTaskView = new ConnectElementsTaskView(this, extras.getInt("PageNumber"), extras.getInt("TaskNumber"));
	    		  break;
	    	  case 2:
	    		  mTaskView = new CompleteTableTaskView(this, extras.getInt("PageNumber"), extras.getInt("TaskNumber"));
	    		  break;
	    	  default:
	    		  break;
	    	}
	    	//mTaskView = new CompleteTableTaskView(this, 0, 1);
	    	
	    	mTaskControl = new TaskControlView(this);

	    	// ������ ����������� ������� 
	    	mTaskControl.mCheckButton.setOnTouchListener(mCheckTouchListener);
	    	mTaskControl.mRestartButton.setOnTouchListener(mRestartTouchListener);

	    	// ����������� View �����������
			mMainLayout.setOrientation(1);
			// ����� ���������� ������s
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	
	    		mMainLayout.addView(mTaskView, new LayoutParams(LayoutParams.MATCH_PARENT, 1040));
	    	
	    	
			mMainLayout.addView(mTaskControl, new LayoutParams(LayoutParams.MATCH_PARENT, 167));
			
			setContentView(mMainLayout);
			
			
			
		}
        else
        	finish();
    }
    
    private OnTouchListener mCheckTouchListener = new OnTouchListener()
	{
		//@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			mTaskView.CheckTask();
			return true;
		}
	};

	private OnTouchListener mRestartTouchListener = new OnTouchListener()
	{
		//@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			mTaskView.RestartTask();
			return true;
		}
	};
}