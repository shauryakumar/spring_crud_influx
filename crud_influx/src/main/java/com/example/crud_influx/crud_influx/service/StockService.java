package com.example.crud_influx.crud_influx.service;

import com.example.crud_influx.crud_influx.model.Stock;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class StockService
{

    InfluxDB connection = InfluxDBFactory.connect("http://localhost:8086");
    String dbName = "StockDB";

    private List<Stock> getPoints(String query)
    {
        Query queryObject = new Query(query, dbName);
        QueryResult queryResult = connection.query(queryObject);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        return resultMapper.toPOJO(queryResult, Stock.class);
    }

    public void save(Stock stock)
    {
        Point point = Point.measurement("stocks")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("stockId", stock.getStockId())
                .addField("stockName", stock.getStockName())
                .addField("stockSymbol", stock.getStockSymbol())
                .addField("stockPrice", stock.getStockPrice())
                .build();

        connection.write(dbName,"one_day_only", point);
    }

    public List<Stock> findAll()
    {
        List<Stock> stockPointList = getPoints("select * from one_day_only.stocks");
        return stockPointList;
    }

    public Stock findById(int stockID)
    {
        List<Stock> stockPointList = getPoints("select * from one_day_only.stocks where stockId ="+stockID);
        if(stockPointList.size() == 0)
            return null;
        else
            return stockPointList.get(0);
    }

    public void deleteById(int stockID)
    {
        String query="DELETE FROM stocks WHERE stockId="+stockID;
        Query queryObject = new Query(query, dbName);
        QueryResult queryResult = connection.query(queryObject);
    }

}
