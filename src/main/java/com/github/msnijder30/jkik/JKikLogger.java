package com.github.msnijder30.jkik;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class JKikLogger {

	private static JKikLogger theInstance;

	private JKikLogger() {

	}

	public static JKikLogger getInstance() {
		if (theInstance == null) {
			theInstance = new JKikLogger();
		}
		return theInstance;
	}

	public void initialize(Level level) {
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

		LayoutComponentBuilder standardPattern = builder.newLayout("PatternLayout");
		standardPattern.addAttribute("pattern", "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");

		AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
		console.add(standardPattern);
		builder.add(console);

		ComponentBuilder<?> triggeringPolicies = builder.newComponent("Policies")
				.addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
						.addAttribute("size", "1M"));

		AppenderComponentBuilder rollingFile = builder.newAppender("rolling", "RollingFile");
		rollingFile.addAttribute("fileName", "all.log");
		rollingFile.addAttribute("filePattern", "all-%d{MM-dd-yy}.log.gz");
		rollingFile.add(standardPattern);
		rollingFile.addComponent(triggeringPolicies);

		builder.add(rollingFile);

		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(level);
		rootLogger.add(builder.newAppenderRef("stdout"));
		rootLogger.add(builder.newAppenderRef("rolling"));

		builder.add(rootLogger);

		Configurator.initialize(builder.build());
	}
}
