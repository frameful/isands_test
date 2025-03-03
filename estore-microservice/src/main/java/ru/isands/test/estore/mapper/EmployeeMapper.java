package ru.isands.test.estore.mapper;

import ru.isands.test.estore.model.Employee;
import ru.isands.test.estore.model.dto.EmployeeDto;
import ru.isands.test.estore.model.dto.EmployeeOutputDto;
import ru.isands.test.estore.model.dto.input.EmployeeInputDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .shop(ShopMapper.toOutputDto(employee.getShop()))
                .birthDate(employee.getBirthDate())
                .positionType(employee.getPositionType())
                .firstName(employee.getFirstName())
                .gender(employee.isGender())
                .lastName(employee.getLastName())
                .patronymic(employee.getPatronymic())
                .electroTypes(employee.getElectroTypeSet().stream().map(ElectroTypeMapper::toDto).collect(Collectors.toSet()))
                .build();
    }

    public static EmployeeOutputDto toOutputDto(Employee employee) {
        return EmployeeOutputDto.builder()
                .id(employee.getId())
                .birthDate(employee.getBirthDate())
                .positionType(employee.getPositionType())
                .firstName(employee.getFirstName())
                .gender(employee.isGender())
                .lastName(employee.getLastName())
                .patronymic(employee.getPatronymic())
                .build();
    }

    private static Date parseDate(String string) {
           try {
               return new SimpleDateFormat("dd.MM.yyyy").parse(string);
           } catch (ParseException e) {
               e.printStackTrace();
               return null;
           }
    }

    public static Employee toModel(EmployeeInputDto employeeInputDto) {
        Employee employee = new Employee();
        employee.setBirthDate(parseDate(employeeInputDto.getBirthDate()));
        employee.setGender(employeeInputDto.getGender() == 1);
        employee.setPatronymic(employeeInputDto.getPatronymic());
        employee.setFirstName(employeeInputDto.getFirstName());
        employee.setLastName(employeeInputDto.getLastName());
        employee.setElectroTypeSet(new HashSet<>());
        return employee;
    }

    public static Employee updateModel(Employee employee, EmployeeInputDto employeeInputDto) {
        employee.setFirstName(employeeInputDto.getFirstName() == null ? employee.getFirstName() : employeeInputDto.getFirstName());
        employee.setLastName(employeeInputDto.getLastName() == null ? employee.getLastName() : employeeInputDto.getLastName());
        employee.setPatronymic(employeeInputDto.getPatronymic() == null ? employee.getPatronymic() : employeeInputDto.getPatronymic());
        employee.setBirthDate(employeeInputDto.getBirthDate() == null ? employee.getBirthDate() : parseDate(employeeInputDto.getBirthDate()));
        employee.setGender(employeeInputDto.getGender() == 1);
        return employee;
    }

}
