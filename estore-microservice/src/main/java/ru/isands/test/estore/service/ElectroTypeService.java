package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.NotFoundException;
import ru.isands.test.estore.mapper.ElectroItemMapper;
import ru.isands.test.estore.mapper.ElectroTypeMapper;
import ru.isands.test.estore.model.ElectroType;
import ru.isands.test.estore.model.dto.ElectroTypeDto;
import ru.isands.test.estore.model.dto.input.ElectroItemInputDto;
import ru.isands.test.estore.model.dto.input.ElectroTypeInputDto;
import ru.isands.test.estore.repository.ElectroTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectroTypeService {

    private final ElectroTypeRepository electroTypeRepository;

    @Autowired
    public ElectroTypeService(ElectroTypeRepository electroTypeRepository) {
        this.electroTypeRepository = electroTypeRepository;
    }

    public List<ElectroTypeDto> getElectroTypes(Integer page, Integer pageSize) {
        return electroTypeRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id").ascending()))
                .stream()
                .map(ElectroTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public ElectroTypeDto getElectroTypeById(Long electroTypeId) {
        return ElectroTypeMapper.toDto(electroTypeRepository.findById(electroTypeId)
                .orElseThrow(() -> new NotFoundException("")));
    }

    public ElectroTypeDto addElectroType(ElectroTypeInputDto electroTypeInputDto) {
        ElectroType electroType = ElectroTypeMapper.toModel(electroTypeInputDto);
        return ElectroTypeMapper.toDto(electroTypeRepository.save(electroType));
    }

    public ElectroTypeDto updateElectroType(Long electroTypeId, ElectroTypeInputDto electroTypeInputDto) {
        ElectroType electroType = electroTypeRepository.findById(electroTypeId)
                .orElseThrow(() -> new NotFoundException(("")));
        electroType = electroTypeRepository.save(ElectroTypeMapper.updateModel(electroType, electroTypeInputDto));
        return ElectroTypeMapper.toDto(electroType);
    }

    public void insertElectroType(Long electroTypeId, String name) {
        electroTypeRepository.insertElectroType(electroTypeId, name);
    }
}
