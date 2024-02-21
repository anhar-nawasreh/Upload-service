package com.stream.upload.video.webapp.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
import reactor.netty.tcp.TcpClient;

import reactor.netty.http.client.HttpClient;
import java.time.Duration;

@Configuration
public class WebClientConfiguration {

    @Value("${custom.timeout.connect}")
    private int connectTimeout;

    @Bean
    public WebClient webClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true);

        HttpClient httpClient = HttpClient.from(tcpClient)
                .protocol(HttpProtocol.H2)
                .option(ChannelOption.SO_KEEPALIVE, true);

        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
                .build();
    }
}