package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import java.util.List;
import java.util.Optional;
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

    public List<PurchaseDto> getPurchases(String sort, Integer page, Integer pageSize) {
        return purchaseRepository.findAll(PageRequest.of(page, pageSize,
                        sort.equals("DESC") ? Sort.by("purchaseDate").descending()
                                : Sort.by("purchaseDate").ascending()))
                .stream()
                .map(PurchaseMapper::toDto)
                .collect(Collectors.toList());
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

        purchase.setElectroItem(electroItem);
        purchase.setEmployee(employee);
        purchase.setPurchaseType(purchaseType);
        purchase.setShop(shop);

        electroItemRepository.save(electroItem);
        electroShopRepository.save(electroShop);

        return PurchaseMapper.toDto(purchaseRepository.save(purchase));
    }

    @Transactional
    public PurchaseDto updatePurchase(Long purchaseId, PurchaseInputDto purchaseInputDto) {
        ElectroItem electroItem = electroItemRepository.findById(purchaseInputDto.getElectroId())
                .orElseThrow(() -> new NotFoundException("Электротовар не найден"));
        Employee employee = employeeRepository.findById(purchaseInputDto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Сотрудник не найден"));
        PurchaseType purchaseType = purchaseTypeRepository.findById(purchaseInputDto.getTypeId())
                .orElseThrow(() -> new NotFoundException("Тип оплаты не найден"));
        Shop shop = shopRepository.findById(purchaseInputDto.getShopId())
                .orElseThrow(() -> new NotFoundException("Магазин не найден"));

        ElectroShop _newElectroShop = electroShopRepository.findByShopAndElectroItem(shop, electroItem)
                .orElseThrow(() -> new NotFoundException("Товар не числится в магазине"));

        if (!(_newElectroShop.getCount() > 0)) {
            throw new OutOfStockException("Товар в данном магазине закончился");
        }

        Purchase oldPurchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new NotFoundException("Покупка не найдена"));
        ElectroItem oldElectroItem = electroItemRepository.findById(oldPurchase.getElectroItem().getId())
                .orElseThrow(() -> new NotFoundException("Предыдущий электротовар не найден"));
        Shop oldShop = shopRepository.findById(oldPurchase.getShop().getId())
                .orElseThrow(() -> new NotFoundException("Предыдущий магазин не найден"));
        Optional<ElectroShop> oldElectroShop = electroShopRepository.findByShopIdAndElectroItemId(oldShop.getId(), oldPurchase.getElectroItem().getId());

        if (oldElectroShop.isPresent()) {
            ElectroShop electroShop = oldElectroShop.get();
            electroShop.setCount(electroShop.getCount() + 1);
            electroShopRepository.save(electroShop);
        } else {
            ElectroShopPK electroShopPK = new ElectroShopPK(shop.getId(), oldElectroItem.getId());
            ElectroShop newElectroShop = new ElectroShop();
            newElectroShop.setId(electroShopPK);
            newElectroShop.setShop(shop);
            newElectroShop.setElectroItem(electroItem);
            newElectroShop.setCount(1L);
            electroShopRepository.save(newElectroShop);
        }

        _newElectroShop.setCount(_newElectroShop.getCount() - 1);
        electroItem.setCount(electroItem.getCount() - 1);
        oldElectroItem.setCount(electroItem.getCount() + 1);

        electroItemRepository.save(electroItem);
        electroItemRepository.save(oldElectroItem);

        electroShopRepository.save(_newElectroShop);

        oldPurchase.setPurchaseType(purchaseType);
        oldPurchase.setEmployee(employee);
        oldPurchase.setShop(shop);
        oldPurchase.setElectroItem(electroItem);

        return PurchaseMapper.toDto(purchaseRepository.save(oldPurchase));
    }

    void insertPurchase(Long purchaseId, Long electroId, Long employeeId, Date purchaseDate, Long typeId,Long shopId) {
        purchaseRepository.insertPurchase(purchaseId, electroId, employeeId, purchaseDate, typeId, shopId);
    };


}
