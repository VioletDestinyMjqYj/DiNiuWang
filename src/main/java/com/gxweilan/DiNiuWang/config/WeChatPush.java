package com.gxweilan.DiNiuWang.config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WeChatPush {
    private final String PUSH_API = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    private String accessToken;

    public WeChatPush(String accessToken) {
        this.accessToken = accessToken;
    }

    public void push(String touser, String template_id, String url, JSONObject data) {
        try {
            URL pushUrl = new URL(PUSH_API + accessToken);
            HttpURLConnection connection = (HttpURLConnection) pushUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject json = new JSONObject();
            json.put("touser", touser);
            json.put("template_id", template_id);
            json.put("url", url);
            json.put("data", data);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(json.toString().getBytes("UTF-8"));
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}