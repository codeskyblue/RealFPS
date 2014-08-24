package com.example.andriodapp;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import com.example.andriodapp.intent.MyIntent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author Silent
 * Show welcome page with some views for user to interactive
 */
public class WelcomePageHandler extends ActivityInitHandler {
	private Spinner sp=null;
	private Button startButton=null;
	private String prossName="";

	public WelcomePageHandler(Activity activi) {
		super(activi);
	}

	@Override
	public void initListeners() {
		sp=(Spinner)activity.findViewById(R.id.spinner1);
		startButton=(Button)activity.findViewById(R.id.button1);
		sp.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				System.out.println("dd");
				prossName=((TextView)arg1).getText().toString();
    //logic
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
      
		
		startButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
 
//				System.out.println("clicked");
				FpsActivity fps=new FpsActivity();
				MyIntent fpsIntent= new MyIntent(activity, FpsActivity.class);  
				fpsIntent.setMessage(prossName);
				activity.startActivity(fpsIntent);
			}
			
		});
	}
public void initParamters() {
      sp.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_dropdown_item_1line, scanProcessList()));
	}
	
	
	/**
	 * 
	 * @return
	 * prepare the processList of device
	 */
	private List scanProcessList(){
		//logic call the service of andriod
		ActivityManager mActivityManager =(ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
	    List runningProcess=	mActivityManager.getRunningAppProcesses();
	    mActivityManager.getDeviceConfigurationInfo();
	    ArrayList processNames=new ArrayList();
	    
	    
	    //get installInformation
	     List<PackageInfo> intsllPacks = activity.getPackageManager().getInstalledPackages(0);     
	     HashMap<String,String> packInfo=new HashMap<String,String>(); 
	     PackageManager pkMag=activity.getPackageManager();
	     for(int i=0;i<intsllPacks.size();i++) {     
	            PackageInfo p = intsllPacks.get(i);     
	            if ((p.versionName == null)) {     
	                continue ;     
	            }       	            
	            packInfo.put(p.packageName, p.applicationInfo.loadLabel(pkMag).toString());   
	        }  
	    
	    for(Object process:runningProcess){
	    	RunningAppProcessInfo processInfo=(RunningAppProcessInfo)process;
	    	if(packInfo.get(processInfo.processName)==null){
	    		continue;
	    	}
	        processNames.add(packInfo.get(processInfo.processName));
	    }
		return processNames;
	}

}
