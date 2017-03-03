package cv.sso.sp.logout;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lastpass.saml.IdPConfig;
import com.lastpass.saml.SAMLClient;
import com.lastpass.saml.SAMLException;
import com.lastpass.saml.SAMLInit;
import com.lastpass.saml.SPConfig;

import cv.sso.sp.acs.CVAssertionConsumerService;

/**
 * Servlet implementation class SsoLogoutServlet
 */
public class SsoLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SsoLogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			SAMLInit.initialize();
			
			IdPConfig idpConfig = new IdPConfig(
					CVAssertionConsumerService.class.getClassLoader().getResourceAsStream("samlidp_IBM_metadata_CIS.xml"));
			SPConfig spConfig = new SPConfig(CVAssertionConsumerService.class.getClassLoader().getResourceAsStream("sp-metadata.xml"));
			SAMLClient client = new SAMLClient(spConfig, idpConfig);
			
			String samlRequestStr = client.generateAuthnRequest(UUID.randomUUID().toString());
			
			String urlParam = URLEncoder.encode(samlRequestStr,"UTF-8");
			
			response.setHeader("Cache-Control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.sendRedirect("https://w3id.alpha.sso.ibm.com/auth/sps/samlidp/saml20/slo?SAMLRequest=" + urlParam);
			
			
		} catch (SAMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
