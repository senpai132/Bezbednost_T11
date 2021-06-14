package adminapi.adminaplication.config;

import adminapi.adminaplication.wrapper.XSSRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String url = ((HttpServletRequest) request).getRequestURL().toString();
        System.out.println("Url: " + url);

        if(!url.equals("https://localhost:8080/api/ocsp/check")) {
            //System.out.println("XSS usao");
            XSSRequestWrapper wrappedRequest = new XSSRequestWrapper((HttpServletRequest) request);

            String body = IOUtils.toString(wrappedRequest.getReader());
            //System.out.println("Pre: " + body.length());
            if (!StringUtils.isBlank(body)) {
                //System.out.println("Ulazim ciscenje");
                body = XSSRequestWrapper.stripXSS(body);
                //System.out.println("Posle strip: " + body.length());
                wrappedRequest.resetInputStream(body.getBytes()); //ovo je probelm za OCSP bytes nisu dobri
                //System.out.println("Posle: " + body.length());
            }

            chain.doFilter(wrappedRequest, response);
        }
        else {

            chain.doFilter(request, response);
        }



    }

}