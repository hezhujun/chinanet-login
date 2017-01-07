package login;

import login.util.PropertyUtilImpl;

/**
 * 数字中南登录器主程序
 */
public class App {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("请输入login或logout");
            System.exit(-1);
        }
        String command = args[0];
        if ("login".equals(command)) {
            Login login = new Login(new PropertyUtilImpl());
            LoginResponseType response = null;
            if (args.length == 3) {
                String account = args[1];
                String password = args[2];

                if (account == null || password == null) {
                    System.out.println("账号或密码不能空");
                    System.exit(-1);
                }

                if (account.length() > 32 || password.length() > 32) {
                    System.out.println("账号或密码超长");
                    System.exit(-1);
                }

                try {
                    response = login.loginAndCache(account, password);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
            } else {
                // 不输入账户和密码就使用之前的账户登录
                try {
                    response = login.login();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
            }

            System.out.println(LoginResponseType.parse(response));

        } else if ("logout".equals(command)) {
            Logout logout = new Logout(new PropertyUtilImpl());
            try {
                LogoutResponseType response = logout.logout();
                System.out.println(LogoutResponseType.parse(response));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}
