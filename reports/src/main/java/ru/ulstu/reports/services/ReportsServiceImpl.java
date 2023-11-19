package ru.ulstu.reports.services;

import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import ru.ulstu.reports.feign.FilestorageClient;
import ru.ulstu.reports.models.DTO.FileDTO;
import ru.ulstu.reports.models.DTO.SupplierNumeratedDTO;
import ru.ulstu.reports.models.mappers.SupplierMapper;
import ru.ulstu.reports.repositories.SupplierRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class ReportsServiceImpl implements ReportsService {
    private final SupplierRepository repo;
    private final SupplierMapper mapper;
    private final FilestorageClient filestorageClient;

    @Override
    public List<SupplierNumeratedDTO> getByActive(Boolean isActive) {
        List<SupplierNumeratedDTO> list = repo.findAllByIsActive(isActive).stream().map(mapper::mapToNumeratedDTO).toList();
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        String fileName = "Get-By-Active-" + isActive + "-" + LocalDateTime.now();
        filestorageClient.uploadFile(FileDTO.builder()
                .bytes(convertToByte(writeCsv(list), fileName))
                .filename(fileName)
                .build());
        return list;
    }

    @Override
    public List<SupplierNumeratedDTO> getAll() {
        List<SupplierNumeratedDTO> list = repo.findAll().stream().map(mapper::mapToNumeratedDTO).toList();
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        String fileName = "Get-All-" + LocalDateTime.now();
        filestorageClient.uploadFile(FileDTO.builder()
                .bytes(convertToByte(writeCsv(list), fileName))
                .filename(fileName)
                .build());
        return list;
    }

    @Override
    public JSONArray getJSONByActive(Boolean isActive) {
        List<SupplierNumeratedDTO> list = repo.findAllByIsActive(isActive).stream().map(mapper::mapToNumeratedDTO).toList();
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        return new JSONArray(list);
    }

    @Override
    public JSONArray getJSONAll() {
        List<SupplierNumeratedDTO> list = repo.findAll().stream().map(mapper::mapToNumeratedDTO).toList();
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(elem -> elem.setNum(i.getAndIncrement()));
        return new JSONArray(list);
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
