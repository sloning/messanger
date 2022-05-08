package com.messenger.config;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.messenger"})
@EnableElasticsearchRepositories(basePackages = "com.messenger.repository")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private final AppProperties appProperties;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(appProperties.getDatabase().getElasticHost())
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
