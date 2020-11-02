package com.example.crud_influx.crud_influx.controller;

import com.example.crud_influx.crud_influx.model.Stock;
import com.example.crud_influx.crud_influx.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/addStock")
    public String saveStock(@RequestBody Stock stock)
    {
        stockService.save(stock);
        return "Added Stock and Price with Stock ID: "+stock.getStockId()+" Successfully!";
    }

    @GetMapping("/allStocks")
    public Iterable<Stock> getStocks()
    {
        return stockService.findAll();
    }

    @GetMapping("/allStocks/{stockID}")
    public Stock getStockByID(@PathVariable int stockID)
    {
        return stockService.findById(stockID);
    }

    @DeleteMapping("/deleteStock/{stockID}")
    public String deleteStockByID(@PathVariable int stockID)
    {
        stockService.deleteById(stockID);
        return "Successfully Deleted Stock with ID: "+stockID;
    }


}
