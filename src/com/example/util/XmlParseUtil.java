package com.example.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.example.bean.Book;
import com.example.bean.LoginBean;
import com.example.bean.UpdateBean;

public class XmlParseUtil {
	/**��¼ע����Ϣ�Ľ���*/
	public static LoginBean getLoginInfo(InputStream in){
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "utf-8");
			LoginBean bean = null;
			for(int type = parser.getEventType();type != XmlPullParser.END_DOCUMENT;type = parser.next()){
				if(type==XmlPullParser.START_TAG){
					if("muser".equals(parser.getName())){
						bean = new LoginBean();
					}else if("u_id".equals(parser.getName())){
						bean.setU_id(Integer.parseInt(parser.nextText().trim()));
					}else if("username".equals(parser.getName())){
						bean.setUsername(parser.nextText());
					}else if("password".equals(parser.getName())){
						bean.setPassword(parser.nextText());
					}
				}
			}
			return bean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static Book getAddBook(InputStream in){
		
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "utf-8");
			Book book = null;
			for(int type = parser.getEventType();type != XmlPullParser.END_DOCUMENT;type = parser.next()){
				if(type==XmlPullParser.START_TAG){
					if("book".equals(parser.getName())){
						book = new Book();
					}else if("id".equals(parser.getName())){
						book.setId(Integer.parseInt(parser.nextText().trim()));
					}
				}
			}
			return book;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static List<Book> getBooks(InputStream in){
		XmlPullParser parser = Xml.newPullParser();
		List<Book> list = new ArrayList<Book>();
		try {
			parser.setInput(in, "utf-8");
			Book book = null;
			for(int type = parser.getEventType();type != XmlPullParser.END_DOCUMENT;type = parser.next()){
				if(type==XmlPullParser.START_TAG){
					if("book".equals(parser.getName())){
						book = new Book();
					}else if("id".equals(parser.getName())){
						book.setId(Integer.parseInt(parser.nextText().trim()));
					}else if("title".equals(parser.getName())){
						book.setTitle(parser.nextText());
					}else if("content".equals(parser.getName())){
						book.setContent(parser.nextText());
					}else if("username".equals(parser.getName())){
						book.setUsername(parser.nextText());
					}else if("content".equals(parser.getName())){
						book.setContent(parser.nextText());
					}else if("author".equals(parser.getName())){
						book.setAuthor(parser.nextText());
					}else if("image".equals(parser.getName())){
						book.setImage(parser.nextText());
					}else if("classify".equals(parser.getName())){
						book.setClassify((Integer.parseInt(parser.nextText())));
					}else if("islend".equals(parser.getName())){
						book.setIslend((Integer.parseInt(parser.nextText())));
//						list.add(book);
					}else if("p_num".equals(parser.getName())){
						book.setP_num(parser.nextText());
						list.add(book);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	public static UpdateBean getServerVersionInfo(InputStream in){
		XmlPullParser parser = Xml.newPullParser();
		UpdateBean update = null;
		try {
			parser.setInput(in, "utf-8");
			int type = parser.getEventType();
			while(type!=XmlPullParser.END_DOCUMENT){
				if(type==XmlPullParser.START_TAG){
					if("info".equals(parser.getName())){
						update = new UpdateBean();
					}else if("version".equals(parser.getName())){
						update.setVersion(parser.nextText());
					}else if("description".equals(parser.getName())){
						update.setDescription(parser.nextText());
					}else if("apkurl".equals(parser.getName())){
						update.setApkurl(parser.nextText());
					}
				}
				type = parser.next();
			}
//			for(int type = parser.getEventType();type != XmlPullParser.END_DOCUMENT;type = parser.next()){
//				if(type==XmlPullParser.START_TAG){
//					if("info".equals(parser.getName())){
//						update = new UpdateBean();
//					}else if("version".equals(parser.getName())){
//						update.setVersion(parser.getText());
//					}else if("description".equals(parser.getName())){
//						update.setDescription(parser.getText());
//					}else if("apkurl".equals(parser.getName())){
//						update.setApkurl(parser.getText());
//					}
//				}
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return update;
	}

}
