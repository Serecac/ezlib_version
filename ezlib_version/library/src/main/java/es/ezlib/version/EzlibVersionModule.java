package es.ezlib.version;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EzlibVersionModule {

    @Provides
    @Singleton
    EzlibVersionManager provideEzlibVersionManager(EzlibVersionConfiguration configuration) {
        return new EzlibVersionManager(configuration);
    }
}
