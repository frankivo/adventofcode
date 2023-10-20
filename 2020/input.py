# Databricks notebook source
dbutils.widgets.text('session_cookie', '')

# COMMAND ----------

# MAGIC %sql
# MAGIC create schema if not exists frank.adventofcode

# COMMAND ----------

headers = { 'User-Agent': 'https://github.com/frankivo/adventofcode frank+github@scriptzone.nl' }
cookies = { 'session': dbutils.widgets.get('session_cookie') }

# COMMAND ----------

import requests
from pyspark.sql import Row
from pyspark.sql.functions import current_timestamp, lit

def downloadDay(day: int):
    url = 'https://adventofcode.com/2020/day/{0}/input'.format(day)
    with requests.get(url, headers=headers, cookies = cookies) as response:
        if (response.status_code != 200):
            raise Exception("Download failed: " + response.status_code)

        data = [Row(day, response.text)]
        spark.createDataFrame(data).toDF("day", "data") \
            .withColumn("loaded", current_timestamp()) \
            .withColumn("year", lit(2020)) \
            .write.mode("append").saveAsTable("frank.adventofcode.inputdata")

# COMMAND ----------

for day in range(1, 25+1):
    print("Download day: {d}".format(d=day))
    downloadDay(day)
