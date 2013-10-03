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
import android.util.DisplayMetrics;

@Kroll.module(name="Tigraph", id="miga.tigraph")
public class TigraphModule extends KrollModule{

	Context context;
	int width;
	int height;
	
	public TigraphModule() {
		super();
		TiApplication appContext = TiApplication.getInstance();
		Activity activity = appContext.getCurrentActivity();
		context=activity.getApplicationContext();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		
	}

	@Kroll.method
	public TiBlob drawLines(HashMap args){
	  KrollDict arg = new KrollDict(args);
	  
	  int h = arg.getInt("height");
	  int lw = arg.getInt("lineWidth");
	  String bg = arg.optString("backgroundColor", "");
	  String col = arg.optString("color", "#000000");
	  String circleCol = arg.optString("circleColor", "#000000");
	  boolean circle = arg.optBoolean("circles", false);
	  boolean text = arg.optBoolean("text", false);
	  String textCol = arg.optString("textColor", "#000000");
	  int circleRad = arg.optInt("circleRadius", 4);
	  int maxY = arg.optInt("maxY", h);
	  int maxX = 0;
	  
	  Object[] argArray = (Object[])args.get("points");
	  
	  for (int index=0; index < argArray.length-1; index++) {
	    Object[] pos2 = (Object[])argArray[index+1];
	    int x2 = Integer.parseInt(pos2[0].toString());
	    if (x2>maxX) maxX = x2;
	  }
	  if (circle) maxX += circleRad*2;
	  Bitmap resultBitmap = Bitmap.createBitmap(maxX,h, Bitmap.Config.ARGB_8888);
	  
	  Canvas canvas = new Canvas(resultBitmap);		
	  Paint paint = new Paint();
	  
	  if (bg!=""){
	    // fill background
	    canvas.drawColor(Color.parseColor(bg));
	  }
	  paint.setAntiAlias(true);
	  
	  paint.setStrokeWidth(lw);
	  paint.setTextSize(16); 
	  
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
	    for (int index=0; index < argArray.length; index++) {
	      Object[] pos = (Object[])argArray[index];
	      float x1 = Integer.parseInt(pos[0].toString());
	      float y1 = h- ( Float.parseFloat(pos[1].toString()) /maxY * h);
	      
	      float tw = paint.measureText(pos[1].toString());
	      paint.setColor(Color.parseColor(circleCol));
	      canvas.drawText(pos[1].toString(), x1-tw*0.5f, y1-circleRad-5, paint); 
	      
	      paint.setColor(Color.parseColor(circleCol));
	      canvas.drawCircle(x1,y1, circleRad, paint);
	    }
	  }
	  
	  if (text){
	    for (int index=0; index < argArray.length; index++) {
	      Object[] pos = (Object[])argArray[index];
	      float x1 = Integer.parseInt(pos[0].toString());
	      float y1 = h- ( Float.parseFloat(pos[1].toString()) /maxY * h);
	      
	      float tw = paint.measureText(pos[1].toString());
	      paint.setColor(Color.parseColor(textCol));
	      canvas.drawText(pos[1].toString(), x1-tw*0.5f, y1-circleRad-5, paint); 
	      }
	  }

	  
	  canvas.drawBitmap(resultBitmap, 0,0, paint);
	  
	  TiBlob result = TiBlob.blobFromImage(resultBitmap);
	  return result;
	}
	
	
	
	
	
	
}
