package ru.isands.test.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.isands.test.estore.model.ElectroEmployeePK;
import ru.isands.test.estore.model.dto.ElectroEmployeeDto;
import ru.isands.test.estore.model.dto.EmployeeDto;
import ru.isands.test.estore.model.dto.input.EmployeeInputDto;
import ru.isands.test.estore.service.EmployeeService;

import java.util.Set;

@RestController
@Tag(name = "Employee", description = "Сервис для выполнения операций над сотрудниками магазина")
@RequestMapping("/estore/api/employee")
public class EmployeeController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Operation(summary = "Получить список всех сотрудников")
	@GetMapping
	public ResponseEntity<Set<EmployeeDto>> getEmployees() {
		return ResponseEntity.ok(employeeService.getEmployees());
	}

	@Operation(summary = "Получить сотрудника по ID.")
	@GetMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

	@Operation(summary = "Добавить сотрудника")
	@PostMapping
	public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeInputDto employeeInputDto) {
		return ResponseEntity.ok(employeeService.addEmployee(employeeInputDto));
	}

	@Operation(summary = "Обновить сотрудника")
	@PatchMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long employeeId,
													  @RequestBody EmployeeInputDto employeeInputDto) {
		return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeInputDto));
	}

	@DeleteMapping("/{employeeId}")
	public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
	}

}
