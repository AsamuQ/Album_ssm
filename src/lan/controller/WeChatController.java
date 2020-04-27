package lan.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lan.domain.Users;
import lan.service.UsersService;
import net.sf.json.JSONObject;

/**
  * @author lanxiaokang
  * @version 创建时间：2020年4月13日下午9:45:39
  * 类说明：
  */
@Controller
public class WeChatController {
	private int i = 0;	//上传图片下标
	private int login_times = 0;//判断是否第一次登录，用于后台该用户图片数据的清空
	
	@Autowired
	UsersService usersService;
	
	/**
	 * 用户登录/注册接口，接受微信小程序code，请求微信官方接口获取用户唯一识别码openid
	 * @param code
	 * @return 用户登录状态
	 */
	@ResponseBody
	@RequestMapping(value="/regist",method = RequestMethod.POST)
    public Map<String,Object> gettingOpenID(@RequestParam("code") String code){
        Map<String,Object> map=new HashMap<String,Object>();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }
        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wx3897a4f6b25c9b92";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "f8813a21ede4d41e98354bf118728c01";
        //jscode
        //String js_code = code;
        //授权（必填）
        String grant_type = "authorization_code";
 
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        System.out.println("controller是否请求到openid："+openid);
 
        //查询数据库中该openid是否存在
        if(usersService.queryOpenIdIsExist(openid)){
            //openid存在，则查找该user对象进行返回
            Users users=usersService.queryUserByOpenID(openid);
            //status为1，用户openid已存在
            System.out.println("*****老用户登陆！*****");
            login_times++;
            map.put("status", 1);
            map.put("userInfo",users);
        }else{
            //openid不存在，则创建新user对象
            Users users=new Users();
            users.setOpenId(openid);
            usersService.saveUsers(users);
            //status为2，用户openid未存在
            System.out.println("*****新用户登陆！*****");
            login_times++;
            map.put("status", 2);
            map.put("userInfo",users);
        }
        
        //遍历map
        for (Entry<String, Object> entry : map.entrySet()) {
        	System.out.println("key = "+entry.getKey()+", value = "+entry.getValue());
        }
        return map;
    }
	
	/**
	 * 上传文件接口，将上传的文件存入图片识别接口所在文件夹
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = { RequestMethod.POST,RequestMethod.GET})
    public Map<Integer,String> uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<Integer,String> map = new HashMap<Integer,String>();
 
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile =  req.getFile("file");
        
        Date date = new Date();
		System.out.println(date.getTime()+"第"+(i+1)+"次接受前端文件！");
		
		String realPath = "D:\\software\\PyCharm\\wx_album\\wxImage";
        try {
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
          //第一次登录清空用户上传目录下所有文件
            if (login_times!=1) {
            	File files[] = dir.listFiles();
            	if (files!=null) {
            		for (File f : files) {
            			f.delete();
            		}
            	}
            }
            File file  =  new File(realPath,(i++)+".jpg");
            multipartFile.transferTo(file);
            System.out.println(date.getTime()+"第"+i+"次接受前端文件成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return map;
    }
	

}
