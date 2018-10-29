package com.mycompany.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.*;
import android.widget.*;
import android.view.*;
import android.os.*;
import android.content.*;
import android.provider.*;
import android.*;
import android.support.annotation.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.support.v4.app.*;
import android.content.pm.*;

public class MainActivity extends AppCompatActivity
{

	ToggleButton tog,toooo;
	//boolean isc;
	FlashlightHelper fh;
	RelativeLayout rl;

	private int REQUEST_CODE_TAKE_PICTURE;


    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

		rl = (RelativeLayout)findViewById(R.id.activity_main);

		tog = (ToggleButton)findViewById(R.id.main_activityToggleButton);
		toooo = (ToggleButton)findViewById(R.id.mainactivityToggleButton1);
		fh = new FlashlightHelper(getApplicationContext());
	}

	public void li(View v)
	{

		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
		startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE); 


	}

	

	public void to(View v)
	{
		if (tog.isChecked())
		{		
			fh.open();				
		}
		else
		{
			fh.close();
		}
	}

	public void tooo(View v)
	{
		if (toooo.isChecked())
		{		
			handler.post(task);		
		}
		else
		{
			handler.removeCallbacks(task);
		}
	}


	private Handler handler = new Handler();   
    private Runnable task = new Runnable() {  
        public void run()
		{   

			handler.postDelayed(this, 1 * 300);//设置循环时间，此处是5秒
			//取得当前时间
			fh.open();
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{}
			fh.close();
        }   

    };




	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{


		Bitmap bm = (Bitmap) data.getExtras().get("data");

		rl.setBackground(new BitmapDrawable(bm));  



    }




}
