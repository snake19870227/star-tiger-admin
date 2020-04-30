package com.snake19870227.stiger.admin.monitor.event;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/29
 */
@Component
public class GlobalEventNotifier extends AbstractEventNotifier {

    private static final Logger logger = LoggerFactory.getLogger(GlobalEventNotifier.class);

    protected GlobalEventNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                logger.info("{} {} ({}) is {}",
                        event.getClass().getSimpleName(),
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
            } else {
                logger.info("{} {} ({}) {}",
                        event.getClass().getSimpleName(),
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        event.getType());
            }
        });
    }
}
