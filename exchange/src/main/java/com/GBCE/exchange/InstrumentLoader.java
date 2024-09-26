package com.GBCE.exchange;

import com.GBCE.domain.Stock;
import com.GBCE.service.InstrumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class InstrumentLoader implements Callable<Integer>, AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(InstrumentLoader.class);
    private final InstrumentService<Stock> instrumentService;

    public InstrumentLoader(InstrumentService<Stock> instrumentService) {
        this.instrumentService = instrumentService;
    }

    @Override
    public Integer call() throws Exception {
        var classLoader = Thread.currentThread().getContextClassLoader();
        Path path = Paths.get(Objects.requireNonNull(classLoader.getResource("instruments.csv")).toURI());
        List<String> payload = Files.readAllLines(path);
        payload.stream().map(this::createStock).forEach(instrumentService::saveInstrument);
        return payload.size();
    }

    private Stock createStock(String line) {
        String[] fields = line.split(",");
        return new Stock(fields[0],
                Stock.StockType.valueOf(fields[1]),
                Long.parseLong(fields[2]),
                Long.parseLong(fields[3]),
                Integer.parseInt(fields[4]));
    }

    @Override
    public void close() throws Exception {
        log.info("close instruments");// No Closing Required here but might be for real world
    }
}
