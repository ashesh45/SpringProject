package com.example.empsystem.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.empsystem.dto.EmployeeDTO;
import com.example.empsystem.dto.request.CreateEmployeeRequest;
import com.example.empsystem.model.Address;
import com.example.empsystem.model.Department;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private DepartmentRepository deptRepo;

    @Autowired
    private ModelMapper modelMapper;

    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images");

    private String saveFile(MultipartFile file, String name) throws IOException {
        if (file == null || file.isEmpty()) return null;
        Files.createDirectories(uploadDir);
        String sanitizedName = name.toLowerCase().replaceAll("[^a-z0-9]", "_");
        String fileName = sanitizedName + ".jpg";
        Path uploadPath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public EmployeeDTO createEmployee(CreateEmployeeRequest request, MultipartFile file) {
        String photo = null;
        try {
            photo = saveFile(file, request.getFname());
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
        request.setPhoto(photo);

        Employee entity = modelMapper.map(request, Employee.class);
        entity.setPassword(request.getPassword());

        if (request.getAddressName() != null || request.getAddressState() != null || request.getAddressZipcode() != null) {
            Address address = new Address();
            address.setName(request.getAddressName());
            address.setState(request.getAddressState());
            address.setZipcode(request.getAddressZipcode());
            entity.setAddress(address);
        }

        if (request.getDepartmentIds() != null && !request.getDepartmentIds().isEmpty()) {
            List<Department> departments = request.getDepartmentIds().stream()
                    .map(deptRepo::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            entity.setDepartments(departments);
        }

        entity = empRepo.save(entity);
        return modelMapper.map(entity, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee entity = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return modelMapper.map(entity, EmployeeDTO.class);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return empRepo.findAll(pageable)
                .map(entity -> modelMapper.map(entity, EmployeeDTO.class));
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto, MultipartFile file) {
        String photo = null;
        try {
            photo = saveFile(file, dto.getFname());
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
        if (photo != null) {
            dto.setPhoto(photo);
        }

        Employee entity = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        modelMapper.map(dto, entity);

        if (dto.getAddressName() != null || dto.getAddressState() != null || dto.getAddressZipcode() != null) {
            Address address = entity.getAddress();
            if (address == null) {
                address = new Address();
                entity.setAddress(address);
            }
            address.setName(dto.getAddressName());
            address.setState(dto.getAddressState());
            address.setZipcode(dto.getAddressZipcode());
        }

        if (dto.getDepartmentIds() != null) {
            List<Department> departments = dto.getDepartmentIds().stream()
                    .map(deptRepo::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            entity.setDepartments(departments);
        }

        entity = empRepo.save(entity);
        return modelMapper.map(entity, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployee(int id) {
        empRepo.deleteById((long) id);
    }
}
