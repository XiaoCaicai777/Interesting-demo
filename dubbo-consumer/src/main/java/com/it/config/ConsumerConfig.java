package com.it.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class ConsumerConfig {

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setPort(2181);
        registryConfig.setTimeout(10000);
        //设置客户端很重要
        registryConfig.setClient("curator");
        registryConfig.setAddress("192.168.222.154");
        return registryConfig;
    }

    @Bean
    public org.apache.dubbo.config.ConsumerConfig consumerConfig(){
        org.apache.dubbo.config.ConsumerConfig consumerConfig = new org.apache.dubbo.config.ConsumerConfig();
        consumerConfig.setRetries(3);
        consumerConfig.setVersion("3");
        consumerConfig.setCheck(false);
        consumerConfig.setStub("com.it.impl.UserServiceStub");
        return consumerConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(28101);
        return protocolConfig;
    }
}
