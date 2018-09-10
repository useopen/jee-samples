package org.jee.jpa.dynamic.tx.util;

import org.jee.jpa.dynamic.tx.Employee;
import org.jee.jpa.dynamic.tx.EmployeeService;

public class EmployeeBeanWrapper extends EmployeeService {

    private EmployeeService employeeBean;
    
    public EmployeeBeanWrapper() {
    }
    
    public EmployeeBeanWrapper(EmployeeService employeeBean) {
        this.employeeBean = employeeBean;
    }
    
    EmployeeService getWrapped() {
        return employeeBean;
    }
    
    @Override
    public void persist(Employee employee) {
        getWrapped().persist(employee);
    }
    
    @Override
    public Employee getById(int id) {
        return getWrapped().getById(id);
    }

}

