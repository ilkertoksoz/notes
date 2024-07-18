package com.tr.d_teknoloji.notebook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.boot.SpringApplication.run;

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = {"com.tr.d_teknoloji.notebook.model", "com.tr.d_teknoloji.notebook.repository"})
public class NotebookApplication {

    public static void main(String[] args) {
        Environment env = run(NotebookApplication.class, args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port", "8080");

        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        String appName = env.getProperty("spring.application.name", "Application");
        String[] activeProfiles = env.getActiveProfiles().length > 0 ? env.getActiveProfiles() : new String[]{"default"};

        log.info(
                """
                            
                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\t{}://localhost:{}
                        \tExternal: \t{}://{}:{}
                        \tProfile(s): \t{}
                        ----------------------------------------------------------""",
                appName,
                protocol,
                serverPort,
                protocol,
                hostAddress,
                serverPort,
                String.join(", ", activeProfiles));
    }
}
