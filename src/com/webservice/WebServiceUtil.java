package com.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceUtil {
	// 定义Web Service的命名空间
	static final String SERVICE_NS = "http://tempuri.org/";
	// 定义Web Service提供服务的URL
	static final String SERVICE_URL = "http://192.169.1.115./service.asmx";

	public static List<String> doSql(String sql) {
		final String methodName = "EXEC_SQL";
		// 创建HttpTransportSE传输对象
		final HttpTransportSE ht = new HttpTransportSE(SERVICE_NS);
		ht.debug = true;
		// 使用SOAP1.1协议创建Envelop对象
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// 实例化SoapObject对象
		SoapObject soapobject = new SoapObject(SERVICE_NS, methodName);
		soapobject.addProperty("sqlwords", sql);
		envelope.bodyOut = soapobject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		FutureTask<List<String>> task = new FutureTask<List<String>>(
				new Callable<List<String>>() {

					@Override
					public List call() throws Exception {
						ht.call(SERVICE_NS + methodName, envelope);
						if (envelope.getResponse() != null) {
							SoapObject result = (SoapObject) envelope.bodyIn;
							SoapObject detail = (SoapObject) result.getProperty(
									methodName + "Result");
							return parseDetail(detail);//待做
						}
						return null;
					}
				});
		new Thread(task).start();
		try
		{
			return task.get();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		 
		return  null;
		
		}
	public static List<String> parseDetail(SoapObject detail){
		List<String> result=new ArrayList<String>();
		for (int i = 0; i < detail.getPropertyCount(); i++)
		{
			// 解析出每个省份
			result.add(detail.getProperty(i).toString().split(",")[0]);
		}
		return result;

	}
}
