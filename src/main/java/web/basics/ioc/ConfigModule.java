package web.basics.ioc;

import com.google.inject.AbstractModule;
import web.basics.services.DataService;
import web.basics.services.HashServices.HashService;
import web.basics.services.HashServices.Sha1HashService;
import web.basics.services.MySqlService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataService.class).to(MySqlService.class);
        bind(HashService.class).to(Sha1HashService.class);
    }
}
