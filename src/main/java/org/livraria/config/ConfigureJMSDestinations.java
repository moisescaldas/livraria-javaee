package org.livraria.config;

import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(name = "java:/jms/topics/checkoutsTopic", interfaceName = "javax.jms.Topic")
public class ConfigureJMSDestinations {
}