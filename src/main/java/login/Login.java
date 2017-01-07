package login;

import com.google.gson.Gson;
import login.security.RSAEncrypt;
import login.util.HttpUtil;
import login.util.PropertyUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hezhujun on 2016/12/20 0020.
 */
public class Login {

    private PropertyUtil propertyUtil;

    public final static String BAIDU_URL = "http://www.baidu.com";
    public final static String LOGIN_URL = "http://61.137.86.87:8080/portalNat444/AccessServices/login";
    public final static String LOGIN_PAGE_URL = "http://61.137.86.87:8080/portalNat444/index.jsp";
//    public final static String BRAS_ADDRESS_PATTERN = "<input type=\"hidden\" value=\"(.*?)\" id=\"brasAddress\" />";
//    public final static String USER_INTRANET_ADDRESS_PATTERN = "<input type=\"hidden\" value=\"(.*?)\"\tid=\"userIntranetAddress\" />";
    public final static String PATTERN = "bas\\.(.*?)\\?wlanuserip=(.*?)&";

    public Login(PropertyUtil propertyUtil) {
        this.propertyUtil = propertyUtil;
    }

    /**
     * 通过配置文件获取用户名和密码进行登录
     *
     * @return 返回结果
     * @throws Exception 异常
     */
    public LoginResponseType login() throws Exception {
        Properties properties = propertyUtil.readProperties();
        String accountID = properties.getProperty("accountID");
        String password = properties.getProperty("password");
        if (accountID == null || password == null) {
            throw new Exception("获取不到账户或密码");
        }

        Map<String, String> map = getBrasAddressAndUserIntranetAddress();
        String brasAddress = map.get("brasAddress");
        String userIntranetAddress = map.get("userIntranetAddress");
        if (userIntranetAddress == null) {
            throw new Exception("无法获取网络地址");
        }
        if (brasAddress == null) {
            throw new Exception("无法获取接入点设备地址");
        }

        LoginResponseType response = login(accountID, password, brasAddress, userIntranetAddress);
        if (response == null) throw new Exception("未知错误");
        else {
            if ("0".equals(response.getResultCode())) {
                saveLoginInfo(accountID, password, brasAddress, userIntranetAddress);
            }
        }

        return response;
    }

    /**
     * 登录
     *
     * @param account  输入的账户名
     * @param password 输入的密码
     * @return 返回结果
     * @throws Exception 异常
     */
    public LoginResponseType login(String account, String password) throws Exception {
        account = account + "@zndx.inter";
        password = RSAEncrypt.newInstance().encryptedString(password);

        Map<String, String> map = getBrasAddressAndUserIntranetAddress();
        String brasAddress = map.get("brasAddress");
        String userIntranetAddress = map.get("userIntranetAddress");
        if (userIntranetAddress == null) {
            throw new Exception("无法获取网络地址");
        }
        if (brasAddress == null) {
            throw new Exception("无法获取接入点设备地址");
        }

        LoginResponseType response = login(account, password, brasAddress, userIntranetAddress);
        if (response == null) throw new Exception("未知错误");

        return response;
    }

    /**
     * 登录并存储登录信息
     *
     * @param account  输入的账户名
     * @param password 输入的密码
     * @return 返回结果
     * @throws Exception 异常
     */
    public LoginResponseType loginAndCache(String account, String password) throws Exception {
        account = account + "@zndx.inter";
        password = RSAEncrypt.newInstance().encryptedString(password);

        Map<String, String> map = getBrasAddressAndUserIntranetAddress();
        String brasAddress = map.get("brasAddress");
        String userIntranetAddress = map.get("userIntranetAddress");
        if (userIntranetAddress == null) {
            throw new Exception("无法获取网络地址");
        }
        if (brasAddress == null) {
            throw new Exception("无法获取接入点设备地址");
        }

        LoginResponseType response = login(account, password, brasAddress, userIntranetAddress);
        if (response == null) throw new Exception("未知错误");
        else {
            if ("0".equals(response.getResultCode())) {
                saveLoginInfo(account, password, brasAddress, userIntranetAddress);
            }
        }
        return response;
    }

    /**
     * 登录
     *
     * @param accountID           格式化的账户名
     * @param password            加密后的密码
     * @param brasAddress         接入点设备地址
     * @param userIntranetAddress 网络地址
     * @return 返回结果
     * @throws Exception 异常
     */
    public LoginResponseType login(String accountID, String password, String brasAddress, String userIntranetAddress) throws Exception {
        LoginResponseType response = null;
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("accountID", accountID);
            params.put("password", password);
            params.put("brasAddress", brasAddress);
            params.put("userIntranetAddress", userIntranetAddress);

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Referer", LOGIN_PAGE_URL);

            String result = HttpUtil.post(LOGIN_URL, params, headers, "UTF-8");
            Gson gson = new Gson();
            response = gson.fromJson(result, LoginResponseType.class);

        } catch (Exception e) {
            throw new Exception("网络异常");
        }
        return response;
    }

    /**
     * 获取brasAddress和userIntranetAddress
     *
     * @return map
     * @throws Exception
     */
    public Map<String, String> getBrasAddressAndUserIntranetAddress() throws Exception {
//        String loginPage = null;
//        try {
//            loginPage = HttpClientUtil.get(BAIDU_URL);
//        } catch (Exception e) {
//            throw new Exception("网络异常");
//        }
//
//        String brasAddress = null;
//        String userIntranetAddress = null;
//        Pattern brasAddressPattern = Pattern.compile(BRAS_ADDRESS_PATTERN);
//        Pattern userIntranetAddressPattern = Pattern.compile(USER_INTRANET_ADDRESS_PATTERN);
//        Matcher matcher = brasAddressPattern.matcher(loginPage);
//        if (matcher.find()) {
//            brasAddress = matcher.group(1);
//            if ("null".equals(brasAddress)) brasAddress = null;
//        }
//        matcher = userIntranetAddressPattern.matcher(loginPage);
//        if (matcher.find()) {
//            userIntranetAddress = matcher.group(1);
//            if (userIntranetAddress == null) userIntranetAddress = null;
//        }

        URL baiduUrl = new URL(BAIDU_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) baiduUrl.openConnection();
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.setRequestMethod("GET");
        //  不允许网页重定向，获取302错误信息，从而获取brasAddress和userIntranetAddress
        httpURLConnection.setInstanceFollowRedirects(false);

        httpURLConnection.connect();

        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_MOVED_TEMP) {
            throw new Exception("不能获取brasAddress和userIntranetAddress");
        }

        String brasAddress = null;
        String userIntranetAddress = null;

        String location = httpURLConnection.getHeaderField("Location");
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(location);
        if (matcher.find()) {
            brasAddress = matcher.group(1);
            userIntranetAddress = matcher.group(2);
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("brasAddress", brasAddress);
        map.put("userIntranetAddress", userIntranetAddress);

        return map;
    }

    public void saveLoginInfo(String accountID, String password, String brasAddress, String userIntranetAddress) {
        Properties prop = new Properties();
        prop.put("accountID", accountID);
        prop.put("password", password);
        prop.put("brasAddress", brasAddress);
        prop.put("userIntranetAddress", userIntranetAddress);
        propertyUtil.saveProperties(prop);
    }

}
