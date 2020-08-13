package org.linwl.commonutils.tools;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import org.apache.poi.util.IOUtils;
import org.linwl.commonutils.models.BaseExcelModel;
import org.linwl.commonutils.models.Constants;
import org.linwl.commonutils.response.ERRORCODE;
import org.linwl.commonutils.response.Result;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @program: JavaCommonTools
 * @description: 通用工具类
 * @author: linwl
 * @create: 2020-08-13 14:28
 **/
public class CommonTools {
    private CommonTools() {}

    /**
     * 导出excel
     * @param response
     * @param fileName
     * @param sheetName
     * @param clazz
     * @param data
     * @param headWriteCellStyle 自定义头样式
     * @param contentWriteCellStyle 自定义内容样式
     * @throws IOException
     */
    public static void exportExcel(
            HttpServletResponse response,
            String fileName,
            String sheetName,
            Class<? extends BaseExcelModel> clazz,
            List data, WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle)
            throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8") + System.currentTimeMillis();
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
            EasyExcel.write(response.getOutputStream(), clazz).registerWriteHandler(horizontalCellStyleStrategy).sheet(sheetName).doWrite(data);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Result<Boolean> result =
                    new Result<>(ERRORCODE.Error, MessageFormat.format("导出excel文件发生异常:{0}", e.getMessage()));
            response.getWriter().println(JSON.toJSONString(result));
        }
    }

    /**
     * 导出excel
     * @param response
     * @param fileName
     * @param sheetName
     * @param clazz
     * @param data
     * @throws IOException
     */
    public static void exportExcel(
            HttpServletResponse response,
            String fileName,
            String sheetName, Class<? extends BaseExcelModel> clazz, List data)
            throws IOException {
        exportExcel(response, fileName, sheetName, clazz, data,null,null);
    }

    /**
     * 导出excel
     * @param response 响应体
     * @param fileName 文件名
     * @param sheetName 列明
     * @param clazz
     * @param data 数据列表
     * @param contentWriteCellStyle 自定义内容样式
     * @throws IOException
     */
    public static void exportExcel(
            HttpServletResponse response,
            String fileName,
            String sheetName,Class<? extends BaseExcelModel> clazz,List data,
            WriteCellStyle contentWriteCellStyle)
            throws IOException {
        exportExcel(response, fileName, sheetName, clazz, data,null,contentWriteCellStyle);
    }


    /**
     * 导出模板
     * @param response
     * @param fileName
     * @param clazz
     * @throws IOException
     */
    public static void exportTemplateExcel(
            HttpServletResponse response, String fileName, Class<? extends BaseExcelModel> clazz)
            throws IOException {
        exportExcel(response, fileName, "模板", clazz, null,null,null);
    }

    /**
     * 下载模板文件
     *
     * @param response
     * @param fileName
     * @param filePath
     * @throws IOException
     */
    public static void downTemplateExcel(HttpServletResponse response, String fileName, String filePath) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8") + System.currentTimeMillis();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        //获取资源文件输入流和httpServletResponse的输出流
        try (InputStream inputStream = classPathResource.getInputStream();
             ServletOutputStream servletOutputStream =
                     response.getOutputStream()) {
            //把资源文件的二进制流数据copy到response的输出流中
            IOUtils.copy(inputStream, servletOutputStream);
            //清除flush所有的缓冲区中已设置的响应信息至客户端
            response.flushBuffer();
        }
    }

    /**
     * 生成员工编号
     *
     * @return
     */
    public static String createEmployeeNo() {
        StringBuffer sb = new StringBuffer();
        sb.append("E");
        sb.append(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
        sb.append(RandomUtil.randomNumbers(4));
        return sb.toString();
    }

    /**
     * 正则匹配是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String regEx = "^-?[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 校验当前时间是否在时间段
     * @param startTime 比较起始时间
     * @param endTime 比较结束时间
     * @return
     */
    public static boolean checkInTime(LocalTime startTime, LocalTime endTime) {
        LocalTime now = LocalTime.now();
        return checkInTime(startTime,endTime,now);
    }

    /**
     * 校验是否在时间段
     * @param startTime 比较起始时间
     * @param endTime 比较结束时间
     * @param sourceTime 比较时间
     * @return
     */
    public static boolean checkInTime(LocalTime startTime, LocalTime endTime,LocalTime sourceTime) {
        if (sourceTime.equals(startTime) || sourceTime.equals(endTime)) {
            return true;
        }
        return sourceTime.isAfter(startTime) && sourceTime.isBefore(endTime);
    }

    /**
     * 按key排序
     * @return
     */
    public static Map<String,Object> sortMap(Map<String,Object> sourceMap){
        return
                sourceMap
                        .entrySet()
                        .parallelStream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (oldKey, oldValue) -> oldValue,
                                        LinkedHashMap::new));
    }

    /**
     * 字典转连接字符串(key=value)
     * @param data
     * @return
     */
    public static String maptolinkStr(Map<String,Object> data)
    {
        return  Joiner.on("&").withKeyValueSeparator("=")
                .join(data);
    }

    /**
     * 字典转连接字符串(key=value)
     * @param data
     * @param connector 拼接符
     * @return
     */
    public static String maptolinkStr(Map<String,Object> data,String connector)
    {
        return  Joiner.on(connector).withKeyValueSeparator("=")
                .join(data);
    }


    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getNowTime() {
        return getNowTime(Constants.DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 获取当前时间串
     *
     * @param timeFormat
     * @return
     */
    public static String getNowTime(String timeFormat) {
        try {
            return DateTimeFormatter.ofPattern(timeFormat).format(LocalDateTime.now());
        } catch (Exception e) {
            return "";
        }
    }
}
