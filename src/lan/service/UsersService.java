package lan.service;

import lan.domain.Users;

/**
  * @author lanxiaokang
  * @version 创建时间：2020年4月17日下午8:05:10
  * 类说明：
  */
public interface UsersService {
	public boolean queryOpenIdIsExist(String openid);

	/**
	 * @param openid
	 * @return
	 */
	public Users queryUserByOpenID(String openid);


	/**
	 * @param openId
	 * @return
	 */
	public void saveUsers(Users users);

}
