package ru.cut2drox.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;
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
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Point;

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
	int tempCircleStartX = 0;
	int tempCircleStartY = 0;
	int tempSquareStartX = 0;
	int tempSquareStartY = 0;
	
	Listener printArrowListener;
	Listener printCircleListener;
	Listener printSquareListener;
	
	Image currentImage;
	
	Stack<Image> images;
	
	ResizableCanvas canvas;
	
	String picPath = ClassLoader.getSystemResource(".").getPath()+"pic\\";
	
	public static void main(String[] args) {
		display = new Display();
	    shell = new Shell(display);
	    
	    ti = new TrayIcon(display,shell);

	    while (!shell.isDisposed()) {
	        if (!display.readAndDispatch())
	          display.sleep();
	      }
	    display.dispose(); 
	    System.exit(0);
	}
	
	public static void closeApp()
	{
		shell.dispose();
	}
	
	/**
	// * @param tip
	 * @wbp.parser.entryPoint
	 */
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

		currentImage = image;
		images=new Stack<Image>();
		
		shellForm = new Shell();
		shellForm.setMinimumSize(new Point(277, 285));
		shellForm.setSize(378, 352);
		shellForm.setText("Cut2drox");
		shellForm.setLayout(new GridLayout(2, false));
		
		Monitor[] list = display.getMonitors();
		org.eclipse.swt.graphics.Rectangle client = shellForm.getBounds();
		org.eclipse.swt.graphics.Rectangle screen = list[0].getBounds();
		try {
			Config conf=new Config().takeConfig();
			client.width=conf.getWidth();
			client.height=conf.getHeight();
		} catch (FileNotFoundException e1) {e1.printStackTrace();}
