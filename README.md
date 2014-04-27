findasponsor
============

Search Tier 2 & Tier 5 sponsors by company names, city, county or by tier requirements. We develop this search application for job seekers who are trying hard to find companies that can sponsor visas. This is also a quick way for recruiters to find if their clients offer Tier 2 and Tier 5 sponsorships.

### Run app

```
java -jar target/findasponsor-0.1-SNAPSHOT.jar index -help
usage: index [-h] URI host port

Index the list of sponsors

positional arguments:
  URI                    Resource URI to read
  host                   Search server host
  port                   Search server port

optional arguments:
  -h, --help             show this help message and exit
````

### Access search page

Access below html page query parameters as described.
```
findasponsor-recline-ui/index.html?backend=elasticsearch&url={elasticsearch-endpoint}

e.g. 
findasponsor-recline-ui/index.html?backend=elasticsearch&url=http://localhost:9200
```
