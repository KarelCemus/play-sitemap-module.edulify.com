package com.edulify.modules.sitemap;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import play.inject.Injector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class SitemapProviders {

    private Injector injector;
    private Config configuration;
    private Map<String,List<UrlProvider>> providers = new HashMap<String,List<UrlProvider>>();

    @Inject
    public SitemapProviders(Injector injector, Config configuration) {
        this.injector = injector;
        this.configuration = configuration;
        this.init();
    }

    public Map<String, List<UrlProvider>> getProviders() {
        return providers;
    }

    public List<UrlProvider> getProviders(String domain) {
        return providers.get(domain);
    }

    public Set<String> getDomains() {
        return providers.keySet();
    }

    private void init() {
        Config domainProviders = configuration.getConfig("sitemap.providers");

        for (Map.Entry<String, ConfigValue> entry : domainProviders.entrySet()) {
            String domain = entry.getKey();
            List<UrlProvider> providers = new ArrayList<>();
            providers.add(injector.instanceOf(AnnotationUrlProvider.class));

            String allProvidersClasses = configuration.getString("sitemap.providers." + domain);

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
            this.providers.put(domain, providers);
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
