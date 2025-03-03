package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.NotFoundException;
import ru.isands.test.estore.mapper.PurchaseTypeMapper;
import ru.isands.test.estore.model.PurchaseType;
import ru.isands.test.estore.model.dto.PurchaseTypeDto;
import ru.isands.test.estore.model.dto.input.PurchaseTypeInputDto;
import ru.isands.test.estore.repository.PurchaseTypeRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseTypeService {

    private final PurchaseTypeRepository purchaseTypeRepository;

    @Autowired
    public PurchaseTypeService(PurchaseTypeRepository purchaseTypeRepository) {
        this.purchaseTypeRepository = purchaseTypeRepository;
    }


    public Set<PurchaseTypeDto> getPurchaseTypes() {
        return purchaseTypeRepository.findAll().stream()
                .map(PurchaseTypeMapper::toDto)
                .collect(Collectors.toSet());
    }

    public PurchaseTypeDto getPurchaseTypeById(Long purchaseTypeId) {
        return PurchaseTypeMapper.toDto(purchaseTypeRepository.findById(purchaseTypeId).orElseThrow(() -> new NotFoundException("")));
    }

    public PurchaseTypeDto addPurchaseType(PurchaseTypeInputDto purchaseTypeInputDto) {
        PurchaseType purchaseType = PurchaseTypeMapper.toModel(purchaseTypeInputDto);
        return PurchaseTypeMapper.toDto(purchaseTypeRepository.save(purchaseType));
    }

    public PurchaseTypeDto updatePurchaseType(Long purchaseTypeId, PurchaseTypeInputDto purchaseTypeInputDto) {
        PurchaseType purchaseType = purchaseTypeRepository.findById(purchaseTypeId)
                .orElseThrow(() -> new NotFoundException(""));
        purchaseType = purchaseTypeRepository.save(PurchaseTypeMapper.updateModel(purchaseType, purchaseTypeInputDto));
        return PurchaseTypeMapper.toDto(purchaseType);
    }

    public void insertPurchaseType(Long purchaseTypeId, String name) {
        purchaseTypeRepository.insertPurchaseType(purchaseTypeId, name);
    }
}
