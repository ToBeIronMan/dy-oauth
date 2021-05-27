package com.dy.controller;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Test {
    private static  String SIGN_KEY="DASDQ";
    public static void main(String[] args){
 JwtBuilder jwtBuilder= Jwts.builder()
                .claim("phone","11")
                .signWith(SignatureAlgorithm.HS256,SIGN_KEY);
 System.out.println(jwtBuilder.compact());
             }
}