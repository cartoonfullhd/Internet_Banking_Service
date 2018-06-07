package com.example.sqlmap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.object.UserObj;

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
	public UserObj LoginBankUserResult(@Param("username") String username, @Param("password") String password);
}
