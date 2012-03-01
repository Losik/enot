//package app.tascact.manual.view;
//
//import android.content.Context;
//import android.view.View;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View.OnTouchListener;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import app.tascact.manual.XMLResources;
//import app.tascact.manual.view.ManualControlView;
//import app.tascact.manual.view.PageReaderView;
//
//public class PageReaderWithControlsView extends LinearLayout {
//
//	PageReaderView reader;
//	ManualControlView controlPanel;
//	long mPrevTouchTime;
//	int pageToDisplay;
//	XMLResources markup;
//	
//	public PageReaderWithControlsView(Context context, XMLResources book, int page)
//		{
//			super(context);
//			setOrientation(LinearLayout.VERTICAL);
//			pageToDisplay = page;
//			markup = book;
//			
//			// Setting up page reader.
//			reader = new PageReaderView(getContext(), markup, pageToDisplay);
//			addView(reader);
//
//			// Setting up control panel.
//			controlPanel = new ManualControlView(getContext());		
//			
//			controlPanel.mNextButton.setOnTouchListener(new OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					if(event.getEventTime() - mPrevTouchTime > 250) {
//						mPrevTouchTime = event.getEventTime();
//						pageToDisplay = reader.nextPage();
//						savePreferences();
//				    }
//					return true;
//				}
//			});
//			
//			controlPanel.mPrevButton.setOnTouchListener(new OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					if(event.getEventTime() - mPrevTouchTime > 250) {
//						mPrevTouchTime = event.getEventTime();					
//						pageToDisplay = reader.prevPage();
//						savePreferences();
//				    }
//					return true;
//				}
//			});
//			
//			controlPanel.mContentsButton.setOnTouchListener(new OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					if(event.getEventTime() - mPrevTouchTime > 250) {
//						mPrevTouchTime = event.getEventTime();
//						Intent intent = new Intent(v.getContext(), ContentActivity.class);
//						intent.putExtra("PageCount", markup.getPageNumber());
//						//startActivityForResult(intent, 0);
//				    }
//					return true;
//				}
//			});
//			
//			addView(controlPanel);//, new LayoutParams(LayoutParams.MATCH_PARENT, 167));
//		}
//		
//		@Override
//		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//			super.onActivityResult(requestCode, resultCode, data);
//			if(resultCode == RESULT_OK && requestCode == 0)	{
//				pageToDisplay = data.getIntExtra("page", -1);
//				savePreferences();
//				reader.setPage(pageToDisplay);
//			}
//		}
//		
//		@Override
//	    protected void onStop() {
//			super.onStop();
//			savePreferences();
//	    }
//		
//		private void savePreferences() {
//			SharedPreferences settings = getSharedPreferences("ManualPrefs", 0);
//			SharedPreferences.Editor editor = settings.edit();
//			if(mManualName.equals("book1"))
//				editor.putInt("page1", pageToDisplay);
//			//if(mManualName.equals("book2"))
//			//	editor.putInt("page2", mPageToDisplay);
//			editor.commit();
//		}
//		
//		private void loadPreferences() {
//			SharedPreferences settings = getSharedPreferences("ManualPrefs", 0);
//			if(mManualName.equals("book1"))
//				pageToDisplay = settings.getInt("page1", 1);
//			//if(mManualName.equals("book2"))
//			//	mPageToDisplay = settings.getInt("page2", 0);
//		}
//	}
//
//	
//}
