package com.dy.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.jackson.JsonObjectDeserializer;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Test02 {
    private static  String SIGN_KEY="DASDQ";
    public static  void main(String[] args) throws UnsupportedEncodingException {

        JSONObject header=new JSONObject();
        header.put("alg","HS256");
        JSONObject payload= new JSONObject();
        payload.put("phone","11");

        String headerEncoder= Base64.getEncoder().encodeToString(header.toJSONString().getBytes());
        String payloadJsonStr=payload.toJSONString();
        String payloadEncoder=Base64.getEncoder().encodeToString(payloadJsonStr.getBytes());
        String sign= DigestUtils.md5Hex(payloadJsonStr+SIGN_KEY);
        String jwt=headerEncoder+"."+payloadEncoder+"."+sign;
        System.out.println(jwt);
        //解密
        String payloadEncode=jwt.split("\\.")[1];
        String payloadDecoder =new String(Base64.getDecoder().decode(payloadEncode),"utf-8");
        String newSign=DigestUtils.md5Hex(payloadDecoder+SIGN_KEY);
        if(newSign.equals(jwt.split("\\.")[2])){
            System.out.println("true");
        }
    }
}
