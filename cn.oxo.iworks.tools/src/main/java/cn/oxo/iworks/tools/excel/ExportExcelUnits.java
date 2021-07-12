package cn.oxo.iworks.tools.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.HttpHeaders;



//public class ExportExcelUnits {
//	private final static Logger logger = LogManager.getLogger(ExportExcelUnits.class);
//
//	public static <T> ExportFileBean export(String fileName, List<T> dataList, Class<T> cls) throws SystemOptServiceException {
//
//		Field[] fields = cls.getDeclaredFields();
//		List<Field> fieldList = Arrays.stream(fields).filter(field -> {
//			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
//			if (annotation != null) {
//				field.setAccessible(true);
//				return true;
//			}
//			return false;
//		}).sorted(Comparator.comparing(field -> {
//			int col = 0;
//			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
//			if (annotation != null) {
//				col = annotation.col();
//			}
//			return col;
//		})).collect(Collectors.toList());
//		Workbook wb = new XSSFWorkbook();
//		Sheet sheet = wb.createSheet("Sheet1");
//		AtomicInteger ai = new AtomicInteger();
//		{
//			Row row = sheet.createRow(ai.getAndIncrement());
//			AtomicInteger aj = new AtomicInteger();
//			// 写入头部
//			fieldList.forEach(field -> {
//				ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
//				String columnName = "";
//				if (annotation != null) {
//					columnName = annotation.value();
//				}
//				Cell cell = row.createCell(aj.getAndIncrement());
//
//				CellStyle cellStyle = wb.createCellStyle();
//				cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//				cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//
//				Font font = wb.createFont();
//				font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
//				cellStyle.setFont(font);
//				cell.setCellStyle(cellStyle);
//				cell.setCellValue(columnName);
//			});
//		}
//		if (CollectionUtils.isNotEmpty(dataList)) {
//			dataList.forEach(t -> {
//				Row row1 = sheet.createRow(ai.getAndIncrement());
//				AtomicInteger aj = new AtomicInteger();
//				fieldList.forEach(field -> {
//					Class<?> type = field.getType();
//					Object value = "";
//					try {
//						value = field.get(t);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					Cell cell = row1.createCell(aj.getAndIncrement());
//					if (value != null) {
//						if (type == Date.class) {
//							cell.setCellValue(value.toString());
//						} else {
//							cell.setCellValue(value.toString());
//						}
//						cell.setCellValue(value.toString());
//					}
//				});
//			});
//		}
//		// 冻结窗格
//		wb.getSheet("Sheet1").createFreezePane(0, 1, 0, 1);
//		// 浏览器下载excel
//
//		// 生成excel文件
//		try {
//
//			String id = UUID.randomUUID().toString();
//			buildExcelFile(id, wb);
//			ExportFileBean iExportFileBean = new ExportFileBean();
//			iExportFileBean.setName(fileName);
//			iExportFileBean.setUuid(id);
//
//			return iExportFileBean;
//		} catch (Exception e) {
//			throw new SystemOptServiceException(e);
//		}
//
//	}
//
//	/**
//	 * 生成excel文件
//	 * 
//	 * @param path 生成excel路径
//	 * @param wb
//	 */
//	private static String buildExcelFile(String id, Workbook wb) {
//		String tempDir = System.getProperty("java.io.tmpdir") + "/" + id + ".xlsx";
//		System.out.println(">>>>>>>>>>>>>>>  " + tempDir);
//		File file = new File(tempDir);
//		if (file.exists()) {
//			file.delete();
//		}
//		FileOutputStream iFileOutputStream = null;
//		try {
//			iFileOutputStream = new FileOutputStream(tempDir);
//			wb.write(iFileOutputStream);
//			wb.close();
//			iFileOutputStream.flush();
//
//			return tempDir;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return tempDir;
//		} finally {
//			if (iFileOutputStream != null) {
//				try {
//					iFileOutputStream.close();
//				} catch (IOException e) {
//					logger.error("ccc " + e.getMessage());
//				}
//			}
//		}
//
//	}
//
//	
//
//}
