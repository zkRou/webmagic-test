package com.kris.webmagic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kairou Zeng
 */
@Component
@ConfigurationProperties("file")
@Data
public class FileConfig {

    private String dir;
}
