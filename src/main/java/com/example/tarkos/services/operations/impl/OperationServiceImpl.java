package com.example.tarkos.services.operations.impl;


import com.example.tarkos.repositories.operations.OperationRepository;
import com.example.tarkos.repositories.products.ProductRepository;
import com.example.tarkos.services.operations.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final ProductRepository productRepository;
    private final Map<String, Double> coefficients = new ConcurrentHashMap<>();

    public OperationServiceImpl(
            OperationRepository operationRepository,
            ProductRepository productRepository
    ) {
        this.operationRepository = operationRepository;
        this.productRepository = productRepository;
    }

    public void makeBeer(String operationType, String apparatusName, String modeName, Collection<Pair<String, Double>> pairs) {

        log.info("Making beer, operation - {}", operationType);
        pairs.forEach(
                pair -> productRepository.updateCountProductByName(pair.getFirst(), pair.getSecond() * (-1))
        );

        log.info("Searching produce product");
        String produceProduct = getProduceProduct(pairs);
        productRepository.updateCountProductByName(
                operationType.equals("WATER_PREPARATION") ? modeName : produceProduct,
                pairs.stream()
                        .map(Pair::getSecond)
                        .max(Double::compare)
                        .orElseThrow(() -> new NoSuchElementException("No such produce product")) * coefficients.get(operationType)

        );

        log.info("Record apparatus operation");
        operationRepository.insertApparatusOperationByName(apparatusName);

        log.info("Record mode operation");
        operationRepository.insertModInLastOperationByName(modeName);

        log.info("Record products apparatuses operation");
        pairs.forEach(
                pair -> operationRepository.insertApparatusProductsInLastOperation(pair.getFirst(), pair.getSecond())
        );
    }

    public void register(String operation, Double coefficient) {
        coefficients.put(operation, coefficient);
    }

    private String getProduceProduct(Collection<Pair<String, Double>> pairs) {
        List<String> list = new ArrayList<>();

        pairs.forEach(
                pair -> list.addAll(
                        productRepository.getProductNameByDerivativeProductName(pair.getFirst())
                )
        );

        Map<String, Integer> map = new HashMap<>();

        for (String name : list) {
            if (!map.containsKey(name)) {
                map.put(name, 1);
            } else {
                map.put(name, map.get(name) + 1);
            }
        }

        long max = map.values()
                .stream()
                .max(Integer::compare)
                .orElseThrow(() -> new NoSuchElementException("No such produce product"));

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                return entry.getKey();
            }
        }

        throw new NoSuchElementException("No such produce product");
    }
}
