package com.hunger.saviour.portal.dtos;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyConfigurationProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
