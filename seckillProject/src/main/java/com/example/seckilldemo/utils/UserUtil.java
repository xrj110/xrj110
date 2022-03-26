package com.example.seckilldemo.utils;

import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.vo.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* 批量生成用户，并且得到cookie
* */
public class UserUtil {
    private static void createUser(int count) throws Exception {
        List<TUser> users = new ArrayList<>(count);
        //生成用户

        for (int i = 0; i < count; i++) {
            TUser user = new TUser();
            user.setId(13000000000L + i);

            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSlat("j9d191281");
            user.setPassword(MD5util.inputPassToDBPass("123456", user.getSlat()));
            users.add(user);
        }
//        System.out.println("create user");
//        //插入数据库
//        Connection conn = getConn();
//        String sql = "insert into t_user( nickname, register_date, slat, password, id)values(?,?,?,?,?)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        for (int i = 0; i < users.size(); i++) {
//            TUser user = users.get(i);
//            pstmt.setString(1, user.getNickname());
//            pstmt.setTimestamp(2, new Timestamp(user.getRegisterDate().getTime()));
//            pstmt.setString(3, user.getSlat());
//            pstmt.setString(4, user.getPassword());
//            pstmt.setLong(5, user.getId());
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.close();
//        conn.close();
//        System.out.println("insert to db");
        String urlString = "http://localhost:8080/login/doLogintest";
        File file = new File("C:\\Users\\tony\\Desktop\\config.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            TUser user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" +
                    "123456";
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();


            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = ((String) respBean.getObj());
            System.out.println("create userTicket : " + user.getId());
            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();
        System.out.println("over");
    }
    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/seckill?serverTimezone=UTC";
        String username = "root";
        String password = "000314";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        UserUtil.createUser(2000);
    }
}
