package cv.sso.util;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import cv.sso.sp.acs.CVAssertionConsumerService;

import java.io.InputStream;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import io.jsonwebtoken.*;
import java.util.Date;  

public class JWTUtil {

	public static String createJWT( String issuer, String subject, long ttlMillis, String UserId) {
		 
		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		 
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		
		//String s ;
		
		//We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("bgt5nhy6");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		 
		  //Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setIssuedAt(now)
		                                .setSubject(subject)
		                                .setIssuer(issuer).claim("userid", UserId)
		                                .signWith(signatureAlgorithm, signingKey);
		 
		//if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
		    long expMillis = nowMillis + ttlMillis;
		    Date exp = new Date(expMillis);
		    builder.setExpiration(exp);
		}
		 
		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
		}
	
	public static String parseJWT(String jwt) {
		//This line will throw an exception if it is not a signed JWS (as expected)
		
		Claims claims;
		
		try {

			claims = Jwts.parser()     
					   .setSigningKey(DatatypeConverter.parseBase64Binary("bgt5nhy6"))
					   .parseClaimsJws(jwt).getBody();

			System.out.println("claim : " + claims.toString());
			
			return  claims.toString();
		    //OK, we can trust this JWT
		/*	System.out.println("ID: " + claims.getId());
			System.out.println("Subject: " + claims.getSubject());
			System.out.println("Issuer: " + claims.getIssuer());
			System.out.println("Expiration: " + claims.getExpiration());
			System.out.println("userid: " + claims.get("userid"));*/
		} 
		 catch (ExpiredJwtException e) {

			    //don't trust the JWT!
				System.out.println("the token is expired");
			}
		catch (SignatureException e) {

		    //don't trust the JWT!
			System.out.println("the token is invalid");
		}
		
		return "";
		
		}
	
	public static String validateIDToken(String jwt_id_token) {
		//This line will throw an exception if it is not a signed JWS (as expected)
		
		Claims claims;
		
		try {
			
			InputStream is = JWTUtil.class.getClassLoader().getResourceAsStream("oidc_w3id_staging.cer");
			//FileInputStream fin = new FileInputStream("PathToCertificate");
			CertificateFactory f = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate)f.generateCertificate(is);
			PublicKey pk = certificate.getPublicKey();

			claims = Jwts.parser()
					   .setSigningKey(pk)
					   .parseClaimsJws(jwt_id_token).getBody();

			
			System.out.println("claims: " + claims.toString());
			
			return claims.get("emailAddress").toString();
		    //OK, we can trust this JWT
		/*	System.out.println("ID: " + claims.getId());
			System.out.println("Subject: " + claims.getSubject());
			System.out.println("Issuer: " + claims.getIssuer());
			System.out.println("Expiration: " + claims.getExpiration());
			System.out.println("userid: " + claims.get("userid"));*/
		} 
		 catch (ExpiredJwtException e) {

			    //don't trust the JWT!
				System.out.println("the token is expired");
			}
		catch (SignatureException e) {

		    //don't trust the JWT!
			System.out.println("the token is invalid");
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
}
