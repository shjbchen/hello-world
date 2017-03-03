package cv.sso.rp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

import cv.sso.entity.RequestTokenResponse;
import cv.sso.util.Constant;
import cv.sso.util.HttpUtil;
import cv.sso.util.JWTUtil;

/**
 * Servlet implementation class OIDCTokenConsumer
 */
public class OIDCTokenConsumer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OIDCTokenConsumer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String code = request.getParameter("code");
		if(StringUtils.isEmpty(code))
		{	
			
			response.getWriter().append("OIDC authentication failed.");
			return ;
		}
		else
		{
			String redirectUrl = Constant.getOIDCRedirectURL();
			String postParam ="code="+code+"&client_id=MWRjOTVhN2MtMTJhYS00&client_secret=ZmY1ZDI5MmMtZmU0YS00&redirect_uri="+redirectUrl+"&grant_type=authorization_code";
			
			String responseStr = HttpUtil.sendPost("https://w3id.alpha.sso.ibm.com/isam/oidc/endpoint/amapp-runtime-oidcidp/token", postParam);
			
			
			Gson gson = new Gson();
			
			RequestTokenResponse responseObj = gson.fromJson(responseStr, RequestTokenResponse.class);
			if(responseObj.getId_token() == null)
			{
				response.getWriter().append("failed to get id token from w3id server.");
				return ;
			}
			
			String userId = JWTUtil.validateIDToken(responseObj.getId_token());
			if(StringUtils.isEmpty(userId))
			{
				response.getWriter().append("failed to get userid from id token.");
				return ;
			}
			
			String logintoken = JWTUtil.createJWT("SSOService", "login for CV User", 10 * 1000,userId);
			response.sendRedirect("http://9.3.68.206:9081?logintoken="+ logintoken);
			
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