//		client.width=currentImage.getBounds().width+70; //20-на левый отступ + 25 на кнопку + около 20 на скролл 
//		client.height=currentImage.getBounds().height+117;
		client.x = screen.width/2 -client.width/2;
		client.y = screen.height/2 - client.height/2;
		shellForm.setBounds(client);
		
		Composite composite_1 = new Composite(shellForm, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 211;
		gd_composite_1.heightHint = 22;
		composite_1.setLayoutData(gd_composite_1);

		btnNewButton_3 = new Button(composite_1, SWT.TOGGLE); //группа цветов
		btnNewButton_3.setToolTipText("черный цвет");
		button_1 = new Button(composite_1, SWT.TOGGLE);
		button_1.setToolTipText("красный цвет");
		button_3 = new Button(composite_1, SWT.TOGGLE);
		button_3.setToolTipText("зеленый цвет");
		button_4 = new Button(composite_1, SWT.TOGGLE);
		button_4.setToolTipText("синий цвет");
		button_5 = new Button(composite_1, SWT.TOGGLE);
		button_5.setToolTipText("белый цвет");
		button_6 = new Button(composite_1, SWT.TOGGLE);
		button_6.setToolTipText("выбрат цвет из палитры");
		
        setColorButton();
        
        printArrowListener=new Listener(){public void handleEvent(Event arg0) {}};
        printCircleListener=new Listener(){public void handleEvent(Event arg0) {}};
        printSquareListener=new Listener(){public void handleEvent(Event arg0) {}};
        
        

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
		button_2.setToolTipText("Отменить действие");
		button_2.setImage(SWTResourceManager.getImage(picPath+"undo.png"));
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(!images.isEmpty())
				{
					Image temp = (Image) images.pop();
					canvas.setImage(temp);
					currentImage = temp;
					canvas.redraw();
				}
			}
		});
		
		GridData gd_button_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_2.widthHint = 25;
		button_2.setLayoutData(gd_button_2);
		button_2.setText("");
		
		canvas = new ResizableCanvas(shellForm,  SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL  | SWT.H_SCROLL | SWT.DOUBLE_BUFFERED,display);
		
		canvas.setImage(currentImage);
		GridData gd_canvas = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_canvas.heightHint = 1;
		gd_canvas.widthHint = 1;
		canvas.setLayoutData(gd_canvas);

		canvas.addScrolling();

		shellForm.addListener (SWT.Resize,  new Listener () {
		    public void handleEvent (Event e) {
		    	int i=shellForm.getClientArea().width-38;
		    	int j=shellForm.getClientArea().height-62;
		    	int posx=5;
		    	int posy=30;
		    if(i>currentImage.getBounds().width+15)
		    {
		    	posx=(i-currentImage.getBounds().width)/2;
		    	i=currentImage.getBounds().width+17;
		    	
		    }
		    if(j>currentImage.getBounds().height+15)
		    {
		    	posy=(j-currentImage.getBounds().height+45)/2;
		    	j=currentImage.getBounds().height+17;
		    }
		    	canvas.setRBounds(posx, posy, i, j);
		    	try {
					Config conf=new Config().takeConfig();
					conf.setWidth(shellForm.getBounds().width);
					conf.setHeight(shellForm.getBounds().height);
					conf.makeConfig();
				} catch (FileNotFoundException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();}
		    }  
		});
		
		
		
		Composite composite = new Composite(shellForm, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_composite.heightHint = 177;
		composite.setLayoutData(gd_composite);
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setToolTipText("Сделать надпись");
		final Button btnNewButton_2 = new Button(composite, SWT.TOGGLE);
		btnNewButton_2.setToolTipText("Нанести стрелку");
		final Button button = new Button(composite, SWT.TOGGLE);
		button.setToolTipText("Нарисовать овал");
		final Button button_7 = new Button(composite, SWT.TOGGLE);
		button_7.setToolTipText("Нарисовать прямоугольник");
		
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.removeListener(SWT.MouseDown, printArrowListener);
				canvas.removeListener(SWT.MouseUp, printArrowListener);
				canvas.removeListener(SWT.MouseDown, printCircleListener);
				canvas.removeListener(SWT.MouseUp, printCircleListener);
				canvas.removeListener(SWT.MouseDown, printSquareListener);
				canvas.removeListener(SWT.MouseUp, printSquareListener);
				
				btnNewButton_2.setSelection(false);
				button_7.setSelection(false);
				button.setSelection(false);
				TextLabel tl = new TextLabel(shellForm,SWT.DIALOG_TRIM);
				try {
					tl.open(canvas,currentImage,images); 
					} catch (FileNotFoundException e) {e.printStackTrace();}
			}
		});
		btnNewButton_1.setImage(SWTResourceManager.getImage(picPath+"pencil.png"));
		btnNewButton_1.setBounds(0, 0, 25, 25);
		btnNewButton_1.setText("");
		

		btnNewButton_2.setImage(SWTResourceManager.getImage(picPath+"arrow.png"));
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.removeListener(SWT.MouseDown, printCircleListener);
				canvas.removeListener(SWT.MouseUp, printCircleListener);
				canvas.removeListener(SWT.MouseDown, printSquareListener);
				canvas.removeListener(SWT.MouseUp, printSquareListener);
				btnNewButton_2.setSelection(true);
				button.setSelection(false);
				button_7.setSelection(false);
				printArrowListener = new Listener() {
					
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
								if(tempLineStartX!=e.x || tempLineStartY!=e.y)
								{
									images.push(new Image(display,currentImage,SWT.IMAGE_COPY));
									GC gc = new GC(currentImage);
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
				canvas.addListener(SWT.MouseDown, printArrowListener);
				canvas.addListener(SWT.MouseUp, printArrowListener);
			}
		});
		btnNewButton_2.setBounds(0, 31, 25, 25);
		btnNewButton_2.setText("");
		
		
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.removeListener(SWT.MouseDown, printArrowListener);
				canvas.removeListener(SWT.MouseUp, printArrowListener);
				canvas.removeListener(SWT.MouseDown, printSquareListener);
				canvas.removeListener(SWT.MouseUp, printSquareListener);
				button.setSelection(true);
				btnNewButton_2.setSelection(false);
				button_7.setSelection(false);
				printCircleListener = new Listener() {
					public void handleEvent(Event e) {
						switch (e.type) {
							case SWT.MouseDown:
								tempCircleStartX=e.x;
								tempCircleStartY=e.y;
								canvas.addListener(SWT.MouseMove, this);
								break;
							case SWT.MouseMove:
								canvas.drawROval(tempCircleStartX, tempCircleStartY, e.x-tempCircleStartX, e.y-tempCircleStartY);
								break;
							case SWT.MouseUp:
								canvas.drawROval(0, 0, 0, 0);
								canvas.removeListener(SWT.MouseMove, this);
								if(tempCircleStartX!=e.x || tempCircleStartY!=e.y)
								{
									images.push(new Image(display,currentImage,SWT.IMAGE_COPY));
									GC gc = new GC(currentImage);
									gc.setAntialias(SWT.ON); 		//СГЛАЖИВАНИЕ!!!
									try {
										Config conf=new Config().takeConfig();
										gc.setForeground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));  //цвет 
										gc.setBackground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));
									} catch (FileNotFoundException e1) {e1.printStackTrace();}
									gc.setLineWidth(3);
									gc.drawOval(tempCircleStartX-canvas.canvasShiftX, tempCircleStartY-canvas.canvasShiftY, e.x-tempCircleStartX, e.y-tempCircleStartY);
									gc.dispose();	
								}
								break;
						}
					}
				};
				canvas.addListener(SWT.MouseDown, printCircleListener);
				canvas.addListener(SWT.MouseUp, printCircleListener);
				
			}
		});
		button.setText("");
		button.setImage(SWTResourceManager.getImage(picPath+"circle.png"));
		button.setBounds(0, 62, 25, 25);
		
		
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.removeListener(SWT.MouseDown, printArrowListener);
				canvas.removeListener(SWT.MouseUp, printArrowListener);
				canvas.removeListener(SWT.MouseDown, printCircleListener);
				canvas.removeListener(SWT.MouseUp, printCircleListener);
				
				button_7.setSelection(true);
				btnNewButton_2.setSelection(false);
				button.setSelection(false);
				
				printSquareListener = new Listener() {
					public void handleEvent(Event e) {
						switch (e.type) {
							case SWT.MouseDown:
								tempSquareStartX=e.x;
								tempSquareStartY=e.y;
								canvas.addListener(SWT.MouseMove, this);
								break;
							case SWT.MouseMove:
								canvas.drawRRectangle(tempSquareStartX, tempSquareStartY, e.x-tempSquareStartX, e.y-tempSquareStartY);
								break;
							case SWT.MouseUp:
								canvas.drawRRectangle(0, 0, 0, 0);
								canvas.removeListener(SWT.MouseMove, this);
								if(tempSquareStartX!=e.x || tempSquareStartY!=e.y)
								{
									images.push(new Image(display,currentImage,SWT.IMAGE_COPY));
									GC gc = new GC(currentImage);
									gc.setAntialias(SWT.ON); 		//СГЛАЖИВАНИЕ!!!
									try {
										Config conf=new Config().takeConfig();
										gc.setForeground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));  //цвет 
										gc.setBackground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));
									} catch (FileNotFoundException e1) {e1.printStackTrace();}
									gc.setLineWidth(3);
									gc.drawRectangle(tempSquareStartX-canvas.canvasShiftX, tempSquareStartY-canvas.canvasShiftY, e.x-tempSquareStartX, e.y-tempSquareStartY);
									gc.dispose();	
								}
								break;
						}
					}
				};
				canvas.addListener(SWT.MouseDown, printSquareListener);
				canvas.addListener(SWT.MouseUp, printSquareListener);
				
			}
		});
		button_7.setText("");
		button_7.setImage(SWTResourceManager.getImage(picPath+"square.png"));
		button_7.setBounds(0, 93, 25, 25);
		
		Composite composite_2 = new Composite(shellForm, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
		gd_composite_2.widthHint = 119;
		gd_composite_2.heightHint = 27;
		composite_2.setLayoutData(gd_composite_2);
		
		Button btnNewButton = new Button(composite_2, SWT.NONE);
		btnNewButton.setBounds(0, 0, 70, 25);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shellForm.close();
				try {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
					ImageLoader loader = new ImageLoader();
					loader.data = new ImageData[] {currentImage.getImageData()};
					String partName = sdf.format(cal.getTime());
					String nameImage = partName +".jpg";
					String fullPath = ClassLoader.getSystemResource(".").getPath()+nameImage;
					
//					File myFolder = new File(ClassLoader.getSystemResource(".").getPath()+"images");		//пока папка не нужна
//					myFolder.mkdir();
					
					loader.save(fullPath, SWT.IMAGE_PNG);
					DBWrapper connection = new DBWrapper();
					String imageShortURL = connection.sendImage(fullPath, nameImage);
					
//					
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
					
					ti.showBalloon(shell,"Файл загружен.Ссылка скопирована в буфер обмена");
		  
				} catch (MalformedURLException e) {e.printStackTrace();} catch (DropboxException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();} catch (URISyntaxException e) {e.printStackTrace();}
			}
		});
		btnNewButton.setText("\u041E\u0442\u043F\u0440\u0430\u0432\u0438\u0442\u044C");
		
		Button button_8 = new Button(composite_2, SWT.NONE);
		button_8.setToolTipText("Сохранить рисунок на диск");
		button_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
			    dialog.setFilterNames(new String[] { "JPEG images" });
			    dialog.setFilterExtensions(new String[] { "*.jpg"});
			    String path =dialog.open();
			    if(path!=null)
			    {
			    	ImageLoader loader = new ImageLoader();
			    	loader.data = new ImageData[] {currentImage.getImageData()};
			    	loader.save(dialog.getFilterPath()+ "\\"+dialog.getFileName(), SWT.IMAGE_PNG);
			    	ti.showBalloon(shell,"Файл сохранен на жесткий диск.");
			    }
			    
			}
		});
		button_8.setText("");
		button_8.setImage(SWTResourceManager.getImage(picPath+"save.png"));
		button_8.setBounds(69, 0, 25, 25);
		
		Button button_9 = new Button(composite_2, SWT.NONE);
		button_9.setToolTipText("Копировать рисунок в буффер обмена");
		button_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Clipboard cl = new Clipboard(Display.getDefault()); 
				cl.setContents(new Object[]{currentImage.getImageData()}, new 
				Transfer[]{ImageTransfer.getInstance()}); 
				ti.showBalloon(shell,"Файл скопирован в буфер обмена.");
			}
		});
		button_9.setText("");
		button_9.setImage(SWTResourceManager.getImage(picPath+"copy.png"));
		button_9.setBounds(93, 0, 25, 25);

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
