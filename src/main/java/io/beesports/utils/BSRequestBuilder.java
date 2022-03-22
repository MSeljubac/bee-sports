package io.beesports.utils;

import io.beesports.domain.enums.MatchType;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BSRequestBuilder {

    private final UrlBuilder urlBuilder;

    @Autowired
    public BSRequestBuilder(UrlBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

    public Request buildMatchesRequest(MatchType matchType, int[] favoriteLeagueIds, int page) {
        return switch (matchType) {
            case PAST -> new Request.Builder()
                    .url(urlBuilder.getPastMatchesUrl(favoriteLeagueIds, page))
                    .build();
            case CURRENT -> new Request.Builder()
                    .url(urlBuilder.getRunningMatchesUrl(favoriteLeagueIds, page))
                    .build();
            case UPCOMING -> new Request.Builder()
                    .url(urlBuilder.getUpcomingMatchesUrl(favoriteLeagueIds, page))
                    .build();
        };
    }

    public Request buildLeaguesRequest(int page) {
        return new Request.Builder()
                .url(urlBuilder.getLeagues(page))
                .build();
    }

}
