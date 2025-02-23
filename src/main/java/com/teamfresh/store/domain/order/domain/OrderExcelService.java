package com.teamfresh.store.domain.order.domain;

import com.teamfresh.store.domain.order.domain.aggregate.dto.OrderProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderExcelService {

    public List<OrderProductInfo> excelParser(MultipartFile file) {
        List<OrderProductInfo> dataList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                OrderProductInfo data = parseRow(row);
                dataList.add(data);
            }

        } catch (IOException e) {
            log.error("Excel 파일 읽기 실패", e);
            throw new RuntimeException("Excel 파일을 읽을 수 없습니다.");
        }

        return dataList;
    }

    private OrderProductInfo parseRow(Row row) {
        try {
            Long productId = (long)row.getCell(0).getNumericCellValue();
            String productName = row.getCell(1).getStringCellValue();
            Long quantity = (long)row.getCell(2).getNumericCellValue();
            Long price = (long)row.getCell(3).getNumericCellValue();
            return new OrderProductInfo(productId, productName, quantity, price);
        } catch (Exception e) {
            log.warn("Excel 데이터 파싱 실패: {}", e.getMessage());
            throw e;
        }
    }
}
