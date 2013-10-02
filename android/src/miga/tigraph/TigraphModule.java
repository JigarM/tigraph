package miga.tigraph;

import java.util.Arrays;
import android.content.Context;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import org.appcelerator.titanium.TiBlob;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import org.appcelerator.kroll.KrollDict;
import java.util.HashMap;
import java.util.Arrays;

@Kroll.module(name="Tigraph", id="miga.tigraph")
public class TigraphModule extends KrollModule{

	Context context;
	
	public TigraphModule() {
		super();
		TiApplication appContext = TiApplication.getInstance();
		Activity activity = appContext.getCurrentActivity();
		context=activity.getApplicationContext();
		
	}

	@Kroll.method
	public TiBlob drawLines(HashMap args){
	  KrollDict arg = new KrollDict(args);
	  int w = arg.getInt("width");
	  int h = arg.getInt("height");
	  int lw = arg.getInt("lineWidth");
	  String bg = arg.optString("backgroundColor", "#FFFFFF");
	  String col = arg.optString("color", "#000000");
	  String circleCol = arg.optString("circleColor", "#000000");
	  boolean circle = arg.optBoolean("circles", false);
	  int circleRad = arg.optInt("circleRadius", 4);
	  int maxY = arg.optInt("maxY", h);
	  
	  //KrollDict points = arg.getKrollDict("points");
	  Object[] argArray = (Object[])args.get("points");
	  
	  
	  
	  
	  Bitmap resultBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
	  Canvas canvas = new Canvas(resultBitmap);		
	  Paint paint = new Paint();
	  // fill background
	  canvas.drawColor(Color.parseColor(bg));
	  paint.setAntiAlias(true);
	  paint.setStrokeWidth(1);
	  paint.setColor(0xAAAAAAFF);
	  Log.e("LOG:"  , String.valueOf(h*0.5f));
	  canvas.drawLine(0,h*0.5f,w,h*0.5f, paint);
	  
	  paint.setStrokeWidth(lw);
	  
	  
	  
	  for (int index=0; index < argArray.length-1; index++) {
             Object[] pos = (Object[])argArray[index];
	     Object[] pos2 = (Object[])argArray[index+1];
	     
	      float x1 = Integer.parseInt(pos[0].toString());
	      float y1 = h- ( Float.parseFloat(pos[1].toString()) /maxY * h);
	      float x2 = Integer.parseInt(pos2[0].toString());
	      float y2 =  h- ( Float.parseFloat(pos2[1].toString()) /maxY * h);
	     	  
	     paint.setColor(Color.parseColor(col));
	     canvas.drawLine(x1,y1,x2,y2, paint);
	    
	  }
	  
	  if (circle){
	    for (int index=0; index < argArray.length-1; index++) {
	      Object[] pos = (Object[])argArray[index];
	      Object[] pos2 = (Object[])argArray[index+1];
	      
	      float x1 = Integer.parseInt(pos[0].toString());
	      float y1 = h- ( Float.parseFloat(pos[1].toString()) /maxY * h);
	      float x2 = Integer.parseInt(pos2[0].toString());
	      float y2 =  h- ( Float.parseFloat(pos2[1].toString()) /maxY * h);
	      paint.setColor(Color.parseColor(circleCol));
	      canvas.drawCircle(x2,y2, circleRad, paint);
	    }
	  }
	  
	  canvas.drawBitmap(resultBitmap, 0,0, paint);
	  
	  TiBlob result = TiBlob.blobFromImage(resultBitmap);
	  return result;
	}
	
	
	
	
	
	
}
