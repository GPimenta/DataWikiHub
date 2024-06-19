package org.wikipedia.controller;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.lifecycle.Startable;

public class ProducerTest2 {

    private Startable container;

    public void launchContainer() {
        DockerComposeContainer<?> container = new DockerComposeContainer<>("path to docker-compose");

        container.start();
    }

}
