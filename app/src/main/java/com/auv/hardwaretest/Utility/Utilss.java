package com.auv.hardwaretest.Utility;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author LiChuang
 * @version 1.0
 * @ClassName Utilss
 * @Description TODO 类描述
 * @since 2020/2/5 14:36
 **/
public class Utilss {

    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     * @param params json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    static String post(String strURL, String params) {
        BufferedReader reader;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            // connection.setRequestProperty("Accept", "MyApplication/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "multipart/form-data"); // 设置发送数据的格式
            connection.setConnectTimeout(10000); //  连接主机的超时时间（单位：毫秒）
            connection.setReadTimeout(10000); //  从主机读取数据的超时时间（单位：毫秒）
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();

            //如果一定要使用如下方式接收响应数据， 则响应必须为: response.getWriter().print(StringUtils.join("{\"errCode\":\"1\",\"errMsg\":\"", message, "\"}")); 来返回
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                String result = new String(data, "UTF-8"); // utf-8编码
//                System.out.println(result);
//                return result;
//            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "回调失败"; // 自定义错误信息
    }


    public  static void   OkHttp(String url){
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Log.d("result",    request.headers().toString());
                Response response = client.newCall(request).execute();//发送请求
                String result = response.body().string();
                Log.d("result",  result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static String sendPostRequest(String url, String parameters) {
        // Post请求的url，与get不同的是不需要带参数
        String result = "";
        try {

            URL postUrl = new URL(url);

            // 加入代理start

//  Properties systemProperties =System.getProperties();
//  systemProperties.setProperty("http.proxyHost",proxyHost);
//
//  systemProperties.setProperty("http.proxyPort",String.valueOf(proxyPort));

// 加入代理end

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            // 设置是否向connection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true
            connection.setDoOutput(true);
            // Read from the connection. Default is true.
            connection.setDoInput(true);
            // 默认是 GET方式
            connection.setRequestMethod("POST");
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            //设置本次连接是否自动重定向
            connection.setInstanceFollowRedirects(true);
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            // 意思是正文是urlencoded编码过的form参数
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());
            // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
//            String content = "name=11&pas=";
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
            out.writeBytes(parameters.replaceFirst("&", ""));
            //流用完记得关
            out.flush();

            //去掉代理start

            System.getProperties().remove("http.proxyHost");

            System.getProperties().remove("http.proxyPort");

            //去掉代理end

            out.close();
            //获取响应
            //获取请求的资源
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            result = br.readLine();
            br.close();
            //该干的都干完了,记得把连接断了
            connection.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return result;
    }


    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     *               json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    static String get(String strURL) {
        BufferedReader reader;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
            connection.setRequestProperty("Content-Type",
                    "plain/text; charset=UTF-8");
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.connect();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();

            //如果一定要使用如下方式接收响应数据， 则响应必须为: response.getWriter().print(StringUtils.join("{\"errCode\":\"1\",\"errMsg\":\"", message, "\"}")); 来返回
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                String result = new String(data, "UTF-8"); // utf-8编码
//                System.out.println(result);
//                return result;
//            }
            connection.disconnect();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    //获取存餐验证码
    public static String getDateTime() {
        @SuppressLint("SimpleDateFormat")
        //时间
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static String encryption(String param, String secretKey) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        param = param.replace("?", "");
        String[] params = param.split("&");
        params = stringSort(params);
        String toBeEncryption = "";
        for (String value :
                params) {
            if (StringUtils.isEmpty(toBeEncryption)) {
                toBeEncryption += value;
            } else {
                toBeEncryption += "&" + value;
            }
        }
        System.out.println(toBeEncryption);
        return getMd5BySecret(toBeEncryption, secretKey);
    }


    public static String[] stringSort(String[] s) {
        List<String> list = new ArrayList<>(s.length);
        Collections.addAll(list, s);
        Collections.sort(list);
        return list.toArray(s);
    }


    //HmacMD5算法
    public static final String KEY_MD5 = "HmacMD5";

    /**
     * MD5加密-》获取签名
     *
     * @param data      要签名的内容
     * @param sigSecret 签名秘钥
     * @return
     * @throws Exception
     */
    public static String getMd5BySecret(@NonNull String data, @NonNull String sigSecret) {
        SecretKey secretKey = new SecretKeySpec(sigSecret.getBytes(), KEY_MD5);
        Mac mac = null;
        try {
            mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        assert mac != null;
        byte[] b = mac.doFinal(data.getBytes());
        return bytesToHexString(b);
    }

    /**
     * 字节转16进制 字母大写
     *
     * @param b MD5加密签名后的byte数组
     * @return
     */
    public static String bytesToHexString(@NonNull byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        String sig = sb.toString();
        //转为大写
//       return sig.toUpperCase();
        //转为小写
        return sig.toLowerCase();
    }


    public static void main(String[] args) {
        String appId = "66490";
        String method = "device.cell.op";
        String timestamp = "2020-02-06 14:47:00";
        String bizData = "{\"cellNo\":\"1\",\"op\":\"DOOR_OPEN\",\"deviceId\":\"TestAndroid_2\"}";
        String version = "1.0";
        String secretKey = "476dd101cc64b05d0acb43ad94233ae3";
        String param = "?appId=" + appId +
                "&method=" + method +
                "&timestamp=" + timestamp +
                "&bizData=" + bizData +
                "&version=" + version;
        System.err.println(encryption(param, secretKey));

    }

}
