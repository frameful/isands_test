package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.AlreadyExistsException;
import ru.isands.test.estore.exception.NotFoundException;
import ru.isands.test.estore.mapper.ElectroItemMapper;
import ru.isands.test.estore.mapper.ShopMapper;
import ru.isands.test.estore.model.*;
import ru.isands.test.estore.model.dto.ElectroItemByShopDto;
import ru.isands.test.estore.model.dto.ElectroItemDto;
import ru.isands.test.estore.model.dto.ShopOutputDto;
import ru.isands.test.estore.model.dto.input.ElectroItemInputDto;
import ru.isands.test.estore.repository.ElectroItemRepository;
import ru.isands.test.estore.repository.ElectroShopRepository;
import ru.isands.test.estore.repository.ElectroTypeRepository;
import ru.isands.test.estore.repository.ShopRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElectroItemService {

    private final ElectroItemRepository electroItemRepository;
    private final ElectroTypeRepository electroTypeRepository;
    private final ElectroShopRepository electroShopRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public ElectroItemService(
            ElectroItemRepository electroItemRepository,
            ElectroTypeRepository electroTypeRepository,
            ElectroShopRepository electroShopRepository,
            ShopRepository shopRepository
    ) {
        this.electroItemRepository = electroItemRepository;
        this.electroTypeRepository = electroTypeRepository;
        this.electroShopRepository = electroShopRepository;
        this.shopRepository = shopRepository;
    }

    public Set<ElectroItemDto> getElectroItems() {
        return electroItemRepository.findAll().stream()
                .map(ElectroItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    public ElectroItemDto getElectroItemById(Long electroItemId) {
        return ElectroItemMapper.toDto(electroItemRepository.findById(electroItemId)
                .orElseThrow(() -> new NotFoundException("")));
    }

    public ElectroItemByShopDto getElectroItemByIdWithCountByShop(Long electroItemId) {
        ElectroItem electroItem = electroItemRepository.findById(electroItemId)
                .orElseThrow(() -> new NotFoundException(""));

        System.out.println(electroItem.getShops());

        Set<ElectroShop> electroShops = electroShopRepository.findByElectroItem(electroItem);

        Map<ShopOutputDto, Long> electroItemCountByShop = electroShops.stream()
                .collect(Collectors.toMap(
                        es -> ShopMapper.toOutputDto(es.getShop()),
                        ElectroShop::getCount
                ));

        return ElectroItemMapper.toByShopDto(electroItem, electroItemCountByShop);
    }

    @Transactional
    public ElectroItemDto addElectroItem(ElectroItemInputDto electroItemInputDto) {
        if (electroItemRepository.existsByName(electroItemInputDto.getName())) {
            throw new AlreadyExistsException("");
        }

        ElectroItem electroItem = ElectroItemMapper.toModel(electroItemInputDto);
        electroItem.setCount(electroItemInputDto.getCount());
        ElectroType electroType = electroTypeRepository.findById(electroItemInputDto.getElectroTypeId())
                .orElseThrow(() -> new NotFoundException(""));
        electroItem.setElectroType(electroType);

        return ElectroItemMapper.toDto(electroItemRepository.save(electroItem));
    }

    public ElectroItemDto updateElectroItemInfo(Long electroItemId, ElectroItemInputDto electroItemInputDto) {
        ElectroItem electroItem = electroItemRepository.findById(electroItemId)
                .orElseThrow(() -> new NotFoundException(""));
        if (electroItemInputDto.getElectroTypeId() != null) {
            ElectroType electroType = electroTypeRepository.findById(electroItemInputDto.getElectroTypeId())
                    .orElseThrow(() -> new NotFoundException(""));
            electroItem.setElectroType(electroType);
        }
        electroItem = electroItemRepository.save(ElectroItemMapper.updateModel(electroItem, electroItemInputDto));
        return ElectroItemMapper.toDto(electroItem);
    }

    @Transactional
    public ElectroItemDto addElectroItemToShop(Long shopId, Long electoItemId, Long amount) {
        ElectroItem electroItem = electroItemRepository.findById(electoItemId)
                .orElseThrow(() -> new NotFoundException(""));
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new NotFoundException(""));

        Optional<ElectroShop> existingElectroShop = electroShopRepository.findByShopAndElectroItem(shop, electroItem);

        electroItem.setCount(electroItem.getCount() + amount);
        electroItemRepository.save(electroItem);

        ElectroItemDto electroItemDto = ElectroItemMapper.toDto(electroItem);

        if (existingElectroShop.isPresent()) {
            ElectroShop electroShop = existingElectroShop.get();
            electroShop.setCount(electroShop.getCount() + amount);
            electroShopRepository.save(electroShop);
        } else {
            ElectroShopPK electroShopPK = new ElectroShopPK(shop.getId(), electoItemId);
            ElectroShop electroShop = new ElectroShop();
            electroShop.setId(electroShopPK);
            electroShop.setShop(shop);
            electroShop.setElectroItem(electroItem);
            electroShop.setCount(amount);
            electroShopRepository.save(electroShop);
        }

        return electroItemDto;
    }

    public void insertElectroItem(Long electroItemId,
                                  String name,
                                  Long electroTypeId,
                                  Long price,
                                  Long count,
                                  Boolean archive,
                                  String description) {
        electroItemRepository.insertElectroItem(electroItemId,
                name, electroTypeId, price, count, archive, description);
    }

}
