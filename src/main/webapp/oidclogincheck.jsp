<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>check oidc login result</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery1.11.min.js"></script>
<script>
$(document).ready(function(){
	//request.getParameter("state") compare the state to prevent request forgy.
	
	//console.log(code);
	
	$("form").submit();
	
	/* $.post("https://w3id.alpha.sso.ibm.com/isam/oidc/endpoint/amapp-runtime-oidcidp/token",
			$("form").serialize(),
			function(data,status){
			    alert("Data: " + data + "\nStatus: " + status);
	}); */
	
  }
);
</script>
</head>
<body>

<form name="input" action="https://w3id.alpha.sso.ibm.com/isam/oidc/endpoint/amapp-runtime-oidcidp/token" method="post">

<input type="hidden" name="code" value="<%=request.getParameter("code")%>" >
<input type="hidden" name="client_id" value="MWRjOTVhN2MtMTJhYS00" >
<input type="hidden" name="client_secret" value="ZmY1ZDI5MmMtZmU0YS00" >
<input type="hidden" name="redirect_uri" value="https://localhost:8443/sp-demo/oidclogincheck.jsp" >
<input type="hidden" name="grant_type" value="authorization_code" >
<br />
<input type="submit" value="Submit">
</form>

</body>
</html>