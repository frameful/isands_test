package ru.isands.test.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.PositionTypeDto;
import ru.isands.test.estore.model.dto.input.PositionTypeInputDto;
import ru.isands.test.estore.service.PositionTypeService;

import java.util.Set;

@RestController
@RequestMapping("/estore/api/position_type")
public class PositionTypeController {

    private final PositionTypeService positionTypeService;

    @Autowired
    public PositionTypeController(PositionTypeService positionTypeService) {
        this.positionTypeService = positionTypeService;
    }

    @GetMapping
    public ResponseEntity<Set<PositionTypeDto>> getPositionTypes() {
        return ResponseEntity.ok(positionTypeService.getPositionTypes());
    }

    @GetMapping("/{positionTypeId}")
    public ResponseEntity<PositionTypeDto> getPositionTypeById(@PathVariable Long positionTypeId) {
        return ResponseEntity.ok(positionTypeService.getPositionTypeById(positionTypeId));
    }

    @PostMapping
    public ResponseEntity<PositionTypeDto> addPositionType(@RequestBody PositionTypeInputDto positionTypeInputDto) {
        return ResponseEntity.ok(positionTypeService.addPositionType(positionTypeInputDto));
    }

    @PatchMapping("/positionTypeId")
    public ResponseEntity<PositionTypeDto> updatePositionType(@PathVariable Long positionTypeId,
                                                              @RequestBody PositionTypeInputDto positionTypeInputDto) {
        return ResponseEntity.ok(positionTypeService.updatePositionType(positionTypeId, positionTypeInputDto));
    }

}
