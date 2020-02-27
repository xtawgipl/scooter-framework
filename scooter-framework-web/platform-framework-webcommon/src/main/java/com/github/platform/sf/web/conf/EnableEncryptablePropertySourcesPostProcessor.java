package com.github.platform.sf.web.conf;

import com.github.platform.sf.common.util.security.std.DESEncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class EnableEncryptablePropertySourcesPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {

	private Logger logger = LoggerFactory.getLogger(EnableEncryptablePropertySourcesPostProcessor.class);


	private String prefix = "ENC(";
	private String suffix = ")";
	private static final String KEY = "123456";

	private ConfigurableEnvironment environment;

    public EnableEncryptablePropertySourcesPostProcessor(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

	@Override
	public int getOrder() {
		
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.info("Post-processing PropertySource instances");
        MutablePropertySources propSources = environment.getPropertySources();
        for (PropertySource<?> ps : propSources) {
        	if(ps.getName().startsWith("applicationConfig")){//applicationConfig 为.yml配置文件中的
            	Map<String, Object> source = (LinkedHashMap<String, Object>)ps.getSource();
            	for(Entry<String, Object> entry : source.entrySet()){
            		if(isEncrypt(entry.getValue().toString())){
            			source.put(entry.getKey(), decrypt(entry.getValue().toString()));
            		}
            	}
        	}

        }
	}
	
	private boolean isEncrypt(String val){
		if(val == null || "".equals(val)){
			return false;
		}
		return val.startsWith(prefix) && val.endsWith(suffix);
	}
	
	
	private String decrypt(String val){
		String encryptStr = val.substring(prefix.length(), val.length() - suffix.length());
		try {
			return DESEncryptUtil.decrypt(encryptStr, KEY);
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException("解密失败: ", e);
		}
	}

}
