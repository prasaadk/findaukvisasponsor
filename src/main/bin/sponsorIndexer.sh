#!/usr/bin/env bash
#
# Wrapper for placing Indexer on a cron
#

URL="https://www.gov.uk/government/uploads/system/uploads/attachment_data/file/305166/Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf"
HOST="localhost"
PORT=9300

java -jar findAsponsor.jar index $URL $HOST $PORT &>/var/log/findasponsor/indexer.log
