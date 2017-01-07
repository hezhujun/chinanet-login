package login.util;

import java.io.*;
import java.util.Properties;

public class PropertyUtilImpl implements PropertyUtil {

    public final static String CONFIG_FILE_PATH = "csu-chinanet-login-config.properties";


    public void saveProperties(Properties prop) {
        try {
            File file = new File(CONFIG_FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                //设置文件属性为隐藏
                String string = " attrib +H  " + file.getAbsolutePath();
                Runtime.getRuntime().exec(string);
            }
            // 隐藏的文件不能使用FileOutputStream
//            prop.store(new FileOutputStream(file), "数字中南登录信息");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            prop.store(bos, "数字中南登录信息");

            // 修改隐藏文件的内容使用RandomAccessFile
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            f.write(bos.toByteArray());
            f.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("保存登录信息失败");
        }
    }

    public Properties readProperties() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(CONFIG_FILE_PATH));
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("读取登录信息失败");
        }
        return prop;
    }
}
