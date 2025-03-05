package ru.isands.test.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.isands.test.estore.model.dto.BestEmployeeDto;
import ru.isands.test.estore.model.dto.EmployeeDto;
import ru.isands.test.estore.model.dto.input.EmployeeInputDto;
import ru.isands.test.estore.service.EmployeeService;

import java.util.List;

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
	public ResponseEntity<List<EmployeeDto>> getEmployees(@RequestParam(name = "page") Integer page,
														  @RequestParam(name = "pageSize") Integer pageSize)
	{
		return ResponseEntity.ok(employeeService.getEmployees(page, pageSize));
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

	@GetMapping("/bestByTotalPriceOfItemsSold")
	public ResponseEntity<BestEmployeeDto> getBestByTotalPriceOfItemsSold() {
		return ResponseEntity.ok(employeeService.getBestByTotalPriceOfItemsSold());
	}

	@GetMapping("/bestJuniorConsultantByNumberOfSmartWatchesSold")
	public ResponseEntity<BestEmployeeDto> getBestJuniorConsultantByNumberOfSmartWatchesSold() {
		return ResponseEntity.ok(employeeService.getBestJuniorConsultantByNumberOfSmartWatchesSold());
	}

	@GetMapping("/bestByNumberOfItemsSold")
	public ResponseEntity<BestEmployeeDto> getBestByNumberOfItemsSold() {
		return ResponseEntity.ok(employeeService.getBestByNumberOfItemsSold());
	}

}
