package lan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lan.dao.UsersDao;
import lan.domain.Users;
import lan.service.UsersService;

/**
  * @author lanxiaokang
  * @version 创建时间：2020年4月17日下午8:43:10
  * 类说明：
  */
@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	UsersDao usersDao;

	/* （非 Javadoc）
	 * @see lan.service.UsersService#queryOpenIdIsExist(java.lang.String)
	 */
	@Override
	public boolean queryOpenIdIsExist(String openid) {
		boolean isExist = usersDao.queryOpenIdIsExist(openid);
		System.out.println("*******是否存在，查询openid: "+isExist+"  openid="+openid);
		return isExist;
	}

	/* （非 Javadoc）
	 * @see lan.service.UsersService#queryUserByOpenID(java.lang.String)
	 */
	@Override
	public Users queryUserByOpenID(String openid) {
		System.out.println("*******通过openid查询user，openid: "+openid);
		return usersDao.queryUserByOpenID(openid);
	}

	/* （非 Javadoc）
	 * @see lan.service.UsersService#saveUsers(lan.domain.Users)
	 */
	@Override
	public void saveUsers(Users users) {
		System.out.println("*******插入users对象: "+users);
		usersDao.saveUsers(users);
	}

}
