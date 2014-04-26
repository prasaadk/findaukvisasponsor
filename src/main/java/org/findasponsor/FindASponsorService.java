package org.findasponsor;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.findasponsor.indexer.ElasticSearchIndexer;
import org.findasponsor.indexer.IndexerInterface;
import org.findasponsor.reader.SponsorRegisterReader;

import java.io.IOException;
import java.util.Collection;

/**
 * Created on 4/25/14
 * @author akshay.
 */
public class FindASponsorService {

    public static void main (String[] args){

        String indexName   = "findasponsor";
        String clusterName = "elasticsearch";
        String type        = "sponsor";

        ArgumentParser parser = ArgumentParsers.newArgumentParser("index")
                .defaultHelp(true)
                .description("Index the list of sponsors");
        parser.addArgument("URI")
                .help("Resource URI to read");
        parser.addArgument("host")
                .help("Search server host");
        parser.addArgument("port")
                .help("Search server port");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        String URI  = ns.getString("URI");
        String host = ns.getString("host");
        int    port = ns.getInt("port");

        SponsorRegisterReader reader    = new SponsorRegisterReader(URI);
        // JAVA API speaks on port 9093
        IndexerInterface<Sponsor> indexer = new ElasticSearchIndexer<Sponsor>(host, port, clusterName, indexName, type);
        try {
            // TODO: this would also mean a small downtime, think of better way
            indexer.deleteAllDocs();
            Collection<Sponsor> sponsors = reader.getSponsors();
            System.out.println("Fetched sponsors");
            indexer.bulkIndex(sponsors);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        indexer.close();
    }
}
