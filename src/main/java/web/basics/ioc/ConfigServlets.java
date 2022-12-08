package web.basics.ioc;

import com.google.inject.servlet.ServletModule;
import web.basics.servlets.*;
import web.basics.filters.*;

public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
        filter("/*").through(DemoFilter.class);
        filter("/*").through(DataFilter.class);
        filter("/*").through(AuthFilter.class);


        serve("/f-upload").with(FileUploadServlet.class);
        serve("/register").with(RegUserServlet.class);
        serve("/filter").with(FilterServlet.class);
        serve("/hello").with(HelloServlet.class);
        serve("/").with(HomeServlet.class);
        serve("/auth").with(AuthServlet.class);
    }
}
