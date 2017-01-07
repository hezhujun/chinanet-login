# chinanet-login
数字中南登录器（核心函数），可以使用命令行运行chinanet-login-1.0.jar来进行数字中南的登录和下线，也可以用来作为数字中南登录功能的jar包。

## 数字中南的登录的步骤
* 查看登录网页的源代码
* 有一篇文章详细介绍了登录步骤的内容http://www.lxway.com/58064916.htm

## 使用方法

### 作为命令行程序
chinanet-login-1.0.jar和lib文件夹必须在同一目录，使用命令`java -jar chinanet-login-1.0.jar login [用户名] [密码]`来登录，使用`java -jar chinanet-login-1.0.jar logout`来下线。详细请看源代码。

## 作为依赖包导入项目中
把chinanet-login-1.0.jar和lib目录下的jar包加入项目的编译路径中。

## 说明
登录数字中南需要4个参数
1. 01 + 学号 + @zndx.inter
2. 加密后的密码
3. brasAddress 接入点设备地址 总是59df7586
4. userIntranetAddress 网络地址 连接网络的设备的ip地址

userIntranetAddress可以通过查看本地IP地址，如果IP地址是10.96.\*.*，那么就是这个IP地址了。但是如果使用自己的路由器登录数字中南，就不能获取到接入数字中南的IP地址。

这时可以通过登录网页的源代码获取到brasAddress和userIntranetAddress。

一开始我使用HttpClient来进行http通讯。HttpURLConnection获取到的登录网页中brasAddress和userIntranetAddress的值是null，而HttpClient可以正常获取到。

我本来想把生成的jar包使用在android上，做一个数字中南登录的app，但是高版本的sdk中的httpclient已经过时，不推荐使用。强行使用会报错，找不到httpclient的一些类。我只好更改代码，把HttpClient换成HttpURLConnection。

而HttpURLConnection获取到的登录网页中brasAddress和userIntranetAddress的值是null，如何解决这个问题呢？通过抓包，发现网页跳转到登录界面是通过302网页重定向实现的。在302的消息头中Location包含brasAddress和userIntranetAddress的值。我只要获取到302的消息头就可以获取brasAddress和userIntranetAddress。

最后说明一点，加密函数是github上mcxr4299/CSU-Logger中实现的。把加密算法的js代码翻译成java代码实在是太难了，我就偷懒找别人写好的。

如果离线经常访问百度等网页从而跳转到登录界面会出现异常，程序不能识别百度的地址，要退出wifi连接等待一会儿重新连接数字中南。