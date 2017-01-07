package login.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author hezhujun
 */
public class HttpUtil {

    private final static String CHARSET = "UTF-8";

    public static String get(String url) throws Exception {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        return get(url, params, headers, CHARSET);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers, String charset)
            throws Exception {
        if (params != null && !params.isEmpty()) {
            StringBuilder urlsb = new StringBuilder(url);
            urlsb.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlsb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            urlsb.deleteCharAt(urlsb.length() - 1);
            url = urlsb.toString();
        }

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.setRequestMethod("GET");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header :
                    headers.entrySet()) {
                httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        httpURLConnection.connect();

        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
            throw new Exception("网络异常");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), charset));
        StringBuilder result = new StringBuilder();
        char[] buffer = new char[1024];
        int len = 0;
        while ((len = br.read(buffer, 0, 1024)) != -1) {
            result.append(buffer, 0, len);
        }
        br.close();
        return result.toString();
    }

    public static String post(String url, Map<String, String> params) throws Exception {
        return post(url, params, null, CHARSET);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers, String charset) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header :
                    headers.entrySet()) {
                httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        httpURLConnection.connect();

        if (params != null && !params.isEmpty()) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), charset));
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry :
                    params.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            sb.deleteCharAt(sb.length() - 1);
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        }

        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception("网络异常");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), charset));
        StringBuilder result = new StringBuilder();
        char[] buffer = new char[1024];
        int len = 0;
        while ((len = br.read(buffer, 0, 1024)) != -1) {
            result.append(buffer, 0, len);
        }
        br.close();
        return result.toString();
    }

}
