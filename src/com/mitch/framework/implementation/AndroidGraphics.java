package com.mitch.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import com.mitch.framework.Graphics;
import com.mitch.framework.Image;
import com.mitch.framework.containers.Vector2d;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    RectF srcRect = new RectF();
    RectF dstRect = new RectF();
    float scale = 2.807f;
    //float scale2 = scale * 2;

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }
    
    @Override
    public Image newImage(String fileName, ImageFormat format) {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        options.inScaled = false;
        
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }


    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }
    
    @Override
    public void drawARGB(int a, int r, int g, int b) {
        paint.setStyle(Style.FILL);
       canvas.drawARGB(a, r, g, b);
    }
    
    @Override
    public Vector2d drawString(String text, int x, int y, Paint paint) {
    	canvas.drawText(text, x, y, paint);	
    	
    	Rect textBounds = new Rect(0,0,0,0);
    	paint.getTextBounds(text, 0, text.length(), textBounds);
    	return new Vector2d(textBounds.right, textBounds.top);
    }
    

    public void drawImage(Image Image, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(((AndroidImage) Image).bitmap, null, dstRect,
                null);
    }
    
    @Override
    public void drawImage(Image image, Vector2d pos) {
    	drawImage(image, pos.x, pos.y);
    }
    
    @Override
    public void drawImage(Image image, double x, double y) {
    	float width    = image.getWidth()  * scale;
    	float height   = image.getHeight() * scale;
    	dstRect.left   = (float) x;
        dstRect.top    = (float) y;
        dstRect.right  = (float) (x + width);
        dstRect.bottom = (float) (y + height);
        canvas.drawBitmap(((AndroidImage)image).bitmap, null, dstRect, null);
    }
    public void drawImage2(Image Image, float x, float y) {
    	float width    = Image.getWidth()  * scale;
    	float height   = Image.getHeight() * scale;
    	dstRect.left   = x;
        dstRect.top    = y;
        dstRect.right  = (x + width);
        dstRect.bottom =  (y + height);
        canvas.drawBitmap(((AndroidImage)Image).bitmap, null, dstRect, null);
        
        
    }
    
    /*public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight) {
   		srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;
        
        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, null);
        
    }
    */
    public void drawScaledImage(Image Image, int x, int y, int width, int height) {
    	dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;
    	canvas.drawBitmap(((AndroidImage) Image).bitmap, null, dstRect, null);
    }
   
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
    public Canvas getCanvas() {
    	return canvas;
    }

	@Override
	public void drawScaledImage(Image Image, int x, int y, float width,
			float height) {
		dstRect.left = x;
        dstRect.top = y;
        dstRect.right = (int) (x + width);
        dstRect.bottom = (int) (y + height);
		 canvas.drawBitmap(((AndroidImage)Image).bitmap, null, dstRect, null);
	}
}
