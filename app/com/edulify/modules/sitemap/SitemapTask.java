package com.edulify.modules.sitemap;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import play.Logger;
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

    private Logger.ALogger log = play.Logger.of(SitemapTask.class);

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

        String baseUrl = sitemapConfig.getBaseUrl();
        try {
            WebSitemapGenerator generator = new WebSitemapGenerator(baseUrl, sitemapConfig.getBaseDir());
            List<UrlProvider> providers = sitemapProviders.getProviders();
            for (UrlProvider urlProvider : providers) {
                urlProvider.addUrlsTo(generator);
            }
            generator.write();
            try {
                generator.writeSitemapsWithIndex();
            } catch (RuntimeException ex) {
                log.warn("Could not create sitemap index", ex);
            }
        } catch(MalformedURLException ex) {
            log.error("Oops! Can't create a sitemap generator for the given baseUrl " + baseUrl, ex);
        }
    }
}
