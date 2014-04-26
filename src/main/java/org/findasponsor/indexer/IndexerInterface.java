package org.findasponsor.indexer;

import java.util.Collection;

/**
 * Created on 4/25/14.
 *
 * Simple interface to define a few helper functions an indexer should implement.
 *
 * @author akshay
 */
public interface IndexerInterface<T> {

    /**
     * Truncate the index
     */
    public void deleteAllDocs();

    /**
     * Index the given document
     * @param doc Document
     */
    public void index(T doc);

    /**
     * Do a bulk indexing operation.
     * @param docs Collection of documents
     */
    public void bulkIndex(Collection<T> docs);

    /**
     * Close the connection to the server
     */
    public void close();

}
