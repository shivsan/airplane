package com.aerodrome.aeroplane.output;

import com.aerodrome.aeroplane.models.Plane;
import com.aerodrome.aeroplane.models.Row;
import com.aerodrome.aeroplane.models.Seat;
import com.aerodrome.aeroplane.models.Section;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleOutputter {
    public String getPlaneStringOutput(Plane plane) {
        int customerNumberLength = getCustomerNumberMaximumLength(plane);

        if (customerNumberLength < 0)
            return "";

        List<List<String>> sectionOutputs =
                Arrays.stream(plane.getSections())
                        .map(section -> printSection(section, customerNumberLength)).collect(Collectors.toList());

        int longestSectionHeight =
                sectionOutputs.stream()
                        .map(sectionList -> sectionList.size())
                        .max(Integer::compareTo).orElse(0);

        String[] concatenatedSectionStringList = new String[longestSectionHeight];

        for (int i = 0; i < concatenatedSectionStringList.length; i++) {
            concatenatedSectionStringList[i] = "";
        }

        String padding = "   ";

        for (int sectionIndex = 0; sectionIndex < sectionOutputs.size(); sectionIndex++) {
            final var section = sectionOutputs.get(sectionIndex);
            int sectionHeight = section.size();
            int sectionWidth = section.get(0).length();

            for (int outputRowIndex = 0; outputRowIndex < sectionHeight; outputRowIndex++) {
                if (sectionIndex != 0 && sectionIndex != sectionOutputs.size())
                    concatenatedSectionStringList[outputRowIndex] += padding;

                concatenatedSectionStringList[outputRowIndex] += section.get(outputRowIndex);
            }

            if (sectionHeight < longestSectionHeight) {
                int index = sectionHeight;

                while (index < longestSectionHeight) {
                    if (sectionIndex != 0 && sectionIndex != sectionOutputs.size())
                        concatenatedSectionStringList[index] += padding;

                    concatenatedSectionStringList[index] += " ".repeat(sectionWidth);
                    index++;
                }
            }
        }

        return Arrays.stream(concatenatedSectionStringList).reduce((line1, line2) -> line1 + "\n" + line2).get();
    }

    private int getCustomerNumberMaximumLength(Plane plane) {
        return getNumberOfDigits(
                Arrays.stream(plane.getSections())
                        .flatMap(section -> Arrays.stream(section.getRows()))
                        .flatMap(row -> Arrays.stream(row.getSeats()))
                        .map(seat -> seat.getCustomerNumber().orElse(0))
                        .max(Integer::compareTo)
                        .orElse(0)
        );
    }

    private int getNumberOfDigits(int n) {
        int widthOfNumber = 0;

        while (n > 0) {
            widthOfNumber++;
            n = n / 10;
        }

        return widthOfNumber;
    }

    protected List<String> printSection(Section section, int customerNumberLength) {
        final var rowList = new LinkedList<String>();
        final var rows = section.getRows();
        final var rowSize = rows[0].getSeats();

        rowList.add("  ┌" + "--".repeat(rowSize.length) + (rowSize.length > 1 ? "-".repeat(rowSize.length - 1) : "") + ("-".repeat(customerNumberLength).repeat(rowSize.length)) + "┐  ");

        for (int i = 0; i < rows.length; i++) {
            rowList.add(getRowText(rows[i], customerNumberLength));
        }
        rowList.add("  └" + "--".repeat(rowSize.length) + (rowSize.length > 1 ? "-".repeat(rowSize.length - 1) : "") + ("-".repeat(customerNumberLength).repeat(rowSize.length)) + "┘  ");
        return rowList;
    }

    protected String getRowText(Row row, int customerNumberLength) {
        final var rowStringBuilder = new StringBuilder();
        final var leftMostSeat = row.getSeats()[0];
        final var rightMostSeat = row.getSeats()[row.getSeats().length - 1];

        rowStringBuilder.append(leftMostSeat.getType().getSuffix());
        rowStringBuilder.append(" |");

        Arrays.stream(row.getSeats()).sequential()
                .forEach(seat ->
                {
                    rowStringBuilder.append(getSeatText(seat, customerNumberLength));
                    rowStringBuilder.append("|");
                });

        rowStringBuilder.append(" ");
        rowStringBuilder.append(rightMostSeat.getType().getSuffix());

        return rowStringBuilder.toString();
    }

    private String getSeatText(Seat seat, int customerNumberLength) {

        if (seat.isOccupied()) {
            final var customerNumber = seat.getCustomerNumber().get();
            customerNumberLength -= (int) Math.log10(customerNumber);
            return String.format(" " + " ".repeat(customerNumberLength - 1) + "%s ", customerNumber);
        } else
            return "   " + " ".repeat(customerNumberLength - 1);
    }
}
