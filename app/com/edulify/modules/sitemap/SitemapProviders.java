package com.edulify.modules.sitemap;

import com.typesafe.config.Config;
import play.inject.Injector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class SitemapProviders {

    private Injector injector;
    private Config configuration;
    private List<UrlProvider> providers = new ArrayList<>();

    @Inject
    public SitemapProviders(Injector injector, Config configuration) {
        this.injector = injector;
        this.configuration = configuration;
        this.init();
    }

    public List<UrlProvider> getProviders() {
        return providers;
    }

    private void init() {
        providers.add(injector.instanceOf(AnnotationUrlProvider.class));

        String allProvidersClasses = configuration.getString("sitemap.providers");

        if (allProvidersClasses != null) {
            String[] providerClasses = allProvidersClasses.split(",");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            for (String provider : providerClasses) {
                if (!"".equals(provider)) {
                    Class<?> clazz = getProviderClass(provider, classLoader);
                    providers.add((UrlProvider)injector.instanceOf(clazz));
                }
            }
        }
    }

    private Class<?> getProviderClass(String name, ClassLoader classLoader) {
        try {
            return classLoader.loadClass(name);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Was not able to load provider class: " + name, ex);
        }
    }
}
