package ru.isands.test.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.PurchaseTypeDto;
import ru.isands.test.estore.model.dto.input.PurchaseTypeInputDto;
import ru.isands.test.estore.service.PurchaseTypeService;

import java.util.Set;

@RestController
@RequestMapping("/estore/api/purchase_type")
public class PurchaseTypeController {

    private final PurchaseTypeService purchaseTypeService;

    @Autowired
    public PurchaseTypeController(PurchaseTypeService purchaseTypeService) {
        this.purchaseTypeService = purchaseTypeService;
    }

    @GetMapping
    public ResponseEntity<Set<PurchaseTypeDto>> getPurchaseTypes() {
        return ResponseEntity.ok(purchaseTypeService.getPurchaseTypes());
    }

    @GetMapping("/{purchaseTypeId}")
    public ResponseEntity<PurchaseTypeDto> getPurchaseTypeById(@PathVariable Long purchaseTypeId) {
        return ResponseEntity.ok(purchaseTypeService.getPurchaseTypeById(purchaseTypeId));
    }

    @PostMapping
    public ResponseEntity<PurchaseTypeDto> addPurchaseType(@RequestBody PurchaseTypeInputDto purchaseTypeInputDto) {
        return ResponseEntity.ok(purchaseTypeService.addPurchaseType(purchaseTypeInputDto));
    }

    @PatchMapping("/{purchaseTypeId}")
    public ResponseEntity<PurchaseTypeDto> updatePurchaseType(@PathVariable Long purchaseTypeId, @RequestBody PurchaseTypeInputDto purchaseTypeInputDto) {
        return ResponseEntity.ok(purchaseTypeService.updatePurchaseType(purchaseTypeId, purchaseTypeInputDto));
    }

}
