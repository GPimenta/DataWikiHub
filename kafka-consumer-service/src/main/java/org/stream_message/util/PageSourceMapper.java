package org.stream_message.util;

import org.stream_message.model.PageSource;
import org.stream_message.model.PageSourcePostgres;

public class PageSourceMapper {

    public static PageSourcePostgres toPostgres(PageSource pageSource) {
        return new PageSourcePostgres.Builder()
                .id(pageSource.getId())
                .key(pageSource.getKey())
                .title(pageSource.getTitle())
                .latest(pageSource.getLatest().getTimeStamp())
                .contentModel(pageSource.getContentModel())
                .source(pageSource.getSource())
                .redirectTarget(pageSource.getRedirectTarget())
                .build();
    }
}
