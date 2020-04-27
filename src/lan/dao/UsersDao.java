package lan.dao;

import org.apache.ibatis.annotations.Param;

import lan.domain.Users;

/**
  * @author lanxiaokang
  * @version 创建时间：2020年4月17日下午8:43:58
  * 类说明：
  */
public interface UsersDao {
	
	public boolean queryOpenIdIsExist(@Param("openid") String openid);
	
	public Users queryUserByOpenID(@Param("openid") String openid);
	
	public void saveUsers(@Param("users") Users users);

}
