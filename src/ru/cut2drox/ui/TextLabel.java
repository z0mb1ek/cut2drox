package ru.cut2drox.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

import ru.cut2drox.brain.Config;

public class TextLabel extends Dialog {

	protected Object result;
	protected Shell shlEe;
	protected Shell parent;
	private Text text;
	Font font;
	int fontSize;
	String[] items = new String[]{"5", "6", "7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
	
	FontData loadFont(Display display) throws FileNotFoundException
	{
		Config conf=new Config().takeConfig();
		FontData defaultFont = new FontData(conf.getFont(), conf.getFontSize(),conf.getFontStyle());
		fontSize=conf.getFontSize();
		font=new Font(display, defaultFont);
		return defaultFont;
	}
	
//	void setFont(FontData newFont) throws IOException
//	{
//		Config conf=new Config().takeConfig();
//		conf.setFont(newFont.getName());
//		conf.setFontSize(newFont.getHeight());
//		fontSize=newFont.getHeight();
//		conf.setFontStyle(newFont.getStyle());
//		conf.makeConfig();
//	}
	
	void setFontSize() throws IOException
	{
		Config conf=new Config().takeConfig();
		conf.setFontSize(fontSize);
		conf.makeConfig();
	}

	public TextLabel(Shell parent, int style) {
		super(parent, style);
		this.parent=parent;
		setText("SWT Dialog");
	}
	
	/**
	 * @param tip 
	 * @wbp.parser.entryPoint
	 */

	public Object open(final ResizableCanvas canvas,final Image image) throws FileNotFoundException {
		Display display = getParent().getDisplay();
		createContents(canvas,image,display);
		shlEe.open();
		shlEe.layout();
		
		while (!shlEe.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void createContents(final ResizableCanvas canvas,final Image image,final Display display) throws FileNotFoundException {
		shlEe = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shlEe.setSize(303, 182);
		shlEe.setText("Текстовая метка");
		final FontDialog fd = new FontDialog(shlEe);
		fd.setFontData(loadFont(display));
		
		Rectangle client=shlEe.getBounds();
		Rectangle par=parent.getBounds();
		client.x=(par.x+par.width/2)-client.width/2;
		client.y=(par.y+par.height/2)-client.height/2;
		shlEe.setBounds(client);
		
		Button button = new Button(shlEe, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEe.dispose();
			}
		});
		button.setBounds(154, 119, 75, 25);
		button.setText("Отмена");
		
		text = new Text(shlEe, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		text.setBounds(10, 40, 277, 73);
		
		//TODO: сделать проверку на ввод только цифр
				final Combo combo = new Combo(shlEe, SWT.NONE);
				combo.setBounds(150, 8, 56, 23);
				combo.setItems(items);
				combo.setText(Integer.toString(fontSize));
				combo.addListener(SWT.Modify, new Listener() {
				      public void handleEvent(Event e) {
				    	  fontSize=Integer.parseInt(combo.getText());
				    	  try {
							setFontSize();
							loadFont(display);
						} catch (IOException e1) {e1.printStackTrace();}
				      }
				    });
		
		Button btnNewButton = new Button(shlEe, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				final String temp=text.getText();

				shlEe.dispose();
				if(!temp.isEmpty())
				{
					Listener listener = new Listener() {
						public void handleEvent(Event e) {
							switch (e.type) {
								case SWT.MouseEnter:
									canvas.drawRText(temp,e.x,e.y-17,font);
									break;
								case SWT.MouseDown:
									GC gc = new GC(image);
									gc.setFont(font);
									try {
									Config conf=new Config().takeConfig();
									gc.setForeground(SWTResourceManager.getColor(new RGB(conf.getR(),conf.getG(),conf.getB())));  //цвет 
									} catch (FileNotFoundException e1) {e1.printStackTrace();}
									gc.drawText(temp, e.x-canvas.canvasShiftX,e.y-17-canvas.canvasShiftY,true);
									gc.dispose();	      				
									break;
								case SWT.MouseMove:
									canvas.drawRText(temp,e.x,e.y-17,font);
									break;
								case SWT.MouseUp:
									canvas.drawRText("",-1,-1,font); //убираем мышку с канваса
									canvas.removeListener(SWT.MouseMove, this);
									canvas.removeListener(SWT.MouseDown, this);
									canvas.removeListener(SWT.MouseUp, this);
									canvas.removeListener(SWT.MouseEnter, this);
									canvas.redraw();
									break;
							}
						}
					};
					canvas.addListener(SWT.MouseDown, listener);
					canvas.addListener(SWT.MouseMove, listener);
					canvas.addListener(SWT.MouseUp, listener);
					canvas.addListener(SWT.MouseEnter, listener);
				}
			}
		});
		btnNewButton.setBounds(73, 119, 75, 25);
		btnNewButton.setText("ОК");
		
		Label label = new Label(shlEe, SWT.NONE);
		label.setBounds(10, 19, 33, 15);
		label.setText("Текст:");
		
		Label label_1 = new Label(shlEe, SWT.NONE);
		label_1.setBounds(57, 11, 91, 15);
		label_1.setText("Размер шрифта:");
		
		
		
		
		Button btnNewButton_1 = new Button(shlEe, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					Config conf=new Config().takeConfig();
					fd.setRGB(new RGB(conf.getR(),conf.getG(),conf.getB()));
					
		        FontData newFont = fd.open();
		        if (newFont == null) return;
		        font=new Font(display, newFont);
		        RGB temp = fd.getRGB();
		        fontSize=newFont.getHeight();
		        conf.setFontSize(fontSize);
		        conf.setFont(newFont.getName());
		        conf.setFontStyle(newFont.getStyle());
		        conf.setR(temp.red);
		        conf.setG(temp.green);
		        conf.setB(temp.blue);
		        conf.makeConfig();
				} catch (FileNotFoundException e1) {e1.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		        combo.setText(Integer.toString(fontSize));
		        try {
					SendForm.setColorButton();
				} catch (FileNotFoundException e) {e.printStackTrace();}
			}
		});
		btnNewButton_1.setBounds(212, 8, 75, 23);
		btnNewButton_1.setText("Шрифт");

	}
}
