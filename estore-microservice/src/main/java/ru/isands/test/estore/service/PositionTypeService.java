package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.NotFoundException;
import ru.isands.test.estore.mapper.PositionTypeMapper;
import ru.isands.test.estore.model.PositionType;
import ru.isands.test.estore.model.dto.PositionTypeDto;
import ru.isands.test.estore.model.dto.input.PositionTypeInputDto;
import ru.isands.test.estore.repository.PositionTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionTypeService {

    private final PositionTypeRepository positionTypeRepository;

    @Autowired
    public PositionTypeService(PositionTypeRepository positionTypeRepository) {
        this.positionTypeRepository = positionTypeRepository;
    }

    public List<PositionTypeDto> getPositionTypes(Integer page, Integer pageSize) {
        return positionTypeRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id").ascending())).stream()
                .map(PositionTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public PositionTypeDto getPositionTypeById(Long positionTypeId) {
        return PositionTypeMapper.toDto(positionTypeRepository.findById(positionTypeId).orElseThrow(() -> new NotFoundException("")));
    }

    public PositionTypeDto addPositionType(PositionTypeInputDto positionTypeInputDto) {
        PositionType positionType = PositionTypeMapper.toModel(positionTypeInputDto);
        return PositionTypeMapper.toDto(positionTypeRepository.save(positionType));
    }

    public PositionTypeDto updatePositionType(Long positionTypeId, PositionTypeInputDto positionTypeInputDto) {
        PositionType positionType = positionTypeRepository.findById(positionTypeId)
                .orElseThrow(() -> new NotFoundException(""));
        positionType = positionTypeRepository.save(PositionTypeMapper.updateModel(positionType, positionTypeInputDto));
        return PositionTypeMapper.toDto(positionType);
    }

    public void insertPositionType(Long positionTypeId, String name) {
        positionTypeRepository.insertPositionType(positionTypeId, name);
    }
}
