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
	// ����Web Service�������ռ�
	static final String SERVICE_NS = "http://tempuri.org/";
	// ����Web Service�ṩ�����URL
	static final String SERVICE_URL = "http://192.169.1.115./service.asmx";

	public static List<String> doSql(String sql) {
		final String methodName = "EXEC_SQL";
		// ����HttpTransportSE�������
		final HttpTransportSE ht = new HttpTransportSE(SERVICE_NS);
		ht.debug = true;
		// ʹ��SOAP1.1Э�鴴��Envelop����
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// ʵ����SoapObject����
		SoapObject soapobject = new SoapObject(SERVICE_NS, methodName);
		soapobject.addProperty("sqlwords", sql);
		envelope.bodyOut = soapobject;
		// ������.Net�ṩ��Web Service���ֽϺõļ�����
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
							return parseDetail(detail);//����
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
			// ������ÿ��ʡ��
			result.add(detail.getProperty(i).toString().split(",")[0]);
		}
		return result;

	}
}
