package ru.cut2drox.ui;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
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

public class TextLabel extends Dialog {

	protected Object result;
	protected Shell shell;
	protected Shell parent;
	private Text text;
	final Font[] font = new Font[1];

	public TextLabel(Shell parent, int style) {
		super(parent, style);
		this.parent=parent;
		setText("SWT Dialog");
	}

	public Object open(final ResizableCanvas canvas,final Image image) {
		Display display = getParent().getDisplay();
		createContents(canvas,image,display);
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void createContents(final ResizableCanvas canvas,final Image image,final Display display) {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shell.setSize(303, 182);
		shell.setText(getText());
		final FontDialog fd = new FontDialog(shell);
		
		Rectangle client=shell.getBounds();
		Rectangle par=parent.getBounds();
		client.x=(par.x+par.width/2)-client.width/2;
		client.y=(par.y+par.height/2)-client.height/2;
		shell.setBounds(client);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
			}
		});
		button.setBounds(154, 119, 75, 25);
		button.setText("Отмена");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				shell.dispose();
				//canvas.drawRText("looool",se.x,se.y-17);
				//-----------------------------------
				Listener listener = new Listener() {
		            public void handleEvent(Event e) {
		      		switch (e.type) {
		      			case SWT.MouseEnter:
		      				canvas.drawRText("looool",e.x,e.y-17);
		      				break;
		      			case SWT.MouseDown:
		      				//Font font = new Font(display,"Arial",14,SWT.BOLD | SWT.ITALIC); 
		      				GC gc = new GC(image);
		      				gc.setFont(font[0]); 
		      				gc.drawText("looool", e.x+canvas.gettest(),e.y-17,true);
		      				gc.dispose();
		      				
		      				break;
		      			case SWT.MouseMove:
		      				canvas.drawRText("looool",e.x,e.y-17);
		      				break;
		      			case SWT.MouseUp:
		      				canvas.removeListener(SWT.MouseMove, this);
		      				canvas.removeListener(SWT.MouseDown, this);
		      				canvas.removeListener(SWT.MouseUp, this);
		      				canvas.removeListener(SWT.MouseEnter, this);
		      	  	      	break;
		      			}
		            }
		        };
		        canvas.addListener(SWT.MouseDown, listener);
		        canvas.addListener(SWT.MouseMove, listener);
		        canvas.addListener(SWT.MouseUp, listener);
		        canvas.addListener(SWT.MouseEnter, listener);
		        //-------------------------------------
			}
		});
		btnNewButton.setBounds(73, 119, 75, 25);
		btnNewButton.setText("ОК");
		
		text = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		text.setBounds(10, 40, 277, 73);
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(10, 19, 33, 15);
		label.setText("Текст:");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(57, 11, 91, 15);
		label_1.setText("Размер шрифта:");
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setBounds(150, 8, 42, 23);
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FontData defaultFont = new FontData("Courier", 10, SWT.BOLD);
				System.out.println(defaultFont.getName());
				System.out.println(defaultFont.getHeight());
				System.out.println(defaultFont.getStyle());
		        fd.setFontData(defaultFont);
		        FontData newFont = fd.open();
		        if (newFont == null) return;
		        font[0]=new Font(display, newFont);
	            
			}
		});
		btnNewButton_1.setBounds(212, 8, 75, 23);
		btnNewButton_1.setText("Шрифт");

	}
}
