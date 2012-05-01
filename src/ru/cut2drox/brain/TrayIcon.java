package ru.cut2drox.brain;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class TrayIcon {
	Display disp;
	Shell sl;
	ToolTip tip;
	TrayItem item;
	String picPath = ClassLoader.getSystemResource(".").getPath()+"pic\\";
	
	public TrayIcon(final Display display,final Shell shell)
	{
		tip = new ToolTip(shell, SWT.BALLOON | SWT.ICON_INFORMATION);
		//Image trayImage = new Image(display, 16, 16);
		Image trayImage = new Image(display, picPath+"trayIcon.png");
		final Tray tray = display.getSystemTray();
	    if (tray == null) {
	      System.out.println("The system tray is not available");
	    } else {
	      item = new TrayItem(tray, SWT.NONE);
	      item.setToolTip(tip);
	      item.setToolTipText("SWT TrayItem");
	      item.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event event) {								//одно нажатие на трей
	          ScreenshotCapture scrc = new ScreenshotCapture(shell,display);
	        }
	      });
	      item.addListener(SWT.DefaultSelection, new Listener() {
	        public void handleEvent(Event event) {
	          System.out.println("default selection");
	        }
	      });
	      item.setImage(trayImage);
	    }
	}
	public TrayIcon()
	{
		
	}
	
    public void showBalloon(Shell shell,String s)
    {
    	this.tip.setText("Cut2Drox");
    	this.tip.setMessage(s);
    	this.tip.setVisible(true);
    }
    
//    public void setIcon()
//    {
//    	Image trayImage = new Image(disp, "D:/2.png");
//    	item.setImage(trayImage);
//    }
    
}
 
