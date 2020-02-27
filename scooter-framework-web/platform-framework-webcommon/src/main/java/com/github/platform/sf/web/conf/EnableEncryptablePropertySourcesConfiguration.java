package com.github.platform.sf.web.conf;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class EnableEncryptablePropertySourcesConfiguration implements EnvironmentAware {

	private ConfigurableEnvironment environment;

	@Bean
	public EnableEncryptablePropertySourcesPostProcessor enableEncryptablePropertySourcesPostProcessor() {
		return new EnableEncryptablePropertySourcesPostProcessor(environment);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (ConfigurableEnvironment) environment;
	}

}
