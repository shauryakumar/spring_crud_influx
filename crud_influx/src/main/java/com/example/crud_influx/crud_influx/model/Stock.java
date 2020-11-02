package com.example.crud_influx.crud_influx.model;

import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import java.time.Instant;

@Data
@Measurement(name = "stocks")
public class Stock
{

    @Column(name="time")
    private Instant time;

    @Column(name="stockId")
    private int stockId;

    @Column(name="stockName")
    private String stockName;

    @Column(name="stockSymbol")
    private String stockSymbol;

    @Column(name="stockPrice")
    private double stockPrice;

}
