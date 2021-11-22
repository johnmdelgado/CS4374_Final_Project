package com.cs4374.finalproject.security;

import com.cs4374.finalproject.exception.SpringJavaStoreException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
   // private Key key;
    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try {
       // key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springarticle.jks");
            keyStore.load(resourceAsStream,"spring".toCharArray());
        }
        catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw new SpringJavaStoreException("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springarticle", "spring".toCharArray());
        }
        catch(KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            throw new SpringJavaStoreException("Exception occured while retrieving public key from keystore");
        }
    }

    //Here is our validation class for our token
    public boolean validateToken(String jwt){
        // If this runs and doesn't return errors, then return true
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        //Jwts.parser().setSigningKey(key);
        return true;
    }

    private PublicKey getPublicKey() {
        try{
            return keyStore.getCertificate("springarticle").getPublicKey();
        }
        catch(KeyStoreException e){
            throw new SpringJavaStoreException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUserNameFromJwt(String token) {
        System.out.println("This is our token: " + token);
        System.out.println("This is our public key:" + getPublicKey());
        Claims claims = Jwts.parser()
                            .setSigningKey(getPublicKey())
                            .parseClaimsJws(token)
                            .getBody();
        System.out.println("Here is our claim: " + claims);
        return claims.getSubject();
    }
}
