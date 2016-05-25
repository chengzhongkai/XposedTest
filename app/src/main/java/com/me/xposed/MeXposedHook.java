package com.me.xposed;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.telephony.TelephonyManager;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MeXposedHook implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		
		try
		{
			if (lpparam.packageName.equals("jp.co.unisys.android.yamadamobile")) {
				HookMethod(TelephonyManager.class.getName(),lpparam.classLoader,"getSubscriberId",Utility.input("imsi"));
			}
		} catch (Throwable e)
		{
			Log.d("MeXposedHook","failed to change getDeviceId" + e.getMessage());
		}
		
	}
	
	public void HookMethod(String className,ClassLoader classLoader,String method,
			final Object result)
	{
		findAndHookMethod(className, classLoader, method, new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				param.setResult(result);
			}
			
		});
	}
	
}
