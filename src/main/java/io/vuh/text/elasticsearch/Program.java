package io.vuh.text.elasticsearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vuh.text.elasticsearch.implementation.ElasticSearchClientImpl;
import io.vuh.text.elasticsearch.implementation.ElasticSearchLoaderImpl;
import io.vuh.text.model.Article;
import rx.Observable;

public class Program {

	public static void main(String[] args) {
		Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("192.168.1.23", 9300));
		ElasticSearchClientImpl elasticSearchClient = new ElasticSearchClientImpl();
		elasticSearchClient.setClient(client);
		elasticSearchClient.setObjectMapper(new ObjectMapper());
		
		ElasticSearchLoaderImpl elasticSearchLoader = new ElasticSearchLoaderImpl();
		elasticSearchLoader.setClient(elasticSearchClient);
		
		List<Article> articles = generateList();
		
		elasticSearchLoader.loadArticles(articles);
		
	}
	
	public static List<Article> generateList(){
		List<Article> list = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			Article article = new Article();
			article.setId(UUID.randomUUID().toString());
			article.setDate(new Date());
			article.setSource("Fox News");
			article.setText("Former President Jimmy Carter said recently that he provided maps of Islamic State positions in Syria to the Russian embassy in Washington, a move apparently at odds with the Obama administration’s official policy of not cooperating with Russia in the Syrian war. \n Carter said on Sunday in Georgia that he knows Russian President Vladimir Putin “fairly well” because they “have a common interest in fly fishing.” When he met with Putin in April along with other global leaders to discuss the crises in Syria and Ukraine, the Russian president gave him an email address so the two could discuss his “fly fishing experiences, particularly in Russia,” Carter said. \n The civil war in Syria, where U.S. officials say Russia has bombed rebels and CIA-backed groups rather than the Islamic State terrorist group, has also been a topic of conversation between the two. Carter said he sent maps of the Islamic State’s locations in Syria, produced by the Carter Center, to the Russian embassy so Moscow could improve the accuracy of its strikes. \n “I sent [Putin] a message Thursday and asked him if he wanted a copy of our map so he could bomb accurately in Syria, and then on Friday, the Russian embassy in Atlanta—I mean in Washington, called down and told me they would like very much to have the map,” Carter said at his Sunday school class in Georgia, according to a video of his remarks first aired by NBC News. “So in the future, if Russia doesn’t bomb the right places, you’ll know it’s not Putin’s fault but it’s my fault,” he added as the audience laughed. \n Obama administration officials have publicly said the United States will not collaborate with Russia as long as it targets U.S.-backed rebels in an effort to prop up Syrian President Bashar al-Assad, a longtime ally of Moscow. The administration has said Assad must eventually step down as part of efforts to seek a political resolution to the Syrian war. “We are not prepared to cooperate on strategy which, as we explained, is flawed, tragically flawed, on the Russians’ part,” said Ash Carter, U.S. defense secretary, earlier this month.");
			article.setTitle("Jimmy Carter offers help for Russia’s bombing campaign in Syria");
			article.setUrl("http://www.foxnews.com/politics/2015/10/21/jimmy-carter-offers-help-for-russias-bombing-campaign-in-syria/");
			list.add(article);
		}
		
		return list;
	};

}
