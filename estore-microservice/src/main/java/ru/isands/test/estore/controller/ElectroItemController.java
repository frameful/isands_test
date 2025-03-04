package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.ElectroItemByShopDto;
import ru.isands.test.estore.model.dto.ElectroItemDto;
import ru.isands.test.estore.model.dto.input.ElectroItemInputDto;
import ru.isands.test.estore.service.ElectroItemService;

import java.util.List;

@RestController
@Tag(name = "ElectroItem", description = "")
@RequestMapping("/estore/api/electro_item")
public class ElectroItemController {

    private final ElectroItemService electroItemService;

    @Autowired
    public ElectroItemController(ElectroItemService electroItemService) {
        this.electroItemService = electroItemService;
    }

    @GetMapping
    public ResponseEntity<List<ElectroItemDto>> getElectroItems(@RequestParam(name = "page") Integer page,
                                                                @RequestParam(name = "pageSize") Integer pageSize)
    {
        return ResponseEntity.ok(electroItemService.getElectroItems(page, pageSize));
    }

    @GetMapping("/{electroItemId}/groupByShop")
    public ResponseEntity<ElectroItemByShopDto> getElectroItemsGroupCountByShop(@PathVariable Long electroItemId) {
        return ResponseEntity.ok(electroItemService.getElectroItemByIdWithCountByShop(electroItemId));
    }

    @GetMapping("/{electroItemId}")
    public ResponseEntity<ElectroItemDto> getElectroItemById(@PathVariable Long electroItemId) {
        return ResponseEntity.ok(electroItemService.getElectroItemById(electroItemId));
    }

    @PostMapping("/addElectroItemToShop")
    public ResponseEntity<ElectroItemDto> addElectroItemToShop(@RequestParam(name = "shopId") Long shopId,
                                                               @RequestParam(name = "electroItemId") Long electoItemId,
                                                               @RequestBody Long newCount) {
        return ResponseEntity.ok(electroItemService.addElectroItemToShop(shopId, electoItemId, newCount));
    }

    @PostMapping
    public ResponseEntity<ElectroItemDto> addElectroItem(@RequestBody ElectroItemInputDto electroItemInputDto) {
        return ResponseEntity.ok(electroItemService.addElectroItem(electroItemInputDto));
    }

    @PatchMapping("/{electroItemId}")
    public ResponseEntity<ElectroItemDto> updateElectroItemInfo(@PathVariable Long electroItemId,
                                                                @RequestBody ElectroItemInputDto electroItemInputDto) {
        return ResponseEntity.ok(electroItemService.updateElectroItemInfo(electroItemId, electroItemInputDto));
    }

}
