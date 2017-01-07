package login;

public class LogoutResponseType {
    private String vendor;
    private long time;
    private String resultCode;
    private String secretKey;
    private String userIntranetAddress;
    private String brasAddress;
    private String resultDescribe;
    private String userAddress;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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

    public String getResultDescribe() {
        return resultDescribe;
    }

    public void setResultDescribe(String resultDescribe) {
        this.resultDescribe = resultDescribe;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "LogoutResponseType [vendor=" + vendor + ", time=" + time + ", resultCode=" + resultCode + ", secretKey="
                + secretKey + ", userIntranetAddress=" + userIntranetAddress + ", brasAddress=" + brasAddress
                + ", resultDescribe=" + resultDescribe + ", userAddress=" + userAddress + "]";
    }

    public static String parse(LogoutResponseType response) {
        String result = null;
        switch (response.getResultCode().charAt(0)) {
            case '0': result = "下线成功"; break;
            case '1': result = "服务器拒绝请求"; break;
            case '2': result = "下线请求执行失败"; break;
            case '3': result = "已经下线"; break;
            case '4': result = "服务器响应超时"; break;
            case '5': result = "后台网络连接异常"; break;
            case '6': result = "服务脚本执行异常"; break;
            case '7': result = "无法获取您的网络地址"; break;
            case '8': result = "无法获取您接入点设备地址"; break;
            case '9': result = "请输入任意其它网站导航至本认证页面,并按正常PORTAL正常流程认证"; break;
            default: result = "未知错误"; break;
        }
        return result;
    }
}