package com.edulify.modules.sitemap;

import static java.lang.String.format;

import java.io.File;

import play.Logger;
import play.mvc.Result;
import play.mvc.Controller;

import javax.inject.Inject;

public class Sitemaps extends Controller {

  private SitemapConfig configuration;

  private Logger.ALogger log = play.Logger.of(Sitemaps.class);

  @Inject
  public Sitemaps(SitemapConfig configuration) {
    this.configuration = configuration;
  }

  public Result sitemap(String sitemapSuffix) {
    String sitemap = String.format("sitemap%s.xml", sitemapSuffix);
    File baseDir = configuration.getBaseDir();
    File sitemapFile = new File(baseDir, sitemap);
    log.debug("Delivering sitemap file " + sitemapFile.getAbsolutePath());
    if(canDelivery(sitemapFile)) {
      return ok(sitemapFile, true);
    }
    if ("_index".equals(sitemapSuffix)) {
      return sitemap("");
    }
    log.error(format("%s sitemap file was not found at directory %s", sitemapFile.getAbsolutePath(), baseDir.getAbsolutePath()));
    return notFound();
  }

  private boolean canDelivery(File file) {
    File baseDir = configuration.getBaseDir();
    return  file.exists() &&
            file.isFile() &&
            file.getParentFile().equals(baseDir);
  }
}