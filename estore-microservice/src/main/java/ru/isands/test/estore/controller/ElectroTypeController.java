package ru.isands.test.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.ElectroTypeDto;
import ru.isands.test.estore.model.dto.input.ElectroTypeInputDto;
import ru.isands.test.estore.service.ElectroTypeService;

import java.util.List;

@RestController
@RequestMapping("/estore/api/electro_type")
public class ElectroTypeController {

    private final ElectroTypeService electroTypeService;

    @Autowired
    public ElectroTypeController(ElectroTypeService electroTypeService) {
        this.electroTypeService = electroTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ElectroTypeDto>> getElectroTypes(@RequestParam(name = "page") Integer page,
                                                                @RequestParam(name = "pageSize") Integer pageSize)
    {
        return ResponseEntity.ok(electroTypeService.getElectroTypes(page, pageSize));
    }

    @GetMapping("/{electroTypeId}")
    public ResponseEntity<ElectroTypeDto> getElectroTypeById(@PathVariable Long electroTypeId) {
        return ResponseEntity.ok(electroTypeService.getElectroTypeById(electroTypeId));
    }

    @PostMapping
    public ResponseEntity<ElectroTypeDto> addElectroType(@RequestBody ElectroTypeInputDto electroTypeInputDto) {
        return ResponseEntity.ok(electroTypeService.addElectroType(electroTypeInputDto));
    }

    @PatchMapping("/{electroTypeId}")
    public ResponseEntity<ElectroTypeDto> updateElectroType(@PathVariable Long electroTypeId,
                                                            @RequestBody ElectroTypeInputDto electroTypeInputDto) {
        return ResponseEntity.ok(electroTypeService.updateElectroType(electroTypeId, electroTypeInputDto));
    }

}
