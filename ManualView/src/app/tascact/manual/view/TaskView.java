package app.tascact.manual.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class TaskView extends RelativeLayout
{
	
    public TaskView(Context context, int PageNumber, int TaskNumber)
    {
		super(context);
	}
    
    // получение размеров экрана
    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    // отрисовка страницы
    @Override protected void onDraw(Canvas canvas) 
	{	
	}
    
    @Override public boolean onTouchEvent(MotionEvent event)
	{
	    return true;
	}
    
    public void RestartTask()
    {
    }
    
    public void CheckTask()
    {
    }
}