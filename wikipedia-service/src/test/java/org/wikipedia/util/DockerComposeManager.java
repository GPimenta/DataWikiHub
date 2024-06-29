package org.wikipedia.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;

import java.io.File;

public class DockerComposeManager {

    private static final Logger logger = LoggerFactory.getLogger(DockerComposeManager.class.getName());
    private DockerComposeContainer<?> container;


    public void startContainer() {
        container = new DockerComposeContainer<>(new File("docker-compose.yaml"))
                .withLogConsumer("broker", new Slf4jLogConsumer(logger))
                .withLogConsumer("schema-registry", new Slf4jLogConsumer(logger))
                .withLogConsumer("connect", new Slf4jLogConsumer(logger))
                .withLogConsumer("control-center", new Slf4jLogConsumer(logger))
                .withLogConsumer("ksqldb-server", new Slf4jLogConsumer(logger))
                .withLogConsumer("ksql-datagen", new Slf4jLogConsumer(logger))
                .withLogConsumer("rest-proxy", new Slf4jLogConsumer(logger))
                .withExposedService("broker", 9092)
                .withExposedService("schema-registry", 8081)
                .withExposedService("connect", 8083)
                .withExposedService("control-center", 9021)
                .withExposedService("ksqldb-server", 8088)
                .withExposedService("rest-proxy", 8082)
                .withExposedService("mockserver", 1080)
                .waitingFor("broker", new WaitAllStrategy()
                                .withStrategy(Wait.forListeningPort())
                                .withStrategy(Wait.forLogMessage(".*Started NetworkTrafficServerConnector.*\\n", 1)))
                .waitingFor("schema-registry", new WaitAllStrategy()
                        .withStrategy(Wait.forHttp("/subjects").forPort(8081).forStatusCode(200)))
                .waitingFor("connect", new WaitAllStrategy()
                        .withStrategy(Wait.forHttp("/connectors").forPort(8083).forStatusCode(200)))
                .waitingFor("control-center", new WaitAllStrategy()
                        .withStrategy(Wait.forHttp("/").forPort(9021).forStatusCode(200)))
                .waitingFor("ksqldb-server", new WaitAllStrategy()
                        .withStrategy(Wait.forHttp("/info").forPort(8088).forStatusCode(200)))
                .waitingFor("rest-proxy", new WaitAllStrategy()
                        .withStrategy(Wait.forHttp("/").forPort(8082).forStatusCode(200)))
                .waitingFor("mockserver", new WaitAllStrategy()
                        .withStrategy(Wait.forListeningPort())
                        .withStrategy(Wait.forLogMessage(".*started on port: 1080.*\\n", 1)));


//        DockerComposeContainer<?> container = new DockerComposeContainer<>("path to docker-compose")
//                .withLogConsumer("broker",  new Slf4jLogConsumer(logger))
//                .withLogConsumer("schema-registry", new Slf4jLogConsumer(logger))
//                .withExposedService("broker", 9092)
//                .withExposedService("schema-registry", 8081)
//                .waitingFor("broker", Wait.forListeningPort())
//                .waitingFor("schema-registry", new HttpWaitStrategy().forPort(8081).forStatusCode(200));

        container.start();
    }
    public void stopContainer() {
        if (container != null) {
            container.stop();
        }
    }

    public String getServiceHost(String serviceName, int servicePort) {
        return container.getServiceHost(serviceName, servicePort);
    }

    public int getServicePort(String serviceName, int servicePort) {
        return container.getServicePort(serviceName, servicePort);
    }

}
