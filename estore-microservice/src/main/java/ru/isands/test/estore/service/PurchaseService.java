package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.NotFoundException;
import ru.isands.test.estore.exception.OutOfStockException;
import ru.isands.test.estore.mapper.PurchaseMapper;
import ru.isands.test.estore.model.*;
import ru.isands.test.estore.model.dto.PurchaseDto;
import ru.isands.test.estore.model.dto.input.PurchaseInputDto;
import ru.isands.test.estore.repository.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ElectroItemRepository electroItemRepository;
    private final EmployeeRepository employeeRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final ShopRepository shopRepository;
    private final ElectroShopRepository electroShopRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ElectroItemRepository electroItemRepository,
                           EmployeeRepository employeeRepository, PurchaseTypeRepository purchaseTypeRepository,
                           ShopRepository shopRepositoryl, ElectroShopRepository electroShopRepository) {
        this.purchaseRepository = purchaseRepository;
        this.electroItemRepository = electroItemRepository;
        this.employeeRepository = employeeRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.shopRepository = shopRepositoryl;
        this.electroShopRepository = electroShopRepository;
    }

    public Set<PurchaseDto> getPurchases() {
        return purchaseRepository.findAll().stream()
                .map(PurchaseMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Set<PurchaseDto> getPurchasesSortedByDate(String sort) {
        return purchaseRepository.findAll(Sort.by(sort.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "purchaseDate")).stream()
                .map(PurchaseMapper::toDto)
                .collect(Collectors.toSet());
    }

    public PurchaseDto getPurchaseById(Long purchaseId) {
        return PurchaseMapper.toDto(purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("Покупка не найдена")));
    }

    @Transactional
    public PurchaseDto addNewPurchase(PurchaseInputDto purchaseInputDto) {

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(new Date());

        ElectroItem electroItem = electroItemRepository.findById(purchaseInputDto.getElectroId())
                .orElseThrow(() -> new NotFoundException("Электротовар не найден"));
        Employee employee = employeeRepository.findById(purchaseInputDto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Сотрудник не найден"));
        PurchaseType purchaseType = purchaseTypeRepository.findById(purchaseInputDto.getTypeId())
                .orElseThrow(() -> new NotFoundException("Тип оплаты не найден"));
        Shop shop = shopRepository.findById(purchaseInputDto.getShopId())
                .orElseThrow(() -> new NotFoundException("Магазин не найден"));

        ElectroShop electroShop = electroShopRepository.findByShopAndElectroItem(shop, electroItem)
                .orElseThrow(() -> new NotFoundException("Товара не числится в магазине"));

        if (!(electroShop.getCount() > 0)) {
            throw new OutOfStockException("Товар закончился");
        }

        electroItem.setCount(electroItem.getCount() - 1);
        electroShop.setCount(electroShop.getCount() - 1);

        purchase.setElecroItem(electroItem);
        purchase.setEmployee(employee);
        purchase.setPurchaseType(purchaseType);
        purchase.setShop(shop);

        electroItemRepository.save(electroItem);
        electroShopRepository.save(electroShop);

        return PurchaseMapper.toDto(purchaseRepository.save(purchase));
    }

    void insertPurchase(Long purchaseId, Long electroId, Long employeeId, Date purchaseDate, Long typeId,Long shopId) {
        purchaseRepository.insertPurchase(purchaseId, electroId, employeeId, purchaseDate, typeId, shopId);
    };

}
