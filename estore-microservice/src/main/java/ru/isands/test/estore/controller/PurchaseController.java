package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.PurchaseDto;
import ru.isands.test.estore.model.dto.input.PurchaseInputDto;
import ru.isands.test.estore.service.PurchaseService;

import java.util.Set;

@RestController
@Tag(name = "Purchase", description = "Сервис для выполнения операций над покупками")
@RequestMapping("/estore/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "Получить список всех покупок, отсортированных по дате")
    @GetMapping
    public ResponseEntity<Set<PurchaseDto>> getPurchases(@RequestParam(value = "sort", required = false) String sort) {
        if (sort != null && (sort.equals("ASC") || sort.equals("DESC"))) {
            return ResponseEntity.ok(purchaseService.getPurchasesSortedByDate(sort));
        } else {
            return ResponseEntity.ok(purchaseService.getPurchases());
        }
    }

    @Operation(summary = "Получить покупку по Id")
    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseDto> getPurchaseById(@PathVariable Long purchaseId) {
        return ResponseEntity.ok(purchaseService.getPurchaseById(purchaseId));
    }

    @Operation(summary = "Добавить покупку")
    @PostMapping
    public ResponseEntity<PurchaseDto> addPurchase(@RequestBody PurchaseInputDto purchaseInputDto) {
        return ResponseEntity.ok(purchaseService.addNewPurchase(purchaseInputDto));
    }


}
