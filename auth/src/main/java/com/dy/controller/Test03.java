package com.dy.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.UnsupportedEncodingException;

public class Test03 {
    private static  String SIGN_KEY="DASDQ";
    public static  void main(String[] args) throws UnsupportedEncodingException {
        //eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6IjEyMzEyMzIxIn0=.4e79f73d16f326f376f0d53ec82d4d61
        String jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6IjExIn0.Bf_-eSMNFIPufr8UVHXXxUyiTP0fI7QS1pyd2F67_xI";
        Claims body= Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(jwtToken).getBody();
        System.out.println(body.get("phone"));
    }


    }
