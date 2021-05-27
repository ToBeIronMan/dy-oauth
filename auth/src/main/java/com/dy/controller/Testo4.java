package com.dy.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Testo4 {
    private static  String SIGN_KEY="DASDQ";
    public static void main(String[] args) throws InterruptedException {
        Long now=System.currentTimeMillis();
        long exp=now+1000*5;
        JwtBuilder jwtBuilder= Jwts.builder()
                .claim("phone","11")
                .signWith(SignatureAlgorithm.HS256,SIGN_KEY)
                .setExpiration(new Date(exp));
        System.out.println(jwtBuilder.compact());
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        Claims body= Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(jwtBuilder.compact()).getBody();
        System.out.println(body);
    }
}
