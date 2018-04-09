/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyks.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.*;
import java.util.ArrayList;
import jxl.*;
import jxl.write.*;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import quanlyks.model.HotelVoucher;
import quanlyks.model.Room;
import quanlyks.model.Service;
import quanlyks.views.MainScreen;

/**
 *
 * @author Tuyen Ti Ton
 */
public class ExportFile {

    private SettingController settingController = new SettingController();
    private IconManager icon = new IconManager();
    private static final Font arial = FontFactory.getFont("font\\vuArial.ttf",
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);
    private static final Font timenewroman = FontFactory.getFont("font\\vuTimes.ttf",
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);
    private static final Font vnArial10 = new Font(arial.getBaseFont(), 11f, Font.BOLD);
    private static final Font vnArial5n = new Font(arial.getBaseFont(), 5f, Font.NORMAL);
    private static final Font vnArial5b = new Font(arial.getBaseFont(), 5f, Font.BOLD);
    private static final Font vnTimes8 = new Font(timenewroman.getBaseFont(), 8f, Font.ITALIC);
    private StaffController staffController = new StaffController();
    private HotelVoucherController voucherController = new HotelVoucherController();

    public ExportFile() {
    }

    public void printTable(JTable table, File file) throws WriteException {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            TableModel model = table.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                Label col = new Label(i, 0, model.getColumnName(i) + "\t");
                sheet.addCell(col);
            }
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Label row = new Label(j, i + 1, model.getValueAt(i, j).toString() + "\t");
                    sheet.addCell(row);
                }
            }
            workbook.write();
            workbook.close();
        } catch (IOException e) {
        }
    }

    public void printBill(HotelVoucher voucher, File file) {
        float left = 30;
        float right = 30;
        float top = 10;
        float bottom = 5;
        Document document = new Document(PageSize.A6, left, right, top, bottom);
        Paragraph paragraph = new Paragraph();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            // tạo cá thông tin về hóa đơn
            paragraph = new Paragraph(settingController.getProperties().get(0), vnArial10);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph(settingController.getProperties().get(1), vnTimes8);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph("******************", vnArial5n);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph("Địa chỉ: " + settingController.getProperties().get(2), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Số điện thoại: " + settingController.getProperties().get(3), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Nhân viên: " + staffController.getStaffByIdStaff(voucher.getIdStaff()).getNameStaff(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Số giao dịch: " + voucher.getIdVoucher() + "." + voucher.getIdCustomer() + "." + MainScreen.getCurrentDateTime(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Ngày nhận phòng: " + voucher.getArrival(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Ngày trả phòng: " + voucher.getDeparture(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Số tiền đặt trước: " + voucher.getDeposit(), vnArial5n);
            document.add(paragraph);
            // tạo bảng danh mục thanh toán
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(5f);
            //Set Column widths
            float[] columnWidths = {1f, 3f, 1f, 1.5f, 1.5f};
            table.setWidths(columnWidths);
            table.addCell(createCellHeader("STT"));
            table.addCell(createCellHeader("Danh mục"));
            table.addCell(createCellHeader("SL"));
            table.addCell(createCellHeader("Đơn giá"));
            table.addCell(createCellHeader("Thành tiền"));
            table.setHeaderRows(1);
            long total = 0;
            ArrayList<Room> arrRoom = voucher.getArrRoom();
            for (int i = 0; i < arrRoom.size(); i++) {
                table.addCell(createCell((i + 1) + ""));
                table.addCell(createCell("Phòng " + arrRoom.get(i).getIdRoom()));
                table.addCell(createCell(voucherController.getDaysHired(voucher.getIdVoucher()) + ""));
                table.addCell(createCell(arrRoom.get(i).getPriceOfRoom() + ""));
                table.addCell(createCell((voucherController.getDaysHired(voucher.getIdVoucher()) * arrRoom.get(i).getPriceOfRoom()) + ""));
                total += voucherController.getDaysHired(voucher.getIdVoucher()) * arrRoom.get(i).getPriceOfRoom();
            }

            ArrayList<Service> arrService = voucher.getArrService();
            for (int i = 0; i < arrService.size(); i++) {
                table.addCell(createCell((arrRoom.size() + 1) + ""));
                table.addCell(createCell(arrService.get(i).getNameService()));
                table.addCell(createCell(arrService.get(i).getNoService() + ""));
                table.addCell(createCell(arrService.get(i).getPriceOfService() + ""));
                table.addCell(createCell((arrService.get(i).getNoService() * arrService.get(i).getPriceOfService()) + ""));
                total += arrService.get(i).getNoService() * arrService.get(i).getPriceOfService();
            }
            document.add(table);
            total = (long) (total - voucher.getDeposit());
            paragraph = new Paragraph("Tiền thanh toán: " + total, vnArial5b);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            paragraph = new Paragraph("Xin chân thành cảm ơn quý khách!", vnTimes8);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printOrderRoom(HotelVoucher voucher, File file) {
        float left = 20;
        float right = 20;
        float top = 10;
        float bottom = 5;
        Document document = new Document(PageSize.A7, left, right, top, bottom);
        Paragraph paragraph = new Paragraph();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            // tạo cá thông tin về hóa đơn
            paragraph = new Paragraph(settingController.getProperties().get(0), vnArial10);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph(settingController.getProperties().get(1), vnTimes8);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph("******************", vnArial5n);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph("Địa chỉ: " + settingController.getProperties().get(2), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Số điện thoại: " + settingController.getProperties().get(3), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Nhân viên: " + staffController.getStaffByIdStaff(voucher.getIdStaff()).getNameStaff(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Số giao dịch: " + voucher.getIdVoucher() + "." + voucher.getIdCustomer() + "." + MainScreen.getCurrentDateTime(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Ngày nhận phòng: " + voucher.getArrival(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Ngày trả phòng: " + voucher.getDeparture(), vnArial5n);
            document.add(paragraph);
            paragraph = new Paragraph("Số tiền đặt trước: " + voucher.getDeposit(), vnArial5n);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(5f);
            //Set Column widths
            float[] columnWidths = {1f, 3f, 1f, 1.5f};
            table.setWidths(columnWidths);
            table.addCell(createCellHeader("STT"));
            table.addCell(createCellHeader("Danh mục"));
            table.addCell(createCellHeader("SL"));
            table.addCell(createCellHeader("Đơn giá"));
            table.setHeaderRows(1);
            ArrayList<Room> arrRoom = voucher.getArrRoom();
            for (int i = 0; i < arrRoom.size(); i++) {
                table.addCell(createCell((i + 1) + ""));
                table.addCell(createCell("Phòng " + arrRoom.get(i).getIdRoom()));
                table.addCell(createCell(voucherController.getDaysHired(voucher.getIdVoucher()) + ""));
                table.addCell(createCell(arrRoom.get(i).getPriceOfRoom() + ""));
            }

            ArrayList<Service> arrService = voucher.getArrService();
            for (int i = 0; i < arrService.size(); i++) {
                table.addCell(createCell((arrRoom.size() + 1) + ""));
                table.addCell(createCell(arrService.get(i).getNameService()));
                table.addCell(createCell(arrService.get(i).getNoService() + ""));
                table.addCell(createCell(arrService.get(i).getPriceOfService() + ""));
            }
            document.add(table);
            paragraph = new Paragraph("Xin chân thành cảm ơn quý khách!", vnTimes8);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.close();
            writer.close();
        } catch (Exception e) {
        }
    }

    private PdfPCell createCell(String content) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, vnArial5n));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private PdfPCell createCellHeader(String content) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, vnArial5n));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

}
