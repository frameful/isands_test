package ru.isands.test.estore.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.isands.test.estore.exception.IncorrectFormatException;
import ru.isands.test.estore.repository.*;

import javax.transaction.Transactional;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Log4j2
@Service
public class CsvService {

    private final ShopRepository shopRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final ElectroItemRepository electroItemRepository;
    private final ElectroShopRepository electroShopRepository;
    private final PositionTypeRepository positionTypeRepository;
    private final ElectroTypeRepository electroTypeRepository;
    private final ElectroEmployeeRepository electroEmployeeRepository;
    private final EmployeeRepository employeeRepository;


    private final Map<String, byte[]> fileMap = new HashMap<>();

    @Autowired
    public CsvService(
            ShopRepository shopRepository,
            PurchaseRepository purchaseRepository,
            PurchaseTypeRepository purchaseTypeRepository,
            ElectroItemRepository electroItemRepository,
            ElectroShopRepository electroShopRepository,
            PositionTypeRepository positionTypeRepository,
            ElectroTypeRepository electroTypeRepository,
            ElectroEmployeeRepository electroEmployeeRepository,
            EmployeeRepository employeeRepository)
    {
        this.shopRepository = shopRepository;
        this.purchaseRepository = purchaseRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.electroItemRepository = electroItemRepository;
        this.electroShopRepository = electroShopRepository;
        this.positionTypeRepository = positionTypeRepository;
        this.electroTypeRepository = electroTypeRepository;
        this.electroEmployeeRepository = electroEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }

    public void unzipDataArchive(MultipartFile file) {
        try (ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(file.getBytes()))) {
            for (ZipEntry entry; (entry = zipStream.getNextEntry()) != null;) {
                fileMap.put(entry.getName(), zipStream.readAllBytes());
                zipStream.closeEntry();
            }
        } catch (Exception e) {
            log.error("Error: ", e);
        }


        handleDataList(fileMap.get("PositionType.csv"), "PositionType");
        handleDataList(fileMap.get("ElectroType.csv"), "ElectroType");
        handleDataList(fileMap.get("PurchaseType.csv"), "PurchaseType");
        handleDataList(fileMap.get("Shop.csv"), "Shop");
        handleDataList(fileMap.get("Employee.csv"), "Employee");
        handleDataList(fileMap.get("ElectroItem.csv"), "ElectroItem");
        handleDataList(fileMap.get("Purchase.csv"), "Purchase");
        handleDataList(fileMap.get("ElectroEmployee.csv"), "ElectroEmployee");
        handleDataList(fileMap.get("ElectroShop.csv"), "ElectroShop");
    }

    private void handleDataList(byte[] bytes, String name) {
        InputStream stream = new ByteArrayInputStream(bytes);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, "CP1251"))) {
            var temp = br.lines().skip(1);
            switch(name) {
                case "ElectroItem": {
                    temp.forEach(this::readElectroItemFromCsvLine);
                    break;
                }
                case "ElectroEmployee": {
                    temp.forEach(this::readElectroEmployeeFromCsvLine);
                    break;
                }
                case "ElectroShop": {
                    temp.forEach(this::readElectroShopFromCsvLine);
                    break;
                }
                case "ElectroType": {
                    temp.forEach(this::readElectroTypeFromCsvLine);
                    break;
                }
                case "Employee": {
                    temp.forEach(this::readEmployeeFromCsvLine);
                    break;
                }
                case "Purchase": {
                    temp.forEach(this::readPurchaseFromCsvLine);
                    break;
                }
                case "Shop": {
                    temp.forEach(this::readShopFromCsvLine);
                    break;
                }
                case "PositionType": {
                    temp.forEach(this::readPositionTypeFromCsvLine);
                    break;
                }
                case "PurchaseType": {
                    temp.forEach(this::readPurchaseTypeFromCsvLine);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readShopFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 3) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        shopRepository.insertShop(Long.parseLong(columns[0]), columns[1], columns[2]);
        shopRepository.updateCurrentId("ru.isands.test.estore.entity.Shop");
    }

    private Date parseDate(String string, DateFormat dateFormat) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void readPurchaseFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 6) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        purchaseRepository.insertPurchase(
                Long.parseLong(columns[0]), Long.parseLong(columns[1]),
                Long.parseLong(columns[2]), parseDate(columns[3], new SimpleDateFormat("dd.MM.yyyy HH:mm")),
                Long.parseLong(columns[4]), Long.parseLong(columns[5])
        );
        purchaseTypeRepository.updateCurrentId("ru.isands.test.estore.entity.Purchase");
    }

    private void readElectroItemFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 7) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        electroItemRepository.insertElectroItem(
                Long.parseLong(columns[0]), columns[1],
                Long.parseLong(columns[2]), Long.parseLong(columns[3]),
                Long.parseLong(columns[4]), Long.parseLong(columns[5]) == 1,
                columns[6]
        );
        electroTypeRepository.updateCurrentId("ru.isands.test.estore.entity.ElectroItem");
    }

    private void readEmployeeFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 8) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        employeeRepository.insertEmployee(
                Long.parseLong(columns[0]), columns[1],
                columns[2], columns[3],
                parseDate(columns[4], new SimpleDateFormat("dd.MM.yyyy")), Long.parseLong(columns[5]),
                Long.parseLong(columns[6]), Long.parseLong(columns[7]) == 1
        );
        employeeRepository.updateCurrentId("ru.isands.test.estore.entity.Employee");
    }

    private void readPositionTypeFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 2) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        positionTypeRepository.insertPositionType(Long.parseLong(columns[0]),columns[1]);
        positionTypeRepository.updateCurrentId("ru.isands.test.estore.entity.PositionType");
    }

    private void readPurchaseTypeFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 2) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        purchaseTypeRepository.insertPurchaseType(Long.parseLong(columns[0]),columns[1]);
        purchaseTypeRepository.updateCurrentId("ru.isands.test.estore.entity.PurchaseType");
    }

    @Transactional
    private void readElectroTypeFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 2) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        electroTypeRepository.insertElectroType(Long.parseLong(columns[0]),columns[1]);
        electroTypeRepository.updateCurrentId("ru.isands.test.estore.entity.ElectroType");
    }

    private void readElectroShopFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 3) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        electroShopRepository.insertElectroShop(Long.parseLong(columns[0]), Long.parseLong(columns[1]), Long.parseLong(columns[2]));
    }

    private void readElectroEmployeeFromCsvLine(String line) {
        String[] columns = line.split(";");
        if (columns.length != 2) {
            throw new IncorrectFormatException("Неправильный формат csv файла");
        }

        electroEmployeeRepository.insertElectroEmployee(Long.parseLong(columns[0]), Long.parseLong(columns[1]));
    }
}