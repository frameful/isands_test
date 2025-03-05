package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.BestShopDto;
import ru.isands.test.estore.model.dto.ShopDto;
import ru.isands.test.estore.model.dto.input.ShopInputDto;
import ru.isands.test.estore.service.ShopService;

import java.util.List;

@RestController
@Tag(name = "Shop", description = "Сервис для выполнения операций над магазинами")
@RequestMapping("/estore/api/shop")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @Operation(summary = "Получить список всех магазинов")
    @GetMapping
    public ResponseEntity<List<ShopDto>> getShops(@RequestParam(name = "page") Integer page,
                                                  @RequestParam(name = "pageSize") Integer pageSize)
    {
        return ResponseEntity.ok(shopService.getShops(page, pageSize));
    }

    @Operation(summary = "Получить магазин по Id")
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable Long shopId) {
        return ResponseEntity.ok(shopService.getShopById(shopId));
    }

    @Operation(summary = "Добавить магазин")
    @PostMapping
    public ResponseEntity<ShopDto> addShop(@RequestBody ShopInputDto shopInputDto) {
        return ResponseEntity.ok(shopService.addShop(shopInputDto));
    }

    @Operation(summary = "Обновить магазин")
    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDto> updateShop(@PathVariable Long shopId, @RequestBody ShopInputDto shopInputDto) {
        return ResponseEntity.ok(shopService.updateShop(shopId, shopInputDto));
    }

    @Operation(summary = "Получить сумму денежных средств, полученной магазином через оплату наличными")
    @GetMapping("/largestTotalSumByCash")
    public ResponseEntity<BestShopDto> getLargestTotalSumByCash() {
        return ResponseEntity.ok(shopService.getLargestTotalSumByCash());
    }

}
