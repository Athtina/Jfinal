package com.example.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import java.io.StringWriter;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/12 9:53
 */
public class TemplateUtils {

  private static Logger logger = LoggerFactory.getLogger(TemplateUtils.class);

  private static String ENCODING = "UTF-8";

  public static String getValue(String templateStr, Map<String,Object> paramsMap){
    StringWriter out = null;
    try {
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
      cfg.setDefaultEncoding(ENCODING);
      cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_26));
      StringTemplateLoader stringLoader = new StringTemplateLoader();
      stringLoader.putTemplate("content", templateStr);
      cfg.setTemplateLoader(stringLoader);
      Template template = cfg.getTemplate("content",ENCODING);
      out = new StringWriter();
      template.process(paramsMap, out);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return templateStr;
    }
    return out.getBuffer().toString();
  }
}
