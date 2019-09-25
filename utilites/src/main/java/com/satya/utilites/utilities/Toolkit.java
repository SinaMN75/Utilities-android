package com.satya.utilites.utilities;

import android.annotation.*;
import android.app.*;
import android.app.Application.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.inputmethod.*;

import androidx.core.content.*;

import org.jetbrains.annotations.*;

import java.lang.reflect.*;
import java.util.*;

public final class Toolkit {
	private static final String PERMISSION_ACTIVITY_CLASS_NAME = "com.blankj.utilcode.util.PermissionUtils$PermissionActivity";
	private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();
	
	@SuppressLint("StaticFieldLeak")
	private static Application sApplication;
	
	private static void init(final Context context) {
		if (context == null) {
			init(getApplicationByReflect());
			return;
		}
		init((Application) context.getApplicationContext());
	}
	
	private static void init(final Application app) {
		if (sApplication == null) {
			if (app == null) {
				sApplication = getApplicationByReflect();
			} else {
				sApplication = app;
			}
			sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
		} else {
			if (app != null && app.getClass() != sApplication.getClass()) {
				sApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
				ACTIVITY_LIFECYCLE.mActivityList.clear();
				sApplication = app;
				sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
			}
		}
	}
	
	public static Application getApp() {
		if (sApplication != null) return sApplication;
		Application app = getApplicationByReflect();
		init(app);
		return app;
	}
	
	private static Application getApplicationByReflect() {
		try {
			@SuppressLint("PrivateApi")
			Class<?> activityThread = Class.forName("android.app.ActivityThread");
			Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
			Object app = activityThread.getMethod("getApplication").invoke(thread);
			if (app == null) throw new NullPointerException("u should init first");
			return (Application) app;
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new NullPointerException("u should init first");
	}
	
	public static Context getTopActivityOrApp() {
		if (isAppForeground()) {
			Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
			return topActivity == null ? Toolkit.getApp() : topActivity;
		} else {
			return Toolkit.getApp();
		}
	}
	
	private static boolean isAppForeground() {
		ActivityManager am = (ActivityManager) Toolkit.getApp().getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) return false;
		List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
		if (info == null || info.size() == 0) return false;
		for (ActivityManager.RunningAppProcessInfo aInfo: info) {
			if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return aInfo.processName.equals(Toolkit.getApp().getPackageName());
			}
		}
		return false;
	}
	
	public interface OnAppStatusChangedListener {
		void onForeground();
		
		void onBackground();
	}
	
	public interface OnActivityDestroyedListener {
		void onActivityDestroyed(Activity activity);
	}
	
	static class ActivityLifecycleImpl implements ActivityLifecycleCallbacks {
		
		final LinkedList<Activity> mActivityList = new LinkedList<>();
		final Map<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();
		final Map<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap<>();
		
		private int mForegroundCount = 0;
		private int mConfigCount = 0;
		private boolean mIsBackground = false;
		
		private static void fixSoftInputLeaks(final Activity activity) {
			if (activity == null) return;
			InputMethodManager imm =
					(InputMethodManager) Toolkit.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm == null) return;
			String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView",
			                                  "mNextServedView"};
			for (String leakView: leakViews) {
				try {
					Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
					if (!leakViewField.isAccessible()) leakViewField.setAccessible(true);
					Object obj = leakViewField.get(imm);
					if (!(obj instanceof View)) continue;
					View view = (View) obj;
					if (view.getRootView() == activity.getWindow().getDecorView().getRootView())
						leakViewField.set(imm, null);
				} catch (Throwable ignore) { }
			}
		}
		
		@Override
		public void onActivityCreated(@NotNull Activity activity, Bundle savedInstanceState) {
			setTopActivity(activity);
		}
		
		@Override
		public void onActivityStarted(@NotNull Activity activity) {
			if (!mIsBackground) {
				setTopActivity(activity);
			}
			if (mConfigCount < 0) {
				++mConfigCount;
			} else {
				++mForegroundCount;
			}
		}
		
		@Override
		public void onActivityResumed(@NotNull Activity activity) {
			setTopActivity(activity);
			if (mIsBackground) {
				mIsBackground = false;
				postStatus(true);
			}
		}
		
		@Override
		public void onActivityPaused(@NotNull Activity activity) {
		
		}
		
		@Override
		public void onActivityStopped(Activity activity) {
			if (activity.isChangingConfigurations()) {
				--mConfigCount;
			} else {
				--mForegroundCount;
				if (mForegroundCount <= 0) {
					mIsBackground = true;
					postStatus(false);
				}
			}
		}
		
		@Override
		public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {}
		
		@Override
		public void onActivityDestroyed(@NotNull Activity activity) {
			mActivityList.remove(activity);
			consumeOnActivityDestroyedListener(activity);
			fixSoftInputLeaks(activity);
		}
		
		Activity getTopActivity() {
			if (!mActivityList.isEmpty()) {
				final Activity topActivity = mActivityList.getLast();
				if (topActivity != null) {
					return topActivity;
				}
			}
			Activity topActivityByReflect = getTopActivityByReflect();
			if (topActivityByReflect != null) {
				setTopActivity(topActivityByReflect);
			}
			return topActivityByReflect;
		}
		
		private void setTopActivity(final Activity activity) {
			if (PERMISSION_ACTIVITY_CLASS_NAME.equals(activity.getClass().getName())) return;
			if (mActivityList.contains(activity)) {
				if (!mActivityList.getLast().equals(activity)) {
					mActivityList.remove(activity);
					mActivityList.addLast(activity);
				}
			} else {
				mActivityList.addLast(activity);
			}
		}
		
		private void postStatus(final boolean isForeground) {
			if (mStatusListenerMap.isEmpty()) return;
			for (OnAppStatusChangedListener onAppStatusChangedListener: mStatusListenerMap.values()) {
				if (onAppStatusChangedListener == null) return;
				if (isForeground) {
					onAppStatusChangedListener.onForeground();
				} else {
					onAppStatusChangedListener.onBackground();
				}
			}
		}
		
		private void consumeOnActivityDestroyedListener(Activity activity) {
			Iterator<Map.Entry<Activity, Set<OnActivityDestroyedListener>>> iterator
					= mDestroyedListenerMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Activity, Set<OnActivityDestroyedListener>> entry = iterator.next();
				if (entry.getKey() == activity) {
					Set<OnActivityDestroyedListener> value = entry.getValue();
					for (OnActivityDestroyedListener listener: value) {
						listener.onActivityDestroyed(activity);
					}
					iterator.remove();
				}
			}
		}
		
		private Activity getTopActivityByReflect() {
			try {
				@SuppressLint("PrivateApi")
				Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
				Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
				Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
				mActivityListField.setAccessible(true);
				Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
				if (activities == null) return null;
				for (Object activityRecord: activities.values()) {
					Class activityRecordClass = activityRecord.getClass();
					Field pausedField = activityRecordClass.getDeclaredField("paused");
					pausedField.setAccessible(true);
					if (!pausedField.getBoolean(activityRecord)) {
						Field activityField = activityRecordClass.getDeclaredField("activity");
						activityField.setAccessible(true);
						return (Activity) activityField.get(activityRecord);
					}
				}
			} catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	public static final class FileProvider4UtilCode extends FileProvider {
		
		@Override
		public boolean onCreate() {
			Toolkit.init(getContext());
			return true;
		}
	}
}