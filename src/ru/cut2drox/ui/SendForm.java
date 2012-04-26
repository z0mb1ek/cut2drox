package ru.cut2drox.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import com.dropbox.client2.exception.DropboxException;

import ru.cut2drox.brain.Config;
import ru.cut2drox.brain.DBWrapper;
import ru.cut2drox.brain.TrayIcon;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;

public class SendForm {

	public static final String DATE_FORMAT_NOW = "yyyyMMddHHmmss";
	protected static Shell shell;
	Shell shellForm;
	static Display display;
	static TrayIcon ti;
	
	public static void main(String[] args) {
		display = new Display();
	    shell = new Shell(display);
	    
	    ti = new TrayIcon(display,shell);

	    while (!shell.isDisposed()) {
	        if (!display.readAndDispatch())
	          display.sleep();
	      }
	    display.dispose();
	    
	}
	
	/**
	// * @param tip
	 * @wbp.parser.entryPoint
	 *///
    /////qweqweqweqwe
	public void open(Image image) throws FileNotFoundException {
		display = Display.getDefault();
		
		createContents(image);
		shellForm.open();
		shellForm.layout();
		while (!shellForm.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents(final Image image) throws FileNotFoundException {
		shellForm = new Shell();
		shellForm.setSize(378, 352);
		shellForm.setText("SWT Application");
		shellForm.setLayout(new GridLayout(2, false));
		
		Monitor[] list = display.getMonitors();
		org.eclipse.swt.graphics.Rectangle client = shellForm.getBounds();
		org.eclipse.swt.graphics.Rectangle screen = list[0].getBounds();
		client.width=image.getBounds().width+70; //20-на левый отступ + 25 на кнопку + около 20 на скролл 
		client.height=image.getBounds().height+117;
		client.x = screen.width/2 -client.width/2;
		client.y = screen.height/2 - client.height/2;
		shellForm.setBounds(client);
		
		Composite composite_1 = new Composite(shellForm, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 211;
		gd_composite_1.heightHint = 22;
		composite_1.setLayoutData(gd_composite_1);

		final Button btnNewButton_3 = new Button(composite_1, SWT.TOGGLE); //группа цветов
		final Button button_1 = new Button(composite_1, SWT.TOGGLE);
		final Button button_3 = new Button(composite_1, SWT.TOGGLE);
		final Button button_4 = new Button(composite_1, SWT.TOGGLE);
		final Button button_5 = new Button(composite_1, SWT.TOGGLE);
		final Button button_6 = new Button(composite_1, SWT.TOGGLE);
        setColorButton(btnNewButton_3,button_4,button_3,button_1,button_5,button_6);

		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					Config conf=new Config().takeConfig();
					 conf.setR(0);
				     conf.setG(0);
				     conf.setB(255);
				     conf.makeConfig();
				} catch (IOException e) {e.printStackTrace();}
				
				btnNewButton_3.setSelection(false);
				button_1.setSelection(false);
				button_3.setSelection(false);
				button_4.setSelection(true);
				button_5.setSelection(false);
				button_6.setSelection(false);
			}
		});
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					Config conf=new Config().takeConfig();
					 conf.setR(255);
				     conf.setG(255);
				     conf.setB(255);
				     conf.makeConfig();
				} catch (IOException e) {e.printStackTrace();}
				btnNewButton_3.setSelection(false);
				button_1.setSelection(false);
				button_3.setSelection(false);
				button_4.setSelection(false);
				button_5.setSelection(true);
				button_6.setSelection(false);
			}
		});
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				btnNewButton_3.setSelection(false);
				button_1.setSelection(false);
				button_3.setSelection(false);
				button_4.setSelection(false);
				button_5.setSelection(false);
				button_6.setSelection(true);
			}
		});
		
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					Config conf=new Config().takeConfig();
					 conf.setR(0);
				     conf.setG(128);
				     conf.setB(0);
				     conf.makeConfig();
				} catch (IOException e) {e.printStackTrace();}
				btnNewButton_3.setSelection(false);
				button_1.setSelection(false);
				button_3.setSelection(true);
				button_4.setSelection(false);
				button_5.setSelection(false);
				button_6.setSelection(false);
			}
		});
		
		button_3.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(SWTResourceManager.getColor(0,128,0)); 
				gc.fillRectangle(5,5,12,12);
			}
		});
		button_4.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(SWTResourceManager.getColor(0,0,255)); 
				gc.fillRectangle(5,5,12,12);
			}
		});
		button_5.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(SWTResourceManager.getColor(255,255,255)); 
				gc.fillRectangle(5,5,12,12);
			}
		});
		button_6.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(SWTResourceManager.getColor(255,0,0)); 
				gc.fillRectangle(5,5,4,12);
				gc.setBackground(SWTResourceManager.getColor(0,128,0));
				gc.fillRectangle(9,5,4,12);
				gc.setBackground(SWTResourceManager.getColor(0,0,255));
				gc.fillRectangle(13,5,4,12);
			}
		});
		
		
		
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					Config conf=new Config().takeConfig();
					 conf.setR(0);
				     conf.setG(0);
				     conf.setB(0);
				     conf.makeConfig();
				} catch (IOException e) {e.printStackTrace();}
				btnNewButton_3.setSelection(true);
				button_1.setSelection(false);
				button_3.setSelection(false);
				button_4.setSelection(false);
				button_5.setSelection(false);
				button_6.setSelection(false);
			}
		});
		
		btnNewButton_3.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(SWTResourceManager.getColor(0,0,0)); 
				gc.fillRectangle(5,5,12,12);
			}
		});
		btnNewButton_3.setFont(SWTResourceManager.getFont("SimSun-ExtB", 10, SWT.NORMAL));
		btnNewButton_3.setBounds(0, 0, 22, 22);
		
		
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					Config conf=new Config().takeConfig();
					 conf.setR(255);
				     conf.setG(0);
				     conf.setB(0);
				     conf.makeConfig();
				} catch (IOException e) {e.printStackTrace();}
				btnNewButton_3.setSelection(false);
				button_1.setSelection(true);
				button_3.setSelection(false);
				button_4.setSelection(false);
				button_5.setSelection(false);
				button_6.setSelection(false);
			}
		});
		button_1.setFont(SWTResourceManager.getFont("SimSun-ExtB", 10, SWT.NORMAL));
		button_1.setBounds(28, 0, 22, 22);
		
		
		button_3.setFont(SWTResourceManager.getFont("SimSun-ExtB", 10, SWT.NORMAL));
		button_3.setBounds(56, 0, 22, 22);
		
		
		button_4.setFont(SWTResourceManager.getFont("SimSun-ExtB", 10, SWT.NORMAL));
		button_4.setBounds(84, 0, 22, 22);
		
		
		button_5.setFont(SWTResourceManager.getFont("SimSun-ExtB", 10, SWT.NORMAL));
		button_5.setBounds(112, 0, 22, 22);
		
		
		button_6.setFont(SWTResourceManager.getFont("SimSun-ExtB", 10, SWT.NORMAL));
		button_6.setBounds(140, 0, 22, 22);
		
		
		button_1.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.setBackground(SWTResourceManager.getColor(255,0,0)); 
				gc.fillRectangle(5,5,12,12);
			}
		});
		
		Button button_2 = new Button(shellForm, SWT.NONE);
		GridData gd_button_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_2.widthHint = 25;
		button_2.setLayoutData(gd_button_2);
		button_2.setText("");
		
		final ResizableCanvas canvas = new ResizableCanvas(shellForm,  SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL  | SWT.H_SCROLL,display);
		
		canvas.setImage(image);
		GridData gd_canvas = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_canvas.heightHint = 1;
		gd_canvas.widthHint = 1;
		canvas.setLayoutData(gd_canvas);

		canvas.addScrolling();

		shellForm.addListener (SWT.Resize,  new Listener () {
		    public void handleEvent (Event e) {
		    	int i=shellForm.getClientArea().width-38;
		    	int j=shellForm.getClientArea().height-62;
		    if(i>image.getBounds().width+15)
		    {
		    	i=image.getBounds().width+17;
		    }
		    if(j>image.getBounds().height+15)
		    {
		    	j=image.getBounds().height+17;
		    }
		    	canvas.setRBounds(5, 30, i, j);
		    } 
		});
		
		
		
		Composite composite = new Composite(shellForm, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_composite.heightHint = 101;
		composite.setLayoutData(gd_composite);
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TextLabel tl = new TextLabel(shellForm,SWT.DIALOG_TRIM);
				try {
					tl.open(canvas,image); 
					} catch (FileNotFoundException e) {e.printStackTrace();}
			}
		});
		btnNewButton_1.setImage(SWTResourceManager.getImage("D:\\T6.png"));
		btnNewButton_1.setBounds(0, 0, 25, 25);
		btnNewButton_1.setText("");
		
		Button btnNewButton_2 = new Button(composite, SWT.NONE);
		btnNewButton_2.setBounds(0, 31, 25, 25);
		btnNewButton_2.setText("");
		
		Button button = new Button(composite, SWT.NONE);
		button.setText("");
		button.setBounds(0, 62, 25, 25);
        //-----------------------------------  
		
		Button btnNewButton = new Button(shellForm, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shellForm.close();
				try {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
					ImageLoader loader = new ImageLoader();
					loader.data = new ImageData[] {image.getImageData()};
					String partName = sdf.format(cal.getTime());
					String nameImage = partName +".jpg";
					String fullPath = ClassLoader.getSystemResource(".").getPath()+"images/"+nameImage;
					//Проверять на папку и если нету создавать!!!!
					loader.save(fullPath, SWT.IMAGE_JPEG);
					DBWrapper connection = new DBWrapper();
					String imageShortURL = connection.sendImage(fullPath, nameImage);
					
//					//--------govnokod------------------
//					String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
//							"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" +
//							"<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1251\" />" +
//							"<title>cut2drox</title></head> " +
//							"<body><div align=\"center\">" +
//							"<img src=\""+imageShortURL +"\"/></div> " +
//							"</body></html>";
//					String nameCover = partName + ".xhtml";
//					ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
//					String finalURL = connection.sendFile(inputStream, content.length(), nameCover);
//					
					StringSelection ss = new StringSelection(imageShortURL);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
					
					ti.showBalloon(shell);
					//----------------------------------	
		  
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (DropboxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setText("\u041E\u0442\u043F\u0440\u0430\u0432\u0438\u0442\u044C");

	}
	
	void setColorButton(Button btnNewButton_3, Button button_4, Button button_3, Button button_1, Button button_5, Button button_6) throws FileNotFoundException
	{
		Config conf=new Config().takeConfig();
		int R = conf.getR();
		int G = conf.getG();
		int B = conf.getB();
		 switch (R) {
         case 0: 
        	 switch(G)
        	 {
        	 	case 0:
        	 		switch(B)
        	 		{
                         case 0:
                             btnNewButton_3.setSelection(true);
                             break;
                         case 255:
                             button_4.setSelection(true);
                             break;
                         default:
                             button_6.setSelection(true);
                             break;
        	 		}
                     break;
                 case 128:
                     switch (B)
                     {
                         case 0:
                             button_3.setSelection(true);
                             break;
                         default:
                             button_6.setSelection(true);
                             break;
                     }
                     break;
                 default:
                     button_6.setSelection(true);
                     break;
        	 }
                  break;
         case 255:
             switch (G)
             {
                 case 0:
                     switch (B)
                     {
                         case 0:
                             button_1.setSelection(true);
                             break;
                         default:
                             button_6.setSelection(true);
                             break;
                     }
                     break;
                 case 255:
                     switch (B)
                     {
                         case 255:
                             button_5.setSelection(true);
                             break;
                         default:
                             button_6.setSelection(true);
                             break;
                     }
                     break;
                 default:
                     button_6.setSelection(true);
                     break;
             }
               break;
         default:
             button_6.setSelection(true);
             break;
     }
	}
}
