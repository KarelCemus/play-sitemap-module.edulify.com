package com.edulify.modules.sitemap;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SitemapTask implements Runnable {

    private SitemapConfig sitemapConfig;
    private SitemapProviders sitemapProviders;

    // Indicates the application is shutting down, see #22 for more details
    private boolean shuttingDown = false;

    @Inject
    public SitemapTask(SitemapConfig sitemapConfig, SitemapProviders providers, ApplicationLifecycle lifecycle) {
        this.sitemapConfig = sitemapConfig;
        this.sitemapProviders = providers;
        lifecycle.addStopHook(() -> {
            this.shuttingDown = true;
            return CompletableFuture.completedFuture(null);
        });
    }

    @Override
    public void run() {
        // Akka triggers tasks also when it is shutting down
        if (shuttingDown) return;

        for(String domain: sitemapProviders.getDomains()) {
            String baseUrl = sitemapConfig.getBaseUrl(domain);
            try {
                WebSitemapGenerator generator = new WebSitemapGenerator(baseUrl, sitemapConfig.getBaseDir(domain));

                List<UrlProvider> providers = sitemapProviders.getProviders(domain);
                for (UrlProvider urlProvider : providers) {
                    urlProvider.addUrlsTo(domain, generator);
                }
                generator.write();
                try {
                    generator.writeSitemapsWithIndex();
                } catch (RuntimeException ex) {
                    play.Logger.warn("Could not create sitemap index", ex);
                }
            } catch(MalformedURLException ex) {
                play.Logger.error("Oops! Can't create a sitemap generator for the given baseUrl " + baseUrl, ex);
            }
        }
    }
}
