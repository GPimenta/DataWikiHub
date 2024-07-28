package dao;

import org.apache.pekko.actor.ActorSystem;
import play.api.libs.concurrent.CustomExecutionContext;


import javax.inject.Inject;

public class ArticleExecutionContext extends CustomExecutionContext {

    @Inject
    public ArticleExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "post.article");
    }
}
