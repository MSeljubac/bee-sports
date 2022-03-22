package io.beesports.utils;

import io.beesports.domain.consts.CalendarConsts;
import io.beesports.domain.consts.UrlConsts;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Component
public class UrlBuilder {

    @Value("${pandascore.base.url}")
    private String pandascoreBaseUrl;

    @Value("${pandascore.token}")
    private String psToken;

    private final CalendarUtils calendarUtils;

    @Autowired
    public UrlBuilder(CalendarUtils calendarUtils) {
        this.calendarUtils = calendarUtils;
    }

    public String getPastMatchesUrl(int[] leagueIds, int page) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(pandascoreBaseUrl + UrlConsts.PAST_MATCHES_URL)).newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("page", String.valueOf(page));

        Date dateThreeDaysBack = calendarUtils.addDays(Calendar.getInstance(), -3).getTime();
        Date dateToday = calendarUtils.addDays(Calendar.getInstance(), 0).getTime();

        String rangeStart = calendarUtils.getDateString(CalendarConsts.PANDA_SCORE_DATE_PATTERN, dateThreeDaysBack);
        String rangeEnd = calendarUtils.getDateString(CalendarConsts.PANDA_SCORE_DATE_PATTERN, dateToday);

        urlBuilder.addQueryParameter("range[begin_at]", rangeStart + "," + rangeEnd);

        if (leagueIds.length > 0) {
            urlBuilder.addQueryParameter("filter[league_id]", Arrays.toString(leagueIds));
        }

        return urlBuilder.build().toString();
    }

    public String getRunningMatchesUrl(int[] leagueIds, int page) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(pandascoreBaseUrl + UrlConsts.RUNNING_MATCHES_URL)).newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("page", String.valueOf(page));

        if (leagueIds.length > 0) {
            urlBuilder.addQueryParameter("filter[league_id]", Arrays.toString(leagueIds));
        }

        return urlBuilder.build().toString();
    }

    public String getUpcomingMatchesUrl(int[] leagueIds, int page) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(pandascoreBaseUrl + UrlConsts.UPCOMING_MATCHES_URL)).newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("page", String.valueOf(page));

        Date dateThreeDaysForward = calendarUtils.addDays(Calendar.getInstance(), 3).getTime();
        Date dateToday = calendarUtils.addDays(Calendar.getInstance(), 0).getTime();

        String rangeStart = calendarUtils.getDateString(CalendarConsts.PANDA_SCORE_DATE_PATTERN, dateToday);
        String rangeEnd = calendarUtils.getDateString(CalendarConsts.PANDA_SCORE_DATE_PATTERN, dateThreeDaysForward);

        urlBuilder.addQueryParameter("range[scheduled_at]", rangeStart + "," + rangeEnd);

        if (leagueIds.length > 0) {
            urlBuilder.addQueryParameter("filter[league_id]", Arrays.toString(leagueIds));
        }

        return urlBuilder.build().toString();
    }

    public String getLeagues(int page) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(pandascoreBaseUrl + UrlConsts.LEAGUES_URL)).newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("page", String.valueOf(page));
        return urlBuilder.build().toString();
    }
}
