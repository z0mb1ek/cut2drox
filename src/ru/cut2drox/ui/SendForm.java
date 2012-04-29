package ru.cut2drox.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayInputStream;
import java.io.File;
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
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;

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
	static Button btnNewButton_3;
	static Button button_1;
	static Button button_3;
	static Button button_4;
	static Button button_5;
	static Button button_6;
	
	int tempLineStartX = 0;
	int tempLineStartY = 0;
	
	Listener printArrowlistener;
	
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

		btnNewButton_3 = new Button(composite_1, SWT.TOGGLE); //группа цветов
		button_1 = new Button(composite_1, SWT.TOGGLE);
		button_3 = new Button(composite_1, SWT.TOGGLE);
		button_4 = new Button(composite_1, SWT.TOGGLE);
		button_5 = new Button(composite_1, SWT.TOGGLE);
		button_6 = new Button(composite_1, SWT.TOGGLE);
        setColorButton();
        
        printArrowlistener=new Listener(){public void handleEvent(Event arg0) {}};

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
				
				ColorDialog cd = new ColorDialog(shellForm);
		        cd.setText("Выбор цвета");
		        try {
					Config conf=new Config().takeConfig();
					cd.setRGB(new RGB(conf.getR(), conf.getG(), conf.getB()));
					RGB newColor = cd.open();
					conf.setR(newColor.red);
					conf.setG(newColor.green);
					conf.setB(newColor.blue);
					conf.makeConfig();
				} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		        try {
					setColorButton();
				} catch (FileNotFoundException e) {e.printStackTrace();}
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
		button_2.setVisible(false);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
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
		gd_composite.heightHint = 177;
		composite.setLayoutData(gd_composite);
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		final Button btnNewButton_2 = new Button(composite, SWT.TOGGLE);
		
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.removeListener(SWT.MouseDown, printArrowlistener);
				canvas.removeListener(SWT.MouseUp, printArrowlistener);
				canvas.removeListener(SWT.MouseMove, printArrowlistener);
				
				btnNewButton_2.setSelection(false);
				TextLabel tl = new TextLabel(shellForm,SWT.DIALOG_TRIM);
				try {
					tl.open(canvas,image); 
					} catch (FileNotFoundException e) {e.printStackTrace();}
			}
		});
		btnNewButton_1.setImage(SWTResourceManager.getImage("D:\\pencil.png"));
		btnNewButton_1.setBounds(0, 0, 25, 25);
		btnNewButton_1.setText("");
		

		btnNewButton_2.setImage(SWTResourceManager.getImage("D:\\arrow.png"));
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				btnNewButton_2.setSelection(true);
				printArrowlistener = new Listener() {
					
					double f1x2 , f1y2, f2x2, f2y2;
					public void handleEvent(Event e) {
						switch (e.type) {
							case SWT.MouseDown:
								tempLineStartX=e.x;
								tempLineStartY=e.y;
								canvas.addListener(SWT.MouseMove, this);
								break;
							case SWT.MouseMove:
								canvas.drawRLine(tempLineStartX, tempLineStartY, e.x, e.y);
								
								double Angle=180*Math.atan2(e.y-tempLineStartY, e.x-tempLineStartX)/Math.PI;
					            f1x2 = e.x+Math.round((16*Math.cos(Math.PI*(Angle+160)/180)));
					            f1y2 = e.y+Math.round((16*Math.sin(Math.PI*(Angle+160)/180)));
					            f2x2 = e.x+Math.round((16*Math.cos(Math.PI*(Angle-160)/180)));
					            f2y2 = e.y+Math.round((16*Math.sin(Math.PI*(Angle-160)/180)));

								int[] arrow = { e.x, e.y,  (int) f2x2, (int) f2y2,(int) f1x2, (int) f1y2};
								canvas.drawRPolygon(arrow);
								break;
							case SWT.MouseUp:
								int[] temp = {0,0};
								canvas.drawRLine(0, 0, 0, 0);
								canvas.drawRPolygon(temp);
								canvas.removeListener(SWT.MouseMove, this);
								if(tempLineStartX!=e.x && tempLineStartY!=e.y)
								{
								GC gc = new GC(image);
								gc.setAntialias(SWT.ON); 		//СГЛАЖИВАНИЕ!!!
								try {
								Config conf=new Config().takeConfig();
								gc.setForeground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));  //цвет 
								gc.setBackground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));
								} catch (FileNotFoundException e1) {e1.printStackTrace();}
								gc.setLineWidth(3);
								gc.drawLine(tempLineStartX-canvas.canvasShiftX, tempLineStartY-canvas.canvasShiftY, e.x-canvas.canvasShiftX, e.y-canvas.canvasShiftY);
								int arrow1[] = {e.x-canvas.canvasShiftX, e.y-canvas.canvasShiftY,  (int) f2x2-canvas.canvasShiftX, (int) f2y2-canvas.canvasShiftY,(int) f1x2-canvas.canvasShiftX, (int) f1y2-canvas.canvasShiftY};
								gc.drawPolygon(arrow1);
								gc.fillPolygon(arrow1);
								gc.dispose();	
								}
								break;
						}
					}
				};
				canvas.addListener(SWT.MouseDown, printArrowlistener);
				canvas.addListener(SWT.MouseUp, printArrowlistener);
				
//				FocusListener fListener = new FocusListener() {
//				      public void focusGained(FocusEvent event) {}
//
//				      public void focusLost(FocusEvent event) {
//						canvas.removeListener(SWT.MouseDown, listener);
//						canvas.removeListener(SWT.MouseUp, listener);
//				      }
//				    };
//				btnNewButton_2.addFocusListener(fListener);
			}
		});
		btnNewButton_2.setBounds(0, 31, 25, 25);
		btnNewButton_2.setText("");
		
		
		
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		button.setText("");
		button.setImage(SWTResourceManager.getImage("D:\\circle.png"));
		button.setBounds(0, 62, 25, 25);
		
		Button button_7 = new Button(composite, SWT.NONE);
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		button_7.setText("");
		button_7.setImage(SWTResourceManager.getImage("D:\\square.png"));
		button_7.setBounds(0, 93, 25, 25);
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
					File myFolder = new File(ClassLoader.getSystemResource(".").getPath()+"images");
					myFolder.mkdir();
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
	
	public static void setColorButton() throws FileNotFoundException
	{
		btnNewButton_3.setSelection(false);
		button_1.setSelection(false);
		button_3.setSelection(false);
		button_4.setSelection(false);
		button_5.setSelection(false);
		button_6.setSelection(false);
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
