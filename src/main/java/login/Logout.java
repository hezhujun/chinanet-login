package login;

import com.google.gson.Gson;
import login.util.HttpUtil;
import login.util.PropertyUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hezhujun on 2016/12/21 0021.
 */
public class Logout {

    private PropertyUtil propertyUtil;

    public final String LOGOUT_URL = "http://61.137.86.87:8080/portalNat444/AccessServices/logout?";
    public final String MAIN_PAGE_URL = "http://61.137.86.87:8080/portalNat444/main2.jsp";

    public Logout(PropertyUtil propertyUtil) {
        this.propertyUtil = propertyUtil;
    }

    /**
     * 通过配置文件获取brasAddress和userIntranetAddress进行下线
     *
     * @return 返回结果
     * @throws Exception 异常
     */
    public LogoutResponseType logout() throws Exception {
        Properties properties = propertyUtil.readProperties();
        String brasAddress = properties.getProperty("brasAddress");
        String userIntranetAddress = properties.getProperty("userIntranetAddress");
        if (userIntranetAddress == null) {
            throw new Exception("无法获取网络地址");
        }
        if (brasAddress == null) {
            throw new Exception("无法获取接入点设备地址");
        }
        return logout(brasAddress, userIntranetAddress);
    }

    /**
     * 下线
     *
     * @param brasAddress         接入点设备地址
     * @param userIntranetAddress 网络地址
     * @return 返回结果
     * @throws Exception 异常
     */
    public LogoutResponseType logout(String brasAddress, String userIntranetAddress) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("brasAddress", brasAddress);
        params.put("userIntranetAddress", userIntranetAddress);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", MAIN_PAGE_URL);

        try {
            String result = HttpUtil.post(LOGOUT_URL, params, headers, "UTF-8");
            Gson gson = new Gson();
            LogoutResponseType response = gson.fromJson(result, LogoutResponseType.class);
            if (response == null) throw new Exception("未知错误");
            return response;
        } catch (Exception e) {
            throw new Exception("网络异常");
        }
    }

}
