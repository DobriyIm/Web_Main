package web.basics.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;

@Singleton
public class ConfigListener extends GuiceServletContextListener {
    protected Injector getInjector() {
        return Guice.createInjector(
                new ConfigServlets(),
                new ConfigModule()
        );
    }
}
