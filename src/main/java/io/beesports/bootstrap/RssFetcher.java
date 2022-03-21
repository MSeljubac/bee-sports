package io.beesports.bootstrap;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import io.beesports.domain.entities.Article;
import io.beesports.mappers.ArticleMapper;
import io.beesports.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class RssFetcher {

    //@Value("${lol.rss.feeds.list}")
    private String rssFeedsUrls;

    private final ArticleMapper articleMapper;
    private final IArticleRepository articleRepository;

    @Autowired
    public RssFetcher(ArticleMapper articleMapper, IArticleRepository articleRepository) {
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
    }

    public void readArticlesFromRssFeeds() throws IOException, FeedException {
        for (String s : rssFeedsUrls.split(",")) {
            URL feedUrl = new URL(s);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new InputStreamReader(feedUrl.openStream()));

            List<Article> articleList = new ArrayList<>();
            if (feed.getEntries().get(0).getPublishedDate() == null) {
                feed.getEntries().sort(Comparator.comparing(SyndEntry::getUpdatedDate).reversed());
            } else {
                feed.getEntries().sort(Comparator.comparing(SyndEntry::getPublishedDate).reversed());
            }
            feed.getEntries().subList(0, 5).forEach(syndEntry -> {
                articleList.add(articleMapper.rssSyndEntryToEntity(syndEntry, feed.getTitle()));
            });
            articleRepository.saveAll(articleList);
        }
    }
}
