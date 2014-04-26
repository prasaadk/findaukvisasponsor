package org.findasponsor.indexer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.indices.IndexMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created on 4/26/14.
 *
 * Indexer for elasticsearch
 *
 * @author akshay
 */
public class ElasticSearchIndexer<T> implements IndexerInterface<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private Client client ;
    private ObjectMapper mapper = new ObjectMapper();
    private String indexName;
    private String type;

    public ElasticSearchIndexer(String host, int port, String clusterName, String indexName, String type) {
        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
        client            = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host,port));
        this.indexName    = indexName;
        this.type         = type;
    }

    public void deleteAllDocs(){
        try {
            client.prepareDeleteByQuery(indexName)
                    .setQuery(termQuery("_type", type))
                    .execute()
                    .actionGet();
        } catch (IndexMissingException ime) {
            log.error("Error truncating index", ime);
        }
    }

    public void index(T doc){
        try {
            client.prepareIndex(indexName, type)
                    .setSource(mapper.writeValueAsString(doc))
                    .execute()
                    .actionGet();
        }
        catch(JsonProcessingException jpe){
            log.error("Error indexing document", jpe);
        }
    }

    public void bulkIndex(Collection<T> docs){
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        try{
            for (T doc: docs) {
                bulkRequest.add(client.prepareIndex(indexName, type)
                        .setSource(mapper.writeValueAsString(doc)));
            }
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.hasFailures()) {
                for(BulkItemResponse brItem: bulkResponse.getItems()){
                    System.out.println(brItem.getFailureMessage());
                }
            }
            }
        catch(JsonProcessingException jpe){
            log.error("Error during bulk index operation",jpe);
        }
    }

    public void close(){
        client.close();
    }
}
