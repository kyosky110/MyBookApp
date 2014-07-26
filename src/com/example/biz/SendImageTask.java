package com.example.biz;

import java.io.File;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

import android.os.AsyncTask;

public class SendImageTask extends AsyncTask<String, Integer, Void> {

	static String host = "bcs.duapp.com";
	static String accessKey = "P6A2eZFCjlmYReenMGoQqeWH";
	static String secretKey = "S9tpH2UHG3o4cDWs1OzCggtreUTIVhC4";
	static String bucket = "bookbucket";
	// ----------------------------------------
	static String object ;
	private String objectName = "/bimage/";
	private static String name;
	private static File file;
	@Override
	protected Void doInBackground(String... params) {
		object = null;
		file = new File(params[0]);
		name = params[1];
		object = objectName+name;
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
	
			// -------------bucket-------------
			// listBucket(baiduBCS);
			// createBucket(baiduBCS);
			// deleteBucket(baiduBCS);
			// getBucketPolicy(baiduBCS);
			// putBucketPolicyByPolicy(baiduBCS);
			// putBucketPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.PublicControl);
			// listObject(baiduBCS);
			// ------------object-------------
			putObjectByFile(baiduBCS);
			// putObjectByInputStream(baiduBCS);
			// getObjectWithDestFile(baiduBCS);
			// putSuperfile(baiduBCS);
			// deleteObject(baiduBCS);
			// getObjectMetadata(baiduBCS);
			// setObjectMetadata(baiduBCS);
			// copyObject(baiduBCS, bucket, object + "_copy" +
			// (System.currentTimeMillis()));
			// getObjectPolicy(baiduBCS);
			// putObjectPolicyByPolicy(baiduBCS);
			// putObjectPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.PublicControl);

			// ------------common------------------
			// generateUrl(BaiduBCS baiduBCS);
	
		return null;
	}
	
	public static void putObjectByFile(BaiduBCS baiduBCS) {
		PutObjectRequest request = new PutObjectRequest(bucket, object, file);
		ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setContentType("text/html");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
	}
}
