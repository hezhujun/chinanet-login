package login.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by hezhujun on 2016/12/25 0025.
 */
public interface PropertyUtil {
    /**
     * 保存配置信息
     *
     * @param prop 配置信息
     * @throws IOException IOException
     */
    public void saveProperties(Properties prop);

    /**
     * 读取配置信息
     *
     * @return Properties对象
     * @throws IOException
     */
    public Properties readProperties();
}
