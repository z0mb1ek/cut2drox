package ru.cut2drox.brain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wb.swt.SWTResourceManager;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;


public class Config {
	
	int firstConnectToDB;
	String app_key;
	String app_secret;
	String font;
	int fontSize;
	int fontStyle;
	int R;
	int G;
	int B;
	int width;
	int height;
	
	String path=ClassLoader.getSystemResource(".").getPath()+"conf.yaml";
//	String[] test = {"qwe","qweqw"};
	
	public Config(){
		firstConnectToDB=0;
		app_key="";
		app_secret="";
		font="";
		fontSize=0;
		fontStyle=0;
		R=0;
		G=0;
		B=0;
		width=0;
		height=0;
	}
	
	public void setHeight(int h)
	{
		this.height=h;
	}
	public int getHeight()
	{
		return height;
	}
	public void setWidth(int w)
	{
		this.width=w;
	}
	public int getWidth()
	{
		return width;
	}
	public void setR(int r)
	{
		this.R=r;
	}
	public int getR()
	{
		return R;
	}
	public void setG(int g)
	{
		this.G=g;
	}
	public int getG()
	{
		return G;
	}
	public void setB(int b)
	{
		this.B=b;
	}
	public int getB()
	{
		return B;
	}
	public void setFontStyle(int style)
	{
		this.fontStyle=style;
	}
	
	public int getFontStyle()
	{
		return fontStyle;
	}
	
	public void setFontSize(int size)
	{
		this.fontSize=size;
	}
	
	public int getFontSize()
	{
		return fontSize;
	}
	
	public String getFont()
	{
		return font;
	}
	public void setFont(String font)
	{
		this.font=font;
	}
	
	public int getFirstConnectToDB()
	{
		return firstConnectToDB;
	}
	
//	public String[] getTest()
//	{
//		return test;
//	}
//	
//	public void setTest(String[] tt)
//	{
//		test=tt;
//	}
	
	public void setFirstConnectToDB(int param)
	{
		this.firstConnectToDB=param;
	}
	
	public String getAppKey()
	{
		return app_key;
	}
	
	public void setAppKey(String key)
	{
		this.app_key=key;
	}
	
	public String getAppSecret()
	{
		return app_secret;
	}
	
	public void setAppSecret(String secret)
	{
		this.app_secret=secret;
	}
	
	 public static void main(String arg[]) throws IOException
	    {
	    	Config c = new Config();
	    	c.makeConfig();
	    }
	
	void makeConf(String path) throws IOException
	{
    	DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    	Yaml yaml = new Yaml(options);
    	
		BufferedWriter out = new BufferedWriter(new FileWriter(path));
		out.write(yaml.dump(this));
		out.close();
	}
	
	public Config takeConfig() throws FileNotFoundException
	{
		Config c = new Config();
		Yaml yaml = new Yaml();
		InputStream input = new FileInputStream(new File(path));
		c=(Config) yaml.load(input);
		return c;
	}
	
	public void makeConfig() throws IOException
	{
		makeConf(path);
	}
	
}
