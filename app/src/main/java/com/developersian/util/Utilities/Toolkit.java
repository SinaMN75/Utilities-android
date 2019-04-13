package com.developersian.util.Utilities;
import android.annotation.*;
import android.app.*;
import android.app.Application.*;
import android.content.*;
import android.os.*;
import com.blankj.utilcode.util.*;

import java.lang.reflect.*;
import java.util.*;
public final class Toolkit{
private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE=new ActivityLifecycleImpl();
@SuppressLint("StaticFieldLeak") private static Application application;
private Toolkit(){
	throw new UnsupportedOperationException("u can't instantiate me...");
}
private static void init(final Application app){
	if(application==null){
		if(app==null)
			application=getApplicationByReflect();
		else
			application=app;
		application.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
	}
}
private static Application getApp(){
	if(application!=null)
		return application;
	Application app=getApplicationByReflect();
	init(app);
	return app;
}
private static Application getApplicationByReflect(){
	try{
		@SuppressLint("PrivateApi") Class<?> activityThread=Class.forName("android.app.ActivityThread");
		Object thread=activityThread.getMethod("currentActivityThread").invoke(null);
		Object app=activityThread.getMethod("getApplication").invoke(thread);
		if(app==null){
			throw new NullPointerException("u should init first");
		}
		return (Application)app;
	}catch(NoSuchMethodException|IllegalAccessException|ClassNotFoundException|InvocationTargetException e){
		e.printStackTrace();
	}
	throw new NullPointerException("u should init first");
}
public static Context getTopActivityOrApp(){
	if(isAppForeground()){
		Activity topActivity=ACTIVITY_LIFECYCLE.getTopActivity();
		return topActivity==null?Toolkit.getApp():topActivity;
	}else{
		return Toolkit.getApp();
	}
}
private static boolean isAppForeground(){
	ActivityManager am=(ActivityManager)Toolkit.getApp().getSystemService(Context.ACTIVITY_SERVICE);
	List<ActivityManager.RunningAppProcessInfo> info=am.getRunningAppProcesses();
	if(info==null||info.size()==0)
		return false;
	for(ActivityManager.RunningAppProcessInfo aInfo: info){
		if(aInfo.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
			return aInfo.processName.equals(Toolkit.getApp().getPackageName());
		}
	}
	return false;
}
public interface OnAppStatusChangedListener{
	void onForeground();
	void onBackground();
}
static class ActivityLifecycleImpl implements ActivityLifecycleCallbacks{
	final LinkedList<Activity> activityList=new LinkedList<>();
	final HashMap<Object,OnAppStatusChangedListener> mStatusListenerMap=new HashMap<>();
	private int mForegroundCount=0;
	private int mConfigCount=0;
	@Override public void onActivityCreated(Activity activity,Bundle savedInstanceState){
		setTopActivity(activity);
	}
	@Override public void onActivityStarted(Activity activity){
		setTopActivity(activity);
		if(mForegroundCount<=0){
			postStatus(true);
		}
		if(mConfigCount<0){
			++mConfigCount;
		}else{
			++mForegroundCount;
		}
	}
	@Override public void onActivityResumed(Activity activity){
		setTopActivity(activity);
	}
	@Override public void onActivityPaused(Activity activity){
	}
	@Override public void onActivityStopped(Activity activity){
		if(activity.isChangingConfigurations()){
			--mConfigCount;
		}else{
			--mForegroundCount;
			if(mForegroundCount<=0){
				postStatus(false);
			}
		}
	}
	@Override public void onActivitySaveInstanceState(Activity activity,Bundle outState){
	}
	@Override public void onActivityDestroyed(Activity activity){
		activityList.remove(activity);
	}
	private void postStatus(final boolean isForeground){
		if(mStatusListenerMap.isEmpty())
			return;
		for(OnAppStatusChangedListener onAppStatusChangedListener: mStatusListenerMap.values()){
			if(onAppStatusChangedListener==null)
				return;
			if(isForeground)
				onAppStatusChangedListener.onForeground();
			else
				onAppStatusChangedListener.onBackground();
		}
	}
	Activity getTopActivity(){
		if(!activityList.isEmpty()){
			final Activity topActivity=activityList.getLast();
			if(topActivity!=null)
				return topActivity;
		}
		Activity topActivityByReflect=getTopActivityByReflect();
		if(topActivityByReflect!=null)
			setTopActivity(topActivityByReflect);
		return topActivityByReflect;
	}
	private void setTopActivity(final Activity activity){
		if(activity.getClass()==PermissionUtils.PermissionActivity.class)
			return;
		if(activityList.contains(activity)){
			if(!activityList.getLast().equals(activity)){
				activityList.remove(activity);
				activityList.addLast(activity);
			}
		}else
			activityList.addLast(activity);
	}
	private Activity getTopActivityByReflect(){
		try{
			@SuppressLint("PrivateApi") Class<?> activityThreadClass=Class.forName("android.app.ActivityThread");
			Object activityThread=activityThreadClass.getMethod("currentActivityThread").invoke(null);
			Field activitiesField=activityThreadClass.getDeclaredField("activityList");
			activitiesField.setAccessible(true);
			Map activities=(Map)activitiesField.get(activityThread);
			if(activities==null)
				return null;
			for(Object activityRecord: activities.values()){
				Class activityRecordClass=activityRecord.getClass();
				Field pausedField=activityRecordClass.getDeclaredField("paused");
				pausedField.setAccessible(true);
				if(!pausedField.getBoolean(activityRecord)){
					Field activityField=activityRecordClass.getDeclaredField("activity");
					activityField.setAccessible(true);
					return (Activity)activityField.get(activityRecord);
				}
			}
		}catch(ClassNotFoundException|IllegalAccessException|NoSuchMethodException|InvocationTargetException|NoSuchFieldException e){
			e.printStackTrace();
		}
		return null;
	}
}
}