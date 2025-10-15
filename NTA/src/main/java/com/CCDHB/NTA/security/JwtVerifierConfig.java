//package com.CCDHB.NTA.security;
//
//import com.auth0.jwk.Jwk;
//import com.auth0.jwk.JwkException;
//import com.auth0.jwk.JwkProvider;
//import com.auth0.jwk.UrlJwkProvider;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.RSAKeyProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.security.interfaces.RSAPublicKey;
//import java.security.interfaces.RSAPrivateKey;
//
//@Configuration
//public class JwtVerifierConfig {
//
//    @Bean
//    public JWTVerifier jwtVerifier() throws MalformedURLException {
//        String jwksUrl = "https://large-catfish-95.clerk.accounts.dev/.well-known/jwks.json";
//        JwkProvider jwkProvider = new UrlJwkProvider(new URL(jwksUrl));
//
//        RSAKeyProvider keyProvider = new RSAKeyProvider() {
//            @Override
//            public RSAPublicKey getPublicKeyById(String keyId) {
//                try {
//                    Jwk jwk = jwkProvider.get(keyId);
//                    return (RSAPublicKey) jwk.getPublicKey();
//                } catch (JwkException e) {
//                    throw new RuntimeException("Failed to get public key for id: " + keyId, e);
//                }
//            }
//
//            @Override
//            public RSAPrivateKey getPrivateKey() {
//                // Not needed for verifying tokens
//                return null;
//            }
//
//            @Override
//            public String getPrivateKeyId() {
//                return null;
//            }
//        };
//
//        Algorithm algorithm = Algorithm.RSA256(keyProvider);
//
//        return JWT.require(algorithm)
//                .withIssuer("https://large-catfish-95.clerk.accounts.dev")
//                .build();
//    }
//}
