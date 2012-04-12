package ru.cut2drox.brain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;


public class Config {
	
	int firstConnectToDB;
	String app_key;
	String app_secret;
//	String[] test = {"qwe","qweqw"};
	
	Config(){
		firstConnectToDB=0;
		app_key="";
		app_secret="";
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
	
//	 public static void main(String arg[]) throws IOException
//	    {
//	    	Config c = new Config();
//	    	DumperOptions options = new DumperOptions();
//	        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//	    	Yaml yaml = new Yaml(options);
//	    	 //OutputStream input = new FileOutputStream(new File("D:/object.yaml"));
//	    	// StringWriter writer = new StringWriter();
//			
//			//System.out.println(yaml.dump(c));
//			BufferedWriter out = new BufferedWriter(new FileWriter("D:/object.yaml"));
//			out.write(yaml.dump(c));
//			out.close();
////	    	 InputStream input = new FileInputStream(new File("D:/object.yaml"));
////	    	 c=(Config) yaml.load(input);
////	    	 System.out.println(c.getAppKey());
////	    	 System.out.println(c.getFirstConnectToDB());
////	    	 System.out.println(c.getAppSecret());
//	    }
	
	public void makeConfig(String path) throws IOException
	{
    	DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    	Yaml yaml = new Yaml(options);
    	
		BufferedWriter out = new BufferedWriter(new FileWriter(path));
		out.write(yaml.dump(this));
		out.close();
	}
	
	public Config takeConfig(String path) throws FileNotFoundException
	{
		Config c = new Config();
		Yaml yaml = new Yaml();
		InputStream input = new FileInputStream(new File(path));
		c=(Config) yaml.load(input);
		return c;
	}
	
}
