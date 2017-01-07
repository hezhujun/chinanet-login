package login.util;

import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/12/22 0022.
 */
public class PropertyUtilImplTest {
    @Test
    public void saveProperties() throws Exception {
        Properties properties = new Properties();
        properties.put("brasAddress", "59df7586");
        properties.put("password", "5d85699b4b24f9adfdab615aa0457936e7b73d99657dffbcaa264cdff3ed2c062a42893364429ba4e59c96b5727b6d33da96efc4778cc8e06ca36bd1e1df16625abb90bb1908708097fb55f641ebe0dc433d948859e30d83bef7282b3ea8500bde32b01854993bede19f71c7663e3984dc9e65fbff4ef2a9aabf8def87392ae0");
        properties.put("userIntranetAddress", "10.96.20.93");
        properties.put("accountID", "0139011404xx@zndx.inter");
        new PropertyUtilImpl().saveProperties(properties);
    }

    @Test
    public void readProperties() throws Exception {
        Properties properties = new PropertyUtilImpl().readProperties();
        for (Map.Entry entry :
                properties.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

}