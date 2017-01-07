package login;

public class LoginResponseType {
	private String userSchoolOctets;
	private String password;
	private String surplusmoney;
	private String vendor;
	private String usedflow;
	private long time;
	private String secretKey;
	private String resultCode;
	private String account;
	private String totalflow;
	private String resultDescribe;
	private String accountID;
	private String lastupdate;
	private String surplusflow;
	private String userIntranetAddress;
	private String brasAddress;
	private String username;
	private String userAgentType;

	public String getUserSchoolOctets() {
		return userSchoolOctets;
	}

	public void setUserSchoolOctets(String userSchoolOctets) {
		this.userSchoolOctets = userSchoolOctets;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurplusmoney() {
		return surplusmoney;
	}

	public void setSurplusmoney(String surplusmoney) {
		this.surplusmoney = surplusmoney;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getUsedflow() {
		return usedflow;
	}

	public void setUsedflow(String usedflow) {
		this.usedflow = usedflow;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTotalflow() {
		return totalflow;
	}

	public void setTotalflow(String totalflow) {
		this.totalflow = totalflow;
	}

	public String getResultDescribe() {
		return resultDescribe;
	}

	public void setResultDescribe(String resultDescribe) {
		this.resultDescribe = resultDescribe;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getSurplusflow() {
		return surplusflow;
	}

	public void setSurplusflow(String surplusflow) {
		this.surplusflow = surplusflow;
	}

	public String getUserIntranetAddress() {
		return userIntranetAddress;
	}

	public void setUserIntranetAddress(String userIntranetAddress) {
		this.userIntranetAddress = userIntranetAddress;
	}

	public String getBrasAddress() {
		return brasAddress;
	}

	public void setBrasAddress(String brasAddress) {
		this.brasAddress = brasAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserAgentType() {
		return userAgentType;
	}

	public void setUserAgentType(String userAgentType) {
		this.userAgentType = userAgentType;
	}

	@Override
	public String toString() {
		return "LoginResponseType [userSchoolOctets=" + userSchoolOctets + ", password=" + password + ", surplusmoney="
				+ surplusmoney + ", vendor=" + vendor + ", usedflow=" + usedflow + ", time=" + time + ", secretKey="
				+ secretKey + ", resultCode=" + resultCode + ", account=" + account + ", totalflow=" + totalflow
				+ ", resultDescribe=" + resultDescribe + ", accountID=" + accountID + ", lastupdate=" + lastupdate
				+ ", surplusflow=" + surplusflow + ", userIntranetAddress=" + userIntranetAddress + ", brasAddress="
				+ brasAddress + ", username=" + username + ", userAgentType=" + userAgentType + "]";
	}

	public static String parse(LoginResponseType response) {
		String result = null;
		int code = Integer.parseInt(response.getResultCode());
		switch (code) {
			case 0 : result = "登录成功"; break;
			case 1 :
				if (response.getResultDescribe() == null || "".equals(response.getResultDescribe())) {
					result = "其他原因认证拒绝";
				} else {
					result = response.getResultDescribe();
				}
				break;
			case 2 : result = "用户连接已经存在"; break;
			case 3 : result = "接入服器务繁忙，稍后重试"; break;
			case 4 : result = "未知错误"; break;
//			case 5 : result = ""; break;
			case 6 : result = "认证响应超时"; break;
			case 7 : result = "捕获用户网络地址错误"; break;
			case 8 : result = "服务器网络连接异常"; break;
			case 9 : result = "认证服务脚本执行异常"; break;
//			case 10 : result = "校验码错误"; break;
			case 11 : result = "您的密码相对简单，帐号存在被盗风险，请及时修改成强度高的密码"; break;
			case 12 : result = "无法获取网络地址"; break;
			case 13 : result = "无法获取接入点设备地址"; break;
			case 14 : result = "无法获取套餐信息"; break;
//			case 15 : result = ""; break;
			case 16 : result = "请输入任意其它网站导航至本认证页面,并按正常PORTAL正常流程认证"; break;
			case 17 : result = "连接已失效，请输入任意其它网站从网关处导航至本认证页面"; break;
			default: result = "未知错误"; break;
		}
		return result;
	}
}