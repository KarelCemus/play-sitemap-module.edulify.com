package com.edulify.modules.sitemap;

import com.typesafe.config.Config;
import play.Environment;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

@Singleton
public class SitemapConfig {

    private Config configuration;
    private Environment environment;


    @Inject
    public SitemapConfig(Config configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    public String getDispatcherName() {
        return configuration.getString("sitemap.dispatcher.name");
    }

    public String getInitialDelay() {
        return configuration.getString("sitemap.initialDelay");
    }

    public String getExecutionInterval() {
        return configuration.getString("sitemap.executionInterval");
    }

    public String getBaseUrl() {
        return configuration.getString("sitemap.baseUrl");
    }

    public File getBaseDir() {
        String baseDir = configuration.getString("sitemap.baseDir");
        return baseDir == null ? environment.getFile("public") : new File(baseDir);
    }
}
