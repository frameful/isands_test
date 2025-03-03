package ru.isands.test.estore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.exception.NotFoundException;
import ru.isands.test.estore.mapper.EmployeeMapper;
import ru.isands.test.estore.model.ElectroEmployeePK;
import ru.isands.test.estore.model.Employee;
import ru.isands.test.estore.model.PositionType;
import ru.isands.test.estore.model.Shop;
import ru.isands.test.estore.model.dto.ElectroEmployeeDto;
import ru.isands.test.estore.model.dto.EmployeeDto;
import ru.isands.test.estore.model.dto.input.EmployeeInputDto;
import ru.isands.test.estore.repository.ElectroEmployeeRepository;
import ru.isands.test.estore.repository.EmployeeRepository;
import ru.isands.test.estore.repository.PositionTypeRepository;
import ru.isands.test.estore.repository.ShopRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ShopRepository shopRepository;
    private final PositionTypeRepository positionTypeRepository;
    private final ElectroEmployeeRepository electroEmployeeRepository;

    @Autowired
    public EmployeeService(
            EmployeeRepository employeeRepository,
            ShopRepository shopRepository,
            PositionTypeRepository positionTypeRepository,
            ElectroEmployeeRepository electroEmployeeRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.shopRepository = shopRepository;
        this.positionTypeRepository = positionTypeRepository;
        this.electroEmployeeRepository = electroEmployeeRepository;
    }

    public Set<EmployeeDto> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toSet());
    }

    public EmployeeDto getEmployeeById(Long employeeId) {
        return EmployeeMapper.toDto(employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("")));
    }

    public EmployeeDto addEmployee(EmployeeInputDto employeeInputDto) {
        Shop shop = shopRepository.findById(employeeInputDto.getShopId()).orElseThrow(() -> new NotFoundException("Магазин не найден"));

        Employee employee = EmployeeMapper.toModel(employeeInputDto);
        PositionType positionType = positionTypeRepository.findById(employeeInputDto.getPositionId())
                .orElseThrow(() -> new NotFoundException("Должность не найдена"));
        employee.setShop(shop);
        employee.setPositionType(positionType);
        return EmployeeMapper.toDto(employeeRepository.save(employee));
    }

    public EmployeeDto updateEmployee(Long employeeId, EmployeeInputDto employeeInputDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Сотрудник не найден"));
        if (employeeInputDto.getPositionId() != null) {
            PositionType positionType = positionTypeRepository.findById(employeeInputDto.getPositionId())
                    .orElseThrow(() -> new NotFoundException("Должность не найдена"));
            employee.setPositionType(positionType);
        }
        employee = employeeRepository.save(EmployeeMapper.updateModel(employee, employeeInputDto));
        return EmployeeMapper.toDto(employee);
    }

    @Transactional
    public Boolean deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(""));
        Shop shop = shopRepository.findById(employee.getShop().getId()).orElseThrow(() -> new NotFoundException(""));
        shop.getEmployees().remove(employee);
        employeeRepository.delete(employee);
        shopRepository.save(shop);
        return employeeRepository.existsById(employeeId);
    }

    public void insertElectroEmployee(Long employeeId, Long electroTypeId) {
        electroEmployeeRepository.insertElectroEmployee(employeeId, electroTypeId);
    }

    void insertEmployee(Long employeeId, String lastname, String firstname, String patronymic, Date birthDate,
                        Long positionId, Long shopId, Boolean gender) {
        employeeRepository.insertEmployee(employeeId, lastname, firstname, patronymic, birthDate, positionId,
                shopId, gender);
    }
}
