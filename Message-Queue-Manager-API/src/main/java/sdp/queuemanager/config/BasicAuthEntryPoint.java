package sdp.queuemanager.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
public class BasicAuthEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.addHeader("Authentication", "Basic Authentication Required to access " + getRealmName());
		response.setStatus(401);
		PrintWriter writer = response.getWriter();
		writer.println(authException.getMessage());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("Message Queue Manager");
		super.afterPropertiesSet();
	}
}
