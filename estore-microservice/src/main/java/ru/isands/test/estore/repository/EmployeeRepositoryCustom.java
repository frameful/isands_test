package ru.isands.test.estore.repository;

import ru.isands.test.estore.model.dto.BestEmployeeDto;

public interface EmployeeRepositoryCustom {


    BestEmployeeDto findBestByTotalPriceOfItemsSold();

    BestEmployeeDto findBestByNumberOfItemsSold();

    BestEmployeeDto findBestJuniorConsultantByNumberOfSmartWatchesSold();

}
