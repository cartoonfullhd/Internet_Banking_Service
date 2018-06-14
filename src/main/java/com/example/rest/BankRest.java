package com.example.rest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.object.AccountObj;
import com.example.object.AnyID;
import com.example.object.BankObj;
import com.example.object.CustomerObj;
import com.example.object.ReceiveMoney;
import com.example.object.Txn;
import com.example.service.DownloadReport;
import com.example.service.InterBanking;
import com.example.service.LoginAuthentication;
import com.example.service.TransferMoney;
import com.example.sqlmap.AccountSql;
import com.example.sqlmap.LoginSql;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path="/bank/account")
public class BankRest
{
	@Autowired LoginAuthentication loginAuthentication;
	@Autowired AccountSql accountSql;
	@Autowired TransferMoney transferMoney;
	@Autowired InterBanking interBanking;
	@Autowired LoginSql loginSql;
	@Autowired DownloadReport downloadReport;
	
	//check login
	@PostMapping(path="/login")
	public ResponseEntity<String> login(
			@RequestParam("j_username") String username,
			@RequestParam("j_password") String password)
	{
		ResponseEntity<String> rs = null;
		if (loginAuthentication.checkLogin(username, password) == true) 
		{
			HttpHeaders headers = new HttpHeaders();
	        headers.add("customer_id", loginAuthentication.getCustomerID());
	        headers.add("Access-Control-Allow-Origin", "*");
			rs =  new ResponseEntity<String>("Success", headers, HttpStatus.OK);
		} else 
		{
			rs =  new ResponseEntity<String>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
		return rs;
	}
	
	//get account details list by using customer id
	@GetMapping(path="/{customerId}")
	public ResponseEntity<List<AccountObj>> getAccountList(
			@PathVariable("customerId") String customerId)
	{
		ResponseEntity<List<AccountObj>> rs =  null;
		rs = new ResponseEntity<List<AccountObj>>(accountSql.getAccountDetailList(customerId) , HttpStatus.OK);
		return rs;
	}
	
	//get an account detail by using account id
	@GetMapping(path="/account")
	public ResponseEntity<AccountObj> getAccount(
			@RequestParam("accountId") String accountId)
	{
		ResponseEntity<AccountObj> rs =  null;
		rs = new ResponseEntity<AccountObj>(accountSql.getAccountDetail(accountId) , HttpStatus.OK);
		return rs;
	}
	
	//get status code
	@GetMapping(path="/statuscode")
	public ResponseEntity<String> getStatus(
			@RequestParam("code") String statusCode)
	{
		ResponseEntity<String> rs =  null;
		rs = new ResponseEntity<String>(accountSql.getStatusCode(statusCode) , HttpStatus.OK);
		return rs;
	}
	
	//get bank name
	@GetMapping(path="/bankcode")
	public ResponseEntity<BankObj> getBankDetail(
			@RequestParam("code") String bankCode)
	{
		ResponseEntity<BankObj> rs =  null;
		rs = new ResponseEntity<BankObj>(accountSql.getBankName(bankCode) , HttpStatus.OK);
		return rs;
	}
	
	//check destination account invalid or valid before transfer
	@GetMapping(path="/checkaccount")
	public ResponseEntity<String> checkAccount(
			@RequestParam("account_id") String accountId)
	{
		ResponseEntity<String> rs = null;
		
		if (transferMoney.checkDesAccount(accountId) == true) 
		{
			rs =  new ResponseEntity<String>("Success", HttpStatus.OK);
		} else 
		{
			rs =  new ResponseEntity<String>("Invalid account", HttpStatus.UNAUTHORIZED);
		}
		return rs;
	}
	
	//get customer detail
	@GetMapping(path="/customerdetail")
	public ResponseEntity<CustomerObj> getCustomerDetail(
			@RequestParam("customer_id") String customerId)
	{
		ResponseEntity<CustomerObj> rs = null;
		rs =  new ResponseEntity<CustomerObj>(accountSql.getCustomerDetail(Integer.parseInt(customerId)) , HttpStatus.OK);
		return rs;
	}
	
	//transfer
	@PostMapping(path="/transfer")
	public ResponseEntity<String> transfer(
			@RequestParam("my_account") String myAccount,
			@RequestParam("des_account") String desAccount,
			@RequestParam("amount") String amount,
			@RequestHeader HttpHeaders headers)
	{
		ResponseEntity<String> rs =  null;
		Map<String, String> headerMap = headers.toSingleValueMap();
		
		if (headers.containsKey("customer_id"))
		{
			if(transferMoney.transfer(myAccount, desAccount, Double.parseDouble(amount), Integer.parseInt(headerMap.get("customer_id"))) == true)
			{
				rs =  new ResponseEntity<String>("Completed transfer" ,HttpStatus.OK);
				return rs;
			}
			else
			{
				rs =  new ResponseEntity<String>("Incompleted transfer" ,HttpStatus.UNAUTHORIZED);
				return rs;
			}
		}else 
		{
			rs =  new ResponseEntity<String>("Unauthorised" ,HttpStatus.UNAUTHORIZED);
			return rs;
		}
	}
	
	//transfer by using APID
		@PostMapping(path="/transferapid")
		public ResponseEntity<String> transferAPID(
				@RequestParam("my_account") String myAccount,
				@RequestParam("des_account") String desAccount,
				@RequestParam("amount") String amount,
				@RequestParam("apid") String apid,
				@RequestParam("des_code") String desCode,
				@RequestHeader HttpHeaders headers)
		{
			ResponseEntity<String> rs =  null;
			Map<String, String> headerMap = headers.toSingleValueMap();
			
			if (headers.containsKey("customer_id"))
			{
				if(transferMoney.transferAPID(myAccount, desAccount, Double.parseDouble(amount), Integer.parseInt(headerMap.get("customer_id")), apid, desCode) == true)
				{
					rs =  new ResponseEntity<String>("Completed transfer" ,HttpStatus.OK);
					return rs;
				}
				else
				{
					rs =  new ResponseEntity<String>("Incompleted transfer" ,HttpStatus.UNAUTHORIZED);
					return rs;
				}
			}else 
			{
				rs =  new ResponseEntity<String>("Unauthorised" ,HttpStatus.UNAUTHORIZED);
				return rs;
			}
		}
		
		
		//receive money by apid api
		@PostMapping(path="/recievemoney")
			public ResponseEntity<Txn> receiveMoney(
				@RequestBody Map<String, String> json)
			{
				Txn txn = null;
				ResponseEntity<Txn> rs =  null;
				Double amountMyAccount = accountSql.getAmount(json.get("ReceiveAccountID"));
				int resultMyAccount;
				resultMyAccount = accountSql.updateMyAccountReceiveMoney(Double.parseDouble(json.get("Amount")) + amountMyAccount,  json.get("ReceiveAccountID"), new Timestamp(System.currentTimeMillis()));
					
				if(resultMyAccount == 1)
				{
					if(!json.get("SendBankCode").equals(json.get("ReceiveBankCode")))
					{
						accountSql.addTransferTxnAPIDTxn(Double.parseDouble(json.get("Amount")), "Success", "Interbank" , json.get("SendAccountID"), json.get("ReceiveAccountID"), new Timestamp(System.currentTimeMillis()), "0", Double.parseDouble(json.get("Amount")), json.get("SendBankCode"), json.get("ReceiveBankCode"));
					}
					rs =  new ResponseEntity<Txn>(accountSql.getTxnReceive() ,HttpStatus.OK);
					return rs;
				}else
				{
					rs =  new ResponseEntity<Txn>(txn ,HttpStatus.UNAUTHORIZED);
					return rs;
				}
			}
		
		//get txn list
		@GetMapping(path="/txn")
		public ResponseEntity<List<Txn>> getTxn(
				@RequestParam("account_id") String accountID)
		{
			ResponseEntity<List<Txn>> rs = null;
			rs =  new ResponseEntity<List<Txn>>(accountSql.getTxn(accountID) , HttpStatus.OK);
			return rs;
		}
		
		//Check any id that duplicate or not
		@GetMapping(path="/checkanyid")
		public ResponseEntity<AnyID> checkAnyId(
				@RequestParam("type") String type,
				@RequestParam("value") String value)
		{
			ResponseEntity<AnyID> rs = null;
			Integer status = interBanking.checkAnyID(type, value);
			if(status == 200)
			{
				rs =  new ResponseEntity<AnyID>(interBanking.getAnyID(type, value), HttpStatus.OK);
			}
			else {
				rs =  new ResponseEntity<AnyID>(interBanking.getAnyID(type, value), HttpStatus.UNAUTHORIZED);
			}
			
			return rs;
		}
		
		//register any id
		@PostMapping(path="/addanyid")
		public ResponseEntity<Integer> addAnyId(
				@RequestBody Map<String, String> json)
		{
			ResponseEntity<Integer> rs = null;
			
			Integer status = interBanking.addAnyID(json.get("IDType"), json.get("IDValue"), json.get("BankCode"), json.get("AccountID"), json.get("AccountName"));
			if(status == 200)
			{
				rs =  new ResponseEntity<Integer>(status, HttpStatus.OK);
			}
			else {
				rs =  new ResponseEntity<Integer>(status, HttpStatus.UNAUTHORIZED);
			}
			
			return rs;
		}
		
		//Delete any id from the system
		@DeleteMapping(path="/deleteanyid")
		public ResponseEntity<Integer> deleteAnyID(
				@RequestParam("aipvalue") String aipvalue)
		{
			ResponseEntity<Integer> rs = null;
			
			Integer status = interBanking.deleteAnyID(aipvalue);
			if(status == 200)
			{
				rs =  new ResponseEntity<Integer>(status, HttpStatus.OK);
			}
			else {
				rs =  new ResponseEntity<Integer>(status, HttpStatus.UNAUTHORIZED);
			}
			return rs;
		}
		
		//Transfer between bank
		@PostMapping(path="/transferanyid")
		public ResponseEntity<Integer> transferAnyId(
				@RequestBody Map<String, String> json)
		{
			ResponseEntity<Integer> rs = null;
			
			Integer status = interBanking.transferAnyID(json.get("AIPID"), json.get("SendBankCode"), json.get("SendAccountID"), json.get("Amount"));
			if(status == 200)
			{
				rs =  new ResponseEntity<Integer>(status, HttpStatus.OK);
			}
			else {
				rs =  new ResponseEntity<Integer>(status, HttpStatus.UNAUTHORIZED);
			}
			return rs;
		}
		
		//Register customer account
		@PostMapping(path="/addaccount")
		public ResponseEntity<Integer> registerAccount(
				@RequestBody Map<String, String> json)
		{
			ResponseEntity<Integer> rs = null;
			CustomerObj customer = new CustomerObj(json.get("Login"), json.get("Password"), json.get("Name"), json.get("Address"), json.get("PhoneNum"), json.get("Email"));
			Integer status = loginSql.addCustomer(customer, new Timestamp(System.currentTimeMillis()));
			
			if(status == 1)
			{
				rs =  new ResponseEntity<Integer>(status, HttpStatus.OK);
			}
			else {
				rs =  new ResponseEntity<Integer>(status, HttpStatus.UNAUTHORIZED);
			}
			return rs;
		}
		
		@GetMapping(path="/checkusername")
		public ResponseEntity<String> checkCustomerUsername(
				@RequestParam("username") String username)
		{
			ResponseEntity<String> rs = null;
			if (loginAuthentication.checkUsername(username) == true) 
			{
				rs =  new ResponseEntity<String>("Duplicated username", HttpStatus.OK);
			} else 
			{
				rs =  new ResponseEntity<String>("Valid register", HttpStatus.UNAUTHORIZED);
			}
			return rs;
		}
		
		//get txn report
				@GetMapping(path="/txnreport")
				public ResponseEntity<byte[]> getTxnReport(
						@RequestParam("account_id") String accountID)
				{
					ResponseEntity<byte[]> rs = null;
					
					rs =  new ResponseEntity<byte[]>(downloadReport.generateReport(accountID) , HttpStatus.OK);
					return rs;
				}
}
