package com.example.sqlmap;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.object.CustomerObj;

@Mapper
public interface LoginSql
{
	public static final String SQL_Get_User = ""
			+	" SELECT "
			+	" LOGIN, PASSWORD, CUSTOMERID "
			+	" FROM CUSTOMER "
			+	" WHERE LOGIN = #{username} "
			+ 	" AND PASSWORD = #{password}";
	@Select(SQL_Get_User)
	public CustomerObj LoginBankUserResult(@Param("username") String username, @Param("password") String password);
	
	public static final String SQL_Get_Username = ""
			+	" SELECT "
			+	" LOGIN, PASSWORD, NAME, ADDRESS, PHONENUM, EMAIL, CREATEDTM "
			+	" FROM CUSTOMER "
			+	" WHERE LOGIN = #{username} ";
	@Select(SQL_Get_Username)
	public CustomerObj CheckUsername(@Param("username") String username);
	
	public static final String SQL_Add_Customer = ""
			+	"	INSERT INTO CUSTOMER  (LOGIN , PASSWORD, NAME, ADDRESS, PHONENUM, EMAIL, CREATEDTM)"
			+	"	VALUES (#{customer.Login}, #{customer.Password}, #{customer.Name} ,#{customer.Address}, #{customer.PhoneNum}, #{customer.Email}, #{timestamp})";
	@Insert(SQL_Add_Customer)
	public Integer addCustomer(@Param("customer") CustomerObj customer, @Param("timestamp") Timestamp timeStamp);
	
}
