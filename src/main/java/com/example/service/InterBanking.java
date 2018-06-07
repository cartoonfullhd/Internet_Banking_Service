package com.example.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.example.object.AnyID;

@Component
public class InterBanking
{
	
	String ip = "http://localhost:8090/";
	AnyID anyID = null;
	
	public Integer checkAnyID(String type, String value)
	{
		URL url;
        HttpURLConnection urlConnection = null;
        String line;
        Integer status = 0;
        String JSONResult = "";
        String urlParameters  = "?type=" + type + "&value=" + value;
        try
        {
            url = new URL(ip + "interbank/any-id/" + urlParameters);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //InputStream decode urlConnection to Byte format
            InputStream Byte = urlConnection.getInputStream();
            //InputStreamReader decode Byte format to Char format
            InputStreamReader Char = new InputStreamReader(Byte);
            BufferedReader reader = new BufferedReader(Char);
            StringBuilder builder = new StringBuilder();
            status = urlConnection.getResponseCode();

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            JSONResult = builder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
        
        try
        {
        	JSONObject APIDObj = new JSONObject(JSONResult);
        	anyID = new AnyID(APIDObj.optString("AIPID" , null), APIDObj.optString("IDValue", null), APIDObj.optString("IDType", null), APIDObj.optString("BankCode", null), APIDObj.optString("Status", null), APIDObj.optString("AccountID", null), APIDObj.optString("AccountName", null), APIDObj.optString("RegisterDTM", null));
        	
        }
        catch (JSONException e)
        {
        	// TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return anyID;
        return status;
	}
	
	public AnyID getAnyID(String type, String value)
	{
		URL url;
        HttpURLConnection urlConnection = null;
        String line;
        String JSONResult = "";
        String urlParameters  = "?type=" + type + "&value=" + value;
        try
        {
            url = new URL(ip + "interbank/any-id/" + urlParameters);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //InputStream decode urlConnection to Byte format
            InputStream Byte = urlConnection.getInputStream();
            //InputStreamReader decode Byte format to Char format
            InputStreamReader Char = new InputStreamReader(Byte);
            BufferedReader reader = new BufferedReader(Char);
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            JSONResult = builder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
        
        try
        {
        	JSONObject APIDObj = new JSONObject(JSONResult);
        	anyID = new AnyID(APIDObj.optString("AIPID" , null), APIDObj.optString("IDValue", null), APIDObj.optString("IDType", null), APIDObj.optString("BankCode", null), APIDObj.optString("Status", null), APIDObj.optString("AccountID", null), APIDObj.optString("AccountName", null), APIDObj.optString("RegisterDTM", null));
        	
        }
        catch (JSONException e)
        {
        	// TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return anyID;
        return anyID;
	}
	
	public Integer addAnyID(String IDType, String IDValue, String BankCode, String AccountID, String AccountName)
	{
		URL url;
        HttpURLConnection urlConnection = null;
        Integer status = 0;
        try
        {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("IDType", IDType);
            jsonParam.put("IDValue", IDValue);
            jsonParam.put("BankCode", BankCode);
            jsonParam.put("AccountID", AccountID);
            jsonParam.put("AccountName", AccountName);

            url = new URL(ip + "interbank/any-id");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream ());
            printout.writeBytes(jsonParam.toString());
            printout.flush();
            printout.close();
            status = urlConnection.getResponseCode();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
        return status;
	}
	
	public Integer deleteAnyID(String value)
	{
		URL url;
		Integer status = 0;
        HttpURLConnection urlConnection = null;
        try
        {

            url = new URL(ip + "interbank/any-id/" + value);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            status = urlConnection.getResponseCode();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
        return status;
	}
	
	public Integer transferAnyID(String desAPID, String desBankCode, String myAccount, String amount)
	{
		URL url;
        HttpURLConnection urlConnection = null;
        Integer status = 0;
        JSONObject jsonParam = new JSONObject();

        try
        {
            jsonParam.put("AIPID", desAPID);
            jsonParam.put("SendBankCode", desBankCode);
            jsonParam.put("SendAccountID", myAccount);
            jsonParam.put("Amount", amount);
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try
        {
            url = new URL(ip + "interbank/money-tranfer/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream ());
            printout.writeBytes(jsonParam.toString());
            printout.flush();
            printout.close();
            status = urlConnection.getResponseCode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
        return status;
    }
}
