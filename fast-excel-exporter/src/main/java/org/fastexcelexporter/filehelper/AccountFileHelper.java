package org.fastexcelexporter.filehelper;

import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.fastexcelexporter.account.Account;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.IntStream;

public class AccountFileHelper {
    private static final String[] HEADERS = {
        "Full Name",
        "Nick Name",
        "Email",
        "Account Number",
        "Initial Balance",
        "Remaining Balance",
        "Created At",
        "Updated At"
    };

    public static void generateWorkbook(OutputStream outputStream, List<Account> accounts) throws IOException {
        try (Workbook wb = new Workbook(outputStream, "FastExcelExporter", "1.0")) {
            Worksheet ws = wb.newWorksheet("Accounts");
            addHeaderRow(ws);

            IntStream.range(0, accounts.size())
                .forEach(rowIndex -> {
                    Account account = accounts.get(rowIndex);
                    account.fillWorksheet(ws, rowIndex + 1);
                });
        }
    }

    private static void addHeaderRow(Worksheet worksheet) {
        IntStream.range(0, HEADERS.length)
            .forEach(index -> worksheet.value(0, index, HEADERS[index]));
        worksheet.range(0, 0, 0, 7)
            .style()
            .horizontalAlignment("center")
            .verticalAlignment("center")
            .bold()
            .fontSize(16)
            .fontColor(Color.WHITE)
            .fillColor(Color.PALE_ORANGE)
            .set();
    }
}
