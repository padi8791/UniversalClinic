package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Department;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentPaginatedRepo extends PagingAndSortingRepository<Department, Long> {
}
