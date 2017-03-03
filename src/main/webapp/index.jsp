  <%@page import="cv.sso.util.*"%>
<html>
<body>
<h2>Welcome to Cient Vantage!</h2>

For IBMers using SAML, please click below link to login.<br/>
<br/>
<a href="https://w3id.alpha.sso.ibm.com/auth/sps/samlidp/saml20/logininitial?RequestBinding=HTTPPost&PartnerId=<%=Constant.getSAMLParterId() %>&NameIdFormat=email&Target=http://cvhost.com">Login by SAML</a>

<br/>
<br/>

For IBMers using OpenId Connect, please click below link to login.<br/>

<a href="https://w3id.alpha.sso.ibm.com/isam/oidc/endpoint/amapp-runtime-oidcidp/authorize?scope=openid&client_id=MWRjOTVhN2MtMTJhYS00&response_type=code&redirect_uri=<%=Constant.getOIDCRedirectURL() %>&response_mode=form_post&state=12345&nonce=7362CAEA-9CA5-4B43-9BA3-34D7C303EBA7">Login by OIDC</a>



<!-- <a href="https://w3id.alpha.sso.ibm.com/pkmslogout">Log Out</a> -->


<%-- <%=JWTUtil.createJWT("SSOService", "login for CV User", 8 * 3600 * 1000,"yzmiaonb@cn.ibm.com")
%> --%>

</body>
</html>
