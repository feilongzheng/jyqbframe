package com.dfppm.giveu.model.bean;

import com.android.volley.mynet.BaseBean;

import java.io.Serializable;

public class LoginBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1383471265655162904L;

	public String emId;
	public String emPwd;
	public String nickName;
	public String userid;
	public String messagetype;

	public int USERGROUP;
	
    /**
     * -1=未知，0=女 ,1=男
     */
	public String sex;
	/**
	 * 2=星星， 捧丝 !=2
	 */
	public String xstatus;
	public int level;
	public ThirdPlatInfo authlist;

    public int giftSkill;
    public int praisesSkill;
    public int voteSkill;
    public String userSessionToken;//	String	用户登陆凭证

    /**
     * 头像地址的时间戳 自己赋值的
     */
    public String avatarfilename;
    public int userGroup = Integer.MIN_VALUE;


    public static class ThirdPlatInfo implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public UserPlatformInfoBean qqlist;
		public UserPlatformInfoBean wxlist;
		public UserPlatformInfoBean sinalist;
	}

	public static class UserPlatformInfoBean implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String platformName;
		public String ANICKNAME;
		public String AUTHTYPE;
		public String AUUID;
		public String EXPIREIN;
		public String TOKEN;
		public String USERID;
	}




	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}



	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getXstatus() {
		return xstatus;
	}

	public void setXstatus(String xstatus) {
		this.xstatus = xstatus;
	}


}