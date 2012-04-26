package ru.cut2drox.brain;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;

import ru.cut2drox.ui.SendForm;

import java.io.FileNotFoundException;

public class ScreenshotCapture {
	
	Image image;
	
	ScreenshotCapture(final Shell shell,final Display display)
	{
		Rectangle totalBounds = display.getBounds();
		GC gc = new GC(display);
		final Image bufferImage = new Image(display, totalBounds.width,totalBounds.height);
        gc.copyArea(bufferImage, 0, 0);
        gc.dispose();
        
        final Shell popup = new Shell(shell,SWT.NO_TRIM | SWT.ON_TOP);
        popup.setBounds(0, 0,3286,1080 );
        popup.setFullScreen(true);
        popup.addListener(SWT.Close, new Listener() {
          public void handleEvent(Event e) {
            bufferImage.dispose();
          }
        });
        final Canvas canvas = new Canvas(popup, SWT.NONE);
        Cursor crossCursor = new Cursor(display,SWT.CURSOR_CROSS);
        canvas.setCursor(crossCursor);
        canvas.setBounds(0, 0,totalBounds.width,totalBounds.height);
        canvas.addPaintListener(new PaintListener() {
          public void paintControl(PaintEvent e) {
            e.gc.drawImage(bufferImage, 0, 0);
          }
        });
        popup.open();
        
        Listener listener = new Listener() {
        	Point point = null;
            public void handleEvent(Event e) {
      		switch (e.type) {
      			case SWT.MouseDown:
      				point = new Point(e.x, e.y);
      				break;
      			case SWT.MouseMove:
      				if (point == null) return;
      				Tracker tracker = new Tracker(canvas, SWT.RESIZE);
      				tracker.setStippled(true);
      				tracker.setRectangles(new Rectangle[] { new Rectangle(e.x, e.y, 0, 0), });
      				tracker.open();
      				final Rectangle[] rect = tracker.getRectangles();
      				canvas.dispose();
                  
      				image = new Image(display, rect[0].width, rect[0].height);
      				GC gc = new GC(bufferImage);
      				gc.copyArea(image, rect[0].x, rect[0].y);
      				gc.dispose();
      				popup.dispose();
      			case SWT.MouseUp:
      				SendForm newForm = new SendForm();
                      try {
                          newForm.open(image);
                      } catch (FileNotFoundException e1) {
                          e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                      }
                      point = null;
      	  	      	break;
      			}
            }
        };
          canvas.addListener(SWT.MouseDown, listener);
          canvas.addListener(SWT.MouseMove, listener);
          canvas.addListener(SWT.MouseUp, listener);
	}
}
