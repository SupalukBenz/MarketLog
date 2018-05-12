package program;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import orm.ItemsDao;
import orm.SaleDetailDao;
import orm.SalesDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Report {
    public static void salesToPdf(SalesDao salesDao){

        Document document = new Document(PageSize.A4, 5, 30, 50, 50);
        FileChooser fileChooser;
        File output = null;
        try {

            fileChooser = new FileChooser();
            fileChooser.setInitialFileName("SalesReport.pdf");
            fileChooser.setInitialDirectory( new File("src"));
            output = fileChooser.showSaveDialog(new Stage());

            PdfWriter.getInstance(document, new FileOutputStream(output));
            float[] columnWidths = {6, 4, 15, 4, 5, 4};
            PdfPTable reportTable = new PdfPTable(columnWidths);
            reportTable.setSpacingBefore(25);
            reportTable.setSpacingAfter(20);
            PdfPCell table_cell;
            document.open();

            Paragraph titleShow = new Paragraph("Sales Report", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
            titleShow.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleShow);
            LocalDate date = LocalDate.now();
            Paragraph dateShow = new Paragraph(date.toString());
            dateShow.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(dateShow);

            table_cell = new PdfPCell(new Phrase("Date", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Receipt", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Company", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Quantity", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);

            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Total", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Status", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            double total = 0;
            int orderTotal = 0;
            Phrase phrase;
            for(Sales sales: salesDao){
                orderTotal++;
                if(sales.getStatus().equals("paid")){
                    total += sales.getTotal();
                }

                String date_sale = sales.getDate();
                phrase = new Phrase(date_sale, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                reportTable.addCell(table_cell);

                String receipt_id = String.valueOf(sales.getReceiptId());
                phrase = new Phrase(receipt_id, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                reportTable.addCell(table_cell);

                String company = sales.getCompany();
                phrase = new Phrase(company, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                reportTable.addCell(table_cell);

                String qty_sale = String.valueOf(sales.getQuantity());
                phrase = new Phrase(qty_sale, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                table_cell.setHorizontalAlignment(1);
                reportTable.addCell(table_cell);

                String total_sale = String.valueOf(sales.getTotal());
                phrase = new Phrase(total_sale, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                table_cell.setHorizontalAlignment(2);
                reportTable.addCell(table_cell);

                String status = sales.getStatus();
                phrase = new Phrase(status, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                table_cell.setHorizontalAlignment(1);
                reportTable.addCell(table_cell);

            }

            document.add(reportTable);

            Paragraph orderShow = new Paragraph("Order: " + orderTotal);
            orderShow.setAlignment(2);
            document.add(orderShow);
            String getTotal = String.format("%-10s %12.2f %5s", "Total:", total, "Baht");
            Paragraph amountShow = new Paragraph(getTotal);
            amountShow.setAlignment(2);
            document.add(amountShow);

            document.close();

        } catch (IOException io){
            System.out.println("Cannot write pdf file.");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void salesDetailToPDF(SaleDetailDao saleDetailDao, int receiptID, String company, String status, double amount, double vat){

        Document document = new Document(PageSize.A4, 20, 30, 50, 50);
        FileChooser fileChooser;
        File output = null;
        try {
            fileChooser = new FileChooser();
            fileChooser.setInitialFileName("Order-"+receiptID+".pdf");
            fileChooser.setInitialDirectory( new File("src"));
            output = fileChooser.showSaveDialog(new Stage());

            PdfWriter.getInstance(document, new FileOutputStream(output));

            float[] columnWidths = {2, 15, 7, 5, 4};
            PdfPTable reportTable = new PdfPTable(columnWidths);
            reportTable.setSpacingBefore(25);
            reportTable.setSpacingAfter(20);
            PdfPCell table_cell;
            document.open();

            String title = String.format("%20s %10d", "Receipt ID", receiptID);
            Paragraph titleShow = new Paragraph(title, FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
            titleShow.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleShow);

            LocalDate date = LocalDate.now();
            Paragraph dateShow = new Paragraph(date.toString());
            dateShow.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(dateShow);

            String companyDetail = String.format("%-10s %20s", "Company:", company);
            Paragraph companyShow = new Paragraph(companyDetail);
            document.add(companyShow);

            String statusDetail = String.format("%-10s %20s", "Status:", status);
            Paragraph statusShow = new Paragraph(statusDetail);
            document.add(statusShow);

            table_cell = new PdfPCell(new Phrase("No.", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Item", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Description", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Quantity", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);

            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Total", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);


            double total = 0;
            int orderTotal = 0;
            Phrase phrase;
            for(SaleDetail sales: saleDetailDao){
                orderTotal++;
                if(sales.getId_detail() == receiptID) {
                    total += sales.getTotal_detail();

                    String number = String.valueOf(orderTotal);
                    phrase = new Phrase(number, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                    table_cell = new PdfPCell(phrase);
                    reportTable.addCell(table_cell);

                    String item = sales.getItem_detail();
                    phrase = new Phrase(item, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                    table_cell = new PdfPCell(phrase);
                    reportTable.addCell(table_cell);

                    String description = String.valueOf(sales.getDescription_detail());
                    phrase = new Phrase(description, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                    table_cell = new PdfPCell(phrase);
                    reportTable.addCell(table_cell);

                    String quantity = String.valueOf(sales.getQty_detail());
                    phrase = new Phrase(quantity, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                    table_cell = new PdfPCell(phrase);
                    table_cell.setHorizontalAlignment(2);
                    reportTable.addCell(table_cell);

                    String total_detail = String.valueOf(sales.getTotal_detail());
                    phrase = new Phrase(total_detail, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                    table_cell = new PdfPCell(phrase);
                    table_cell.setHorizontalAlignment(1);
                    reportTable.addCell(table_cell);
                }

            }

            document.add(reportTable);

            String amountDetail = String.format("%-7s %12.2f", "Amount:", amount);
            Paragraph amountShow = new Paragraph(amountDetail);
            amountShow.setAlignment(2);
            document.add(amountShow);

            String vatDetail = String.format("%-7s %12.2f", "Vat 10%:", vat);
            Paragraph vatShow = new Paragraph(vatDetail);
            vatShow.setAlignment(2);
            document.add(vatShow);

            String getTotal = String.format("%-10s %12.2f %5s", "Total:", total, "Baht");
            Paragraph totalShow = new Paragraph(getTotal);
            totalShow.setAlignment(2);
            document.add(totalShow);

            document.close();

        } catch (IOException io){
            System.out.println("Cannot write pdf file.");
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public static void saveItemsToPDF(ItemsDao itemsDao){
        Document document = new Document(PageSize.A4, 5, 30, 50, 50);
        FileChooser fileChooser;
        File output = null;
        try {

            fileChooser = new FileChooser();
            fileChooser.setInitialFileName("ItemsReport.pdf");
            fileChooser.setInitialDirectory( new File("src"));
            output = fileChooser.showSaveDialog(new Stage());

            PdfWriter.getInstance(document, new FileOutputStream(output));

            float[] columnWidths = {2, 15, 7, 4, 5};
            PdfPTable reportTable = new PdfPTable(columnWidths);
            reportTable.setSpacingBefore(25);
            reportTable.setSpacingAfter(20);
            PdfPCell table_cell;
            document.open();

            Paragraph titleShow = new Paragraph("Items Report", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
            titleShow.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleShow);
            LocalDate date = LocalDate.now();
            Paragraph dateShow = new Paragraph(date.toString());
            dateShow.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(dateShow);

            table_cell = new PdfPCell(new Phrase("ID", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Item name", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Description", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Quantity", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);

            reportTable.addCell(table_cell);

            table_cell = new PdfPCell(new Phrase("Total", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
            table_cell.setHorizontalAlignment(1);
            reportTable.addCell(table_cell);
            Phrase phrase;

            double total = 0;
            int itemTotal = 0;
            for(Item item: itemsDao){
                itemTotal++;
                total+=item.getTotal_item();

                String id = String.valueOf(item.getId_item());
                phrase = new Phrase(id, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                reportTable.addCell(table_cell);

                String nameItem = item.getName_item();
                phrase = new Phrase(nameItem, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                reportTable.addCell(table_cell);

                String description = item.getDescription_item();
                phrase = new Phrase(description, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                reportTable.addCell(table_cell);

                String quantity = String.valueOf(item.getQuantity_item());
                phrase = new Phrase(quantity, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                table_cell.setHorizontalAlignment(1);
                reportTable.addCell(table_cell);

                String totalItem = String.valueOf(item.getTotal_item());
                phrase = new Phrase(totalItem, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
                table_cell = new PdfPCell(phrase);
                table_cell.setHorizontalAlignment(2);
                reportTable.addCell(table_cell);

            }

            document.add(reportTable);
            String amount = String.format("%-6s %12d %5s", "Stock:", itemTotal, "items");
            Paragraph orderShow = new Paragraph(amount);
            orderShow.setAlignment(2);
            document.add(orderShow);
            String getTotal = String.format("%-10s %12.2f %5s", "Total:", total, "Baht");
            Paragraph amountShow = new Paragraph(getTotal);
            amountShow.setAlignment(2);
            document.add(amountShow);

            document.close();

        } catch (IOException io){
            System.out.println("Cannot write pdf file.");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
