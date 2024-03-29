package vn.edu.vnua.dse.stcalendar.ggcalendar.wrapperapi;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import vn.edu.vnua.dse.stcalendar.exceptions.CustomException;


public class APIService {
	public static String GetResult(String url, String accessToken) {
		try {
			return Request.Get(url)
					.setHeader("Accept-Charset", "utf-8")
					.setHeader("Authorization", "Bearer " + accessToken)
					.execute().returnContent().asString();
		} catch (ClientProtocolException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi get api: " + e.getMessage());
		} catch (IOException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi get api: " + e.getMessage());
		}
		
		return null;
	}

	public static String DeleteResult(String url, String accessToken) {
		try {
			return Request.Delete(url)
					.setHeader("Accept-Charset", "utf-8")
					.setHeader("Authorization", "Bearer " + accessToken)
					.execute().returnContent().asString();
		} catch (ClientProtocolException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Delete api: " + e.getMessage());
		} catch (IOException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Delete api: " + e.getMessage());
		}catch (Exception e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Delete api: " + e.getMessage());
		}
		return null;
	}
	
	public static String PostResult(String url, List<NameValuePair> form) {
		try {
			return Request.Post(url).bodyForm(form).execute().returnContent().asString();
		} catch (ClientProtocolException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
			//throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Post api: PostResult(String url, List<NameValuePair> form)," + e.getMessage());

		} catch (IOException e) {
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Post api: PostResult(String url, List<NameValuePair> form), " + e.getMessage());
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}
	//Request URL:https://content.googleapis.com/calendar/v3/calendars?alt=json&key=AIzaSyAa8yy0GdcGPHdtD083HiGGx_S0vMPScDM
	public static String PostResult(String url, String accessToken, String json) {
		try {
			return Request.Post(url).addHeader("Authorization", "OAuth " + accessToken)
					.bodyString(json, ContentType.APPLICATION_JSON)
					.execute().returnContent().asString();
		} catch (ClientProtocolException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Post api: PostResult(String url, String accessToken, String json)," + e.getMessage());
		} catch (IOException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Post api: PostResult(String url, String accessToken, String json), " + e.getMessage());
		}
		return null;
	}
	
	public static String PutResult(String url, String accessToken, String json) {
		try {
			return Request.Put(url).addHeader("Authorization", "OAuth " + accessToken)
					.bodyString(json, ContentType.APPLICATION_JSON)
					.execute().returnContent().asString();
		} catch (ClientProtocolException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Put api: " + e.getMessage());
		} catch (IOException e) {
			Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, e);
//			throw new CustomException(APIService.class.getName() + "->" + "Lỗi khi Put api: " + e.getMessage());
		}
		return null;
	}
}
