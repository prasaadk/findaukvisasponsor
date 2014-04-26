package org.findasponsor.indexer;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.Collection;

/**
 * Created on 4/25/14.
 * @author akshay
 */
public abstract class SolrIndexer<T> implements IndexerInterface<T> {
    SolrServer solr;

    public SolrIndexer(String url){
        this.solr = new HttpSolrServer(url);
    }

    public void bulkIndex(Collection<T> docs) {
        try {
            for(T doc: docs){
                index(doc);
            }
            solr.commit();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (SolrServerException sse) {
            throw new RuntimeException(sse);
        }

    }

    public void index(T doc) {
        try {
            solr.add(toSolrDoc(doc));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (SolrServerException sse) {
            throw new RuntimeException(sse);
        }

    }

    public void close() {
        try {
            solr.commit();
            solr.shutdown();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (SolrServerException sse) {
            throw new RuntimeException(sse);
        }
    }

    public abstract SolrInputDocument toSolrDoc(T doc);
}
