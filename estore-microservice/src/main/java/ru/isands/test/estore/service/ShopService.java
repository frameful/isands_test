package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.NotFoundException;
import ru.isands.test.estore.mapper.EmployeeMapper;
import ru.isands.test.estore.mapper.ShopMapper;
import ru.isands.test.estore.model.ElectroShopPK;
import ru.isands.test.estore.model.Employee;
import ru.isands.test.estore.model.Shop;
import ru.isands.test.estore.model.dto.EmployeeDto;
import ru.isands.test.estore.model.dto.EmployeeOutputDto;
import ru.isands.test.estore.model.dto.ShopDto;
import ru.isands.test.estore.model.dto.input.ShopInputDto;
import ru.isands.test.estore.repository.ElectroShopRepository;
import ru.isands.test.estore.repository.EmployeeRepository;
import ru.isands.test.estore.repository.ShopRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {
    
    private final ShopRepository shopRepository;
    private final ElectroShopRepository electroShopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository, ElectroShopRepository electroShopRepository) {
        this.shopRepository = shopRepository;
        this.electroShopRepository = electroShopRepository;
    }


    public List<ShopDto> getShops(Integer page, Integer pageSize) {
        return shopRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id").ascending())).stream()
                .map(ShopMapper::toDto)
                .collect(Collectors.toList());
    }

    public ShopDto getShopById(Long shopId) {
        return ShopMapper.toDto(shopRepository.findById(shopId).orElseThrow(() -> new NotFoundException("")));
    }

    public ShopDto addShop(ShopInputDto shopInputDto) {
        Shop shop = ShopMapper.toModel(shopInputDto);
        return ShopMapper.toDto(shopRepository.save(shop));
    }

    public ShopDto updateShop(Long shopId, ShopInputDto shopInputDto) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new NotFoundException("Магазин не найден"));
        return ShopMapper.toDto(shopRepository.save(ShopMapper.updateModel(shop, shopInputDto)));
    }

    public void insertElectroShop(ElectroShopPK electroShopPK, Long count) {
        electroShopRepository.insertElectroShop(electroShopPK.getShopId(), electroShopPK.getElectroItemId(), count);
    }

    void insertShop(Long shopId, String name, String address) {
        shopRepository.insertShop(shopId, name, address);
    };
}
