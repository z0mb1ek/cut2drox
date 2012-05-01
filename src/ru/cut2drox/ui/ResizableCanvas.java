package ru.cut2drox.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.wb.swt.SWTResourceManager;

import ru.cut2drox.brain.Config;

public class ResizableCanvas extends Canvas {

	Display display;
	int x;
	int y;
	int width;
	int height;
	Image image=null;
	String s="";
	int textX=0;
	int textY=0;
	int canvasShiftX=0;
	int canvasShiftY=0;
	Font font;
	int lineStartX=0;
	int lineStartY=0;
	int lineEndX=0;
	int lineEndY=0;
	int circleStartX=0;
	int circleStartY=0;
	int circleHeight=0;
	int circleWidth=0;
	int squareStartX=0;
	int squareStartY=0;
	int squareHeight=0;
	int squareWidth=0;
	int[] polArray={0,0,0,0,0,0};
	
	public ResizableCanvas(Composite arg0, int arg1,Display disp) {
		super(arg0, arg1);
		this.display=disp;
	}
	
	ScrollBar hBar = this.getHorizontalBar();
	ScrollBar vBar = this.getVerticalBar();
	
	
	final Point origin = new Point(0, 0);
	
	
	
	Listener hLis = new Listener()
	{
		public void handleEvent(Event e) {
			int hSelection = hBar.getSelection();
	        int destX = -hSelection - origin.x;
	        Rectangle rect = image.getBounds();
	       	scroll(destX, 0, 0, 0, rect.width, rect.height, false);
	        origin.x = -hSelection;
	        canvasShiftX=origin.x;
		}

	};
	
    Listener vLis = new Listener() 
    {
      public void handleEvent(Event e) {
        int vSelection = vBar.getSelection();
        int destY = -vSelection - origin.y;
        Rectangle rect = image.getBounds();
        scroll(0, destY, 0, 0, rect.width, rect.height, false);
        origin.y = -vSelection;
        canvasShiftY=origin.y;
      }

    };
    Listener rLis = new Listener() {
      public void handleEvent(Event e) {
    	setBounds(x,y,width,height);
        Rectangle rect = image.getBounds();
        Rectangle client = getClientArea();
        hBar.setMaximum(rect.width);
        vBar.setMaximum(rect.height);
        hBar.setThumb(Math.min(rect.width, client.width));
        vBar.setThumb(Math.min(rect.height, client.height));
        int hPage = rect.width - client.width;
        int vPage = rect.height - client.height;
        int hSelection = hBar.getSelection();
        int vSelection = vBar.getSelection();
        if (hSelection >= hPage) {
          if (hPage <= 0) hSelection = 0;
          origin.x = -hSelection;
          canvasShiftX=origin.x;
        }
        if (vSelection >= vPage) {
          if (vPage <= 0) vSelection = 0;
          origin.y = -vSelection;
          canvasShiftY=origin.y;
        }
        redraw();
      }
    };
    Listener pLis = new Listener() {
      public void handleEvent(Event e) {
        GC gc = e.gc;
        gc.setAntialias(SWT.ON); 		//СГЛАЖИВАНИЕ!!!
		gc.drawImage(image, origin.x, origin.y);
        Rectangle rect = image.getBounds();
        Rectangle client = getClientArea();
        int marginWidth = client.width - rect.width;
        if (marginWidth > 0) {
          gc.fillRectangle(rect.width, 0, marginWidth, client.height);
        }
        int marginHeight = client.height - rect.height;
        if (marginHeight > 0) {
          gc.fillRectangle(0, rect.height, client.width, marginHeight);
        }
        try {
			Config conf=new Config().takeConfig();
			 gc.setForeground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));   //цвет
			 gc.setBackground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));
			} catch (FileNotFoundException e1) {e1.printStackTrace();}
       
        gc.setFont(font); 
        gc.drawText(s, textX, textY,true);
        gc.setLineWidth(3);
        gc.drawLine(lineStartX,lineStartY,lineEndX,lineEndY);
        gc.fillPolygon(polArray);
        gc.drawPolygon(polArray);
        gc.drawOval(circleStartX, circleStartY,circleWidth, circleHeight);
        gc.drawRectangle(squareStartX, squareStartY, squareWidth, squareHeight);
      }
    };
	
    public void addScrolling()
    {
    	hBar.addListener(SWT.Selection,hLis);
    	vBar.addListener(SWT.Selection, vLis);
    	this.addListener(SWT.Resize, rLis);
    	this.addListener(SWT.Paint, pLis);
    }
    
    public void setRBounds(int x,int y,int width,int height)
    {
    	this.x=x;
    	this.y=y;
    	this.width=width;
    	this.height=height;
    }
    
    public void setImage(Image image)
    {
    	this.image=image;
    }
    
    public void drawRText(String s,int i,int j, Font font) //заняться методом!!!
    {
    	this.s=s;
    	this.textX=i;
    	this.textY=j;
    	this.font=font;
    	redraw();
    }
    public void drawRLine(int x1,int y1,int x2,int y2)
    {
    	this.lineStartX=x1;
    	this.lineStartY=y1;
    	this.lineEndX=x2;
    	this.lineEndY=y2;
    	redraw();
    }
    public void drawRPolygon(int[] pointArray)
    {
    	this.polArray=pointArray;
    	redraw();
    }
    public void drawROval(int x,int y,int width,int height)
    {
    	this.circleStartX=x;
    	this.circleStartY=y;
    	this.circleHeight=height;
    	this.circleWidth=width;
    	redraw();
    }
    public void drawRRectangle(int x,int y,int width,int height)
    {
    	this.squareStartX=x;
    	this.squareStartY=y;
    	this.squareHeight=height;
    	this.squareWidth=width;
    	redraw();
    }
    public int getCanvasShiftX()
    {
    	return -canvasShiftX;
    }
    
    public int getCanvasShiftY()
    {
    	return -canvasShiftY;
    }
    
    
    

}

