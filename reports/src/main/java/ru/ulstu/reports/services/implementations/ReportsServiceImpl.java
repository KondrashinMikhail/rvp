package ru.ulstu.reports.services.implementations;

import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.reports.models.mappers.SupplierMapper;
import ru.ulstu.reports.repositories.SupplierRepository;
import ru.ulstu.reports.services.interfaces.RabbitMQProducerService;
import ru.ulstu.reports.services.interfaces.ReportsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.ulstu.reports.configs.RabbitConfig.ROUTING_KEY;

@Service
@AllArgsConstructor
@Log4j2
public class ReportsServiceImpl implements ReportsService {
    private final SupplierRepository repo;
    private final SupplierMapper mapper;
//    private final FilestorageClient filestorageClient;
    private final RabbitMQProducerService rabbitMQService;

    @Override
    public List<SupplierNumeratedDTO> getByActive(Boolean isActive, String correlationId) {
        List<SupplierNumeratedDTO> list = repo.findAllByIsActive(isActive).stream().map(mapper::mapToNumeratedDTO).toList();
        log.info(isActive ? "Получены отчеты по всем активным заказчикам" : "Получены отчеты по всем заблокированным заказчикам");
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        String fileName = String.format("Get-By-Active-%s-%s.csv", isActive, LocalDateTime.now());
//        TODO: раскомментировать, если нужно пересылать данные через feign
//        filestorageClient.uploadFile(
//                FileDTO.builder()
//                        .bytes(convertToByte(writeCsv(list), fileName))
//                        .filename(fileName)
//                        .build());

        JSONObject json = writeToJSON(fileName, convertToByte(writeCsv(list), fileName));
        json.put("correlation-id", correlationId);
        rabbitMQService.sendFile(ROUTING_KEY, json);
        log.info(String.format("В обменник отправлен файл %s", fileName));
        return list;
    }

    @Override
    public List<SupplierNumeratedDTO> getAll(String correlationId) {
        List<SupplierNumeratedDTO> list = repo.findAll().stream().map(mapper::mapToNumeratedDTO).toList();
        log.info("Получены отчеты по всем заказчикам");
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        String fileName = String.format("Get-All-%s.csv", LocalDateTime.now());
//        TODO: раскомментировать, если нужно пересылать данные через feign
//        filestorageClient.uploadFile(
//                FileDTO.builder()
//                        .bytes(convertToByte(writeCsv(list), fileName))
//                        .filename(fileName)
//                        .build());

        JSONObject json = writeToJSON(fileName, convertToByte(writeCsv(list), fileName));
        json.put("correlation-id", correlationId);
        rabbitMQService.sendFile(ROUTING_KEY, json);
        log.info(String.format("В обменник отправлен файл %s", fileName));
        return list;
    }

    private JSONObject writeToJSON(String filename, byte[] data) {
        JSONObject json = new JSONObject();
        json.put("filename", filename);
        json.put("data", java.util.Base64.getEncoder().encodeToString(data));
        return json;
    }

    @SneakyThrows
    private File writeCsv(List<SupplierNumeratedDTO> data) {
        File file = new File("/file.csv");
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile);

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{
                "num",
                "id",
                "name",
                "ip",
                "ooo",
                "ogrn",
                "organization",
                "isActive"
        });

        data.forEach(item -> csvData.add(new String[]{
                item.getNum().toString(),
                item.getId().toString(),
                item.getName(),
                item.getIpName(),
                item.getOooName(),
                item.getOgrn(),
                item.getOrganizationName(),
                item.getIsActive().toString()
        }));

        writer.writeAll(csvData);
        writer.close();

        return file;
    }

    @SneakyThrows
    private byte[] convertToByte(File file, String fileName) {
        return new MockMultipartFile(fileName,
                file.getName(), "text/plain", IOUtils.toByteArray(new FileInputStream(file))).getBytes();
    }
}
