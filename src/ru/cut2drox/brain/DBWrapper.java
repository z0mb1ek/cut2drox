package ru.cut2drox.brain;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxLink;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;

public class DBWrapper {
	
	final static private String APP_KEY = "we3nd71md39ebxc";
	final static private String APP_SECRET = "e4tc3fheligb6af";
	final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;

	DropboxAPI<WebAuthSession> mDB;
	Config conf;
	
	Shell parent;
	
	public DBWrapper(Shell parent) throws MalformedURLException, DropboxException, IOException, URISyntaxException
	{
		this.parent=parent;
		conf = new Config().takeConfig();
		if(conf.getFirstConnectToDB()==0)
		{
			firstConnect();
		} else
		{
			Connect();
		}
	}
	
	void firstConnect() throws DropboxException, MalformedURLException, IOException, URISyntaxException
	{
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
        WebAuthInfo authInfo = session.getAuthInfo();
        RequestTokenPair pair = authInfo.requestTokenPair;
        String url = authInfo.url;
        
        Desktop.getDesktop().browse(new URL(url).toURI());		//посмотреть вариант на СВТ!!!!!!
        //JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        MessageBox dialog = new MessageBox(parent,SWT.OK | SWT.ICON_INFORMATION);
        dialog.setMessage("После авторизации нажмите ОК, чтобы продолжить");
        dialog.open();
      	session.retrieveWebAccessToken(pair);
      	AccessTokenPair tokens = session.getAccessTokenPair();
      	
      	conf.setAppKey(tokens.key);
      	conf.setAppSecret(tokens.secret);
      	conf.setFirstConnectToDB(1);
      	conf.makeConfig();
      	
      	this.mDB = new DropboxAPI<WebAuthSession>(session);
	}
	
	void Connect()
	{
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
        AccessTokenPair access = new AccessTokenPair(conf.getAppKey(),conf.getAppSecret());
        session.setAccessTokenPair(access);
        
        this.mDB = new DropboxAPI<WebAuthSession>(session);
	}
	
	public String sendImage(String fullPath,String name) throws DropboxException, IOException
	{
		File f = new File(fullPath);
		InputStream input = new FileInputStream(f);
		long length = f.length();
        Entry newEntry = mDB.putFile("/images/"+name,input, length, null, null);
        //DropboxLink dl =  mDB.share("/images/"+name);
        DropboxLink dl =  mDB.media("/images/"+name,false);
        input.close();
        f.delete();
        return dl.url;
	}
	
	public String sendFile(ByteArrayInputStream inputStream,long lenght,String name) throws DropboxException
	{
		Entry newEntry = mDB.putFile("/cover/"+name, inputStream, lenght, null, null);
		//DropboxLink dl =  mDB.share("/cover/"+name);
		DropboxLink dl =  mDB.media("/cover/"+name,false);
        return dl.url;
	}
	
}
