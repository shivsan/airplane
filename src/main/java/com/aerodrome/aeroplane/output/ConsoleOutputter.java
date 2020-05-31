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
    public String printPlane(Plane plane) {
        List<List<String>> sectionOutputs =
                Arrays.stream(plane.getSections())
                        .map(section -> printSection(section)).collect(Collectors.toList());

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

    protected List<String> printSection(Section section) {
        final var rowList = new LinkedList<String>();
        final var rows = section.getRows();
        final var rowSize = rows[0].getSeats();

        rowList.add("  ┌" + "---".repeat(rowSize.length) + (rowSize.length > 1 ? "-".repeat(rowSize.length - 1) : "") + "┐  ");

        for (int i = 0; i < rows.length; i++) {
            rowList.add(getRowText(rows[i]));
        }

        rowList.add("  └" + "---".repeat(rowSize.length) + (rowSize.length > 1 ? "-".repeat(rowSize.length - 1) : "") + "┘  ");
        return rowList;
    }

    protected String getRowText(Row row) {
        final var rowStringBuilder = new StringBuilder();
        final var leftMostSeat = row.getSeats()[0];
        final var rightMostSeat = row.getSeats()[row.getSeats().length - 1];

        rowStringBuilder.append(leftMostSeat.getType().getSuffix());
        rowStringBuilder.append(" |");

        Arrays.stream(row.getSeats()).sequential()
                .forEach(seat ->
                {
                    rowStringBuilder.append(getSeatText(seat));
                    rowStringBuilder.append("|");
                });

        rowStringBuilder.append(" ");
        rowStringBuilder.append(rightMostSeat.getType().getSuffix());

        return rowStringBuilder.toString();
    }

    private String getSeatText(Seat seat) {
        if (seat.isOccupied()) {
            return String.format(" %s ", seat.getCustomerNumber().get());
        } else
            return "   ";
    }
}
