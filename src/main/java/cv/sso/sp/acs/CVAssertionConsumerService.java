package cv.sso.sp.acs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lastpass.saml.AttributeSet;
import com.lastpass.saml.IdPConfig;
import com.lastpass.saml.SAMLClient;
import com.lastpass.saml.SAMLException;
import com.lastpass.saml.SAMLInit;
import com.lastpass.saml.SPConfig;

import cv.sso.util.Constant;
import cv.sso.util.JWTUtil;

/**
 * Servlet implementation class CVAssertionConsumerService
 */
public class CVAssertionConsumerService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CVAssertionConsumerService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		response.setCharacterEncoding("UTF-8");

		
		PrintWriter out = response.getWriter();		
		try {
			SAMLInit.initialize();
			IdPConfig idpConfig = new IdPConfig(
					CVAssertionConsumerService.class.getClassLoader().getResourceAsStream("samlidp_IBM_metadata_CIS.xml"));
			SPConfig spConfig = new SPConfig(CVAssertionConsumerService.class.getClassLoader().getResourceAsStream(Constant.getSPMetaDataFileName()));
			SAMLClient client = new SAMLClient(spConfig, idpConfig);
			String authresponse = request.getParameter("SAMLResponse");
		
			String target = request.getParameter("RelayState");
			System.out.println("--target:" + target);
			
			AttributeSet attributeSet = client.validateResponse(authresponse);	
			
			String nameId = attributeSet.getNameId();
			System.out.println("NameId=" + nameId);
			if(nameId != null && nameId != "") {
				System.out.println("--SAMLResponse Success.");
				//request.getSession(true).setAttribute("SSO", nameId);
				String logintoken = JWTUtil.createJWT("SSOService", "login for CV User", 10 * 1000,nameId);
				response.sendRedirect("https://9.3.68.206?logintoken="+ logintoken);
			} else {
				System.out.println("--SAMLResponse Fail.");
				//request.getSession(true).removeAttribute("SSO");
				out.print("W3ID SSO Failure.");
			}
		} catch (SAMLException e) {
			e.printStackTrace();
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
