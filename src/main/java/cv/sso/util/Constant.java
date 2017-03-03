package cv.sso.util;

public class Constant {
	
	private static String based_on_site = "vantagevm02"; //198 , localhost, vantagevm02
	
	private static String OIDC_REDIRECT_URL_LOCAL="https%3a%2f%2flocalhost%3a8443%2fsp-demo%2foidc%2ftokenConsumer";

	private static String OIDC_REDIRECT_URL_198="https%3a%2f%2f9.115.81.198%3a8483%2fsp-demo%2foidc%2ftokenConsumer";
	
	private static String OIDC_REDIRECT_URL_vantagevm02="https%3a%2f%2fvantagevm02.austin.ibm.com%3a9444%2fsp-demo%2foidc%2ftokenConsumer";
	
	private static String CV_SERVER_vantagevm02 = "https://vantagevm02.austin.ibm.com:9444";
	private static String CV_SERVER_206 = "http://9.3.68.206:9081";
	
	public static String getCVServer()
	{
		if( based_on_site.equals("localhost") )
		{
			return CV_SERVER_206;
		}
		else if( based_on_site.equals("198") )
		{
			return CV_SERVER_206;
		}
		else if( based_on_site.equals("vantagevm02") )
		{
			return CV_SERVER_vantagevm02;
		}
		
		return CV_SERVER_206;
	}
	
	
	public static String getOIDCRedirectURL()
	{
		if( based_on_site.equals("localhost") )
		{
			return OIDC_REDIRECT_URL_LOCAL;
		}
		else if( based_on_site.equals("198") )
		{
			return OIDC_REDIRECT_URL_198;
		}
		else if( based_on_site.equals("vantagevm02") )
		{
			return OIDC_REDIRECT_URL_vantagevm02;
		}
		
		return OIDC_REDIRECT_URL_LOCAL;
	}
	
	
	private static String SAML_PARTERID_LOCAL="http://mock-sp";

	private static String SAML_PARTERID_198="http://mock-sp-chen";
	
	public static String getSAMLParterId()
	{
		return based_on_site.equals("localhost") ? SAML_PARTERID_LOCAL : SAML_PARTERID_198;
	}
	
	private static String SP_METADATA_FILENAME_LOCAL="sp-metadata.xml";

	private static String SP_METADATA_FILENAME_198="sp-metadata_198.xml";
	
	public static String getSPMetaDataFileName()
	{
		return based_on_site.equals("localhost") ?  SP_METADATA_FILENAME_LOCAL : SP_METADATA_FILENAME_198;
	}
}
