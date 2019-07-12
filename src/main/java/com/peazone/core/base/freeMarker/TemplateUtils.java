package com.peazone.core.base.freeMarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class TemplateUtils {

    private static final Logger logger = LoggerFactory.getLogger(TemplateUtils.class);

    private volatile static Configuration cfg;

    private TemplateUtils() {
    }

    private static Configuration getInstance() {
        if (cfg == null) {
            synchronized (TemplateUtils.class) {
                if (cfg == null) {
                    cfg = new Configuration(Configuration.VERSION_2_3_28);

                    // Specify the source where the template files come from.
                    // Here I set a
                    // plain directory for it, but non-file-system sources are
                    // possible too:
                    // cfg.setDirectoryForTemplateLoading(new
                    // File("/where/you/store/templates"));

                    // Set the preferred charset template files are stored in.
                    // UTF-8 is
                    // a good choice in most applications:
                    cfg.setDefaultEncoding("UTF-8");

                    // Sets how errors will appear.
                    // During web page *development*
                    // TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
                    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

                    // Don't log exceptions inside FreeMarker that it will
                    // thrown at you anyway:
                    cfg.setLogTemplateExceptions(false);

                    // Wrap unchecked exceptions thrown during template
                    // processing into TemplateException-s.
                    cfg.setWrapUncheckedExceptions(true);
                }
            }
        }
        return cfg;
    }

    /**
     * 渲染模板
     * 
     * @param rootMap
     *            参数Map
     * @param templateName
     *            模板名称
     * @param templateContent
     *            模板内容
     * @return 渲染后模板
     * @throws TemplateException
     * @throws IOException
     */
    public static String renderTemplate(Map<String, Object> rootMap, String templateName, String templateContent) throws TemplateException, IOException {
        if (templateContent == null) {
            return null;
        }
        if (templateName == null) {
            templateName = String.valueOf(Calendar.getInstance().getTimeInMillis());
        }
        if (rootMap == null || rootMap.size() == 0) {
            return templateContent;
        }
        cfg = TemplateUtils.getInstance();
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(templateName, templateContent);
        cfg.setTemplateLoader(loader);
        StringWriter writer = new StringWriter();
        try {
            Template template = cfg.getTemplate(templateName, "utf-8");
            try {
                template.process(rootMap, writer);
            } catch (TemplateException e) {
                logger.error("FreeMarker render template[id={}] error:", templateName, e);
                throw e;
            }
        } catch (IOException e) {
            logger.error("FreeMarker get template[id={}] error:", templateName, e);
            throw e;
        }

        return writer.toString();
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put("user", "Tom");
            Map<String, String> latestProduct = new HashMap<String, String>();
            latestProduct.put("url", "products/greenmouse.html");
            latestProduct.put("name", "green mouse");
            rootMap.put("latestProduct", latestProduct);
            String templateContent = "<html>\r\n" + "<head>\r\n" + "  <title>Welcome!</title>\r\n" + "</head>\r\n" + "<body>\r\n" + "  <h1>Welcome ${user}!</h1>\r\n" + "  <p>Our latest product:\r\n" + "  <a href=\"${latestProduct.url}\">${latestProduct.name}</a>!\r\n" + "</body>\r\n" + "</html>";
            String renderTemplate = TemplateUtils.renderTemplate(rootMap, "testTemplate", templateContent);
            System.out.println(renderTemplate);
        } catch (Exception e) {
        }

    }

}
