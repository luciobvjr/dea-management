package br.com.dea.management.employee.service;

import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.dto.CreateEmployeeRequestDto;
import br.com.dea.management.employee.dto.UpdateEmployeeRequestDto;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.position.domain.Position;
import br.com.dea.management.position.repository.PositionRepository;
import br.com.dea.management.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    public Page<Employee> findAllEmployeePaginated(Integer page, Integer pageSize) {
        return this.employeeRepository.findAllPaginated(PageRequest.of(page, pageSize, Sort.by("user.name").ascending()));
    }

    public Employee findEmployeeById(Long id) {
        return this.employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(Employee.class, id));
    }

    public Employee createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
        Position position = this.positionRepository.findById(createEmployeeRequestDto.getPosition())
                .orElseThrow(() -> new NotFoundException(Position.class, createEmployeeRequestDto.getPosition()));

        User user = User.builder()
                .name(createEmployeeRequestDto.getName())
                .email(createEmployeeRequestDto.getEmail())
                .linkedin(createEmployeeRequestDto.getLinkedin())
                .password(createEmployeeRequestDto.getPassword())
                .build();

        Employee employee = Employee.builder()
                .employeeType(createEmployeeRequestDto.getEmployeeType())
                .position(position)
                .user(user)
                .build();

        return this.employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long employeeId, UpdateEmployeeRequestDto updateEmployeeRequestDto) {
        Position position = this.positionRepository.findById(updateEmployeeRequestDto.getPosition())
                .orElseThrow(() -> new NotFoundException(Position.class, updateEmployeeRequestDto.getPosition()));

        Employee employee = this.findEmployeeById(employeeId);

        User user = employee.getUser();
        user.setName(updateEmployeeRequestDto.getName());
        user.setEmail(updateEmployeeRequestDto.getEmail());
        user.setLinkedin(updateEmployeeRequestDto.getLinkedin());
        user.setPassword(updateEmployeeRequestDto.getPassword());

        employee.setEmployeeType(updateEmployeeRequestDto.getEmployeeType());
        employee.setUser(user);
        employee.setPosition(position);
        return this.employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = this.findEmployeeById(id);
        this.employeeRepository.delete(employee);
    }
}
