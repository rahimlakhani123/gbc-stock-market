package com.GBCE.service;

import java.util.Optional;
import java.util.Set;

public interface InstrumentService<T> {
    Optional<T> getInstrument(String symbol);

    void saveInstrument(T t);

    Set<String> getAllSymbols();
}
