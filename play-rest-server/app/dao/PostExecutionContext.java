package dao;

import org.apache.pekko.actor.ActorSystem;
import play.api.libs.concurrent.CustomExecutionContext;


import javax.inject.Inject;

public class PostExecutionContext extends CustomExecutionContext {

    @Inject
    public PostExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "post.repository");
    }
}
