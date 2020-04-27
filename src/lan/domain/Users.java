package lan.domain;
/**
  * @author lanxiaokang
  * @version 创建时间：2020年3月12日上午11:21:53
  * 类说明：
  */
public class Users {
	private int userId;
	private String openId;

	
	public Users() {
		super();
	}
	public Users(int userId, String openId) {
		super();
		this.userId = userId;
		this.openId = openId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openid) {
		this.openId = openid;
		
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Users [userId=" + userId + ", openId=" + openId + "]";
	}
	
	
	
	
	
}
