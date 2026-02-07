package org.util.parser;

import org.util.dto.ParsedEntry;

public class LineParser {
    public ParsedEntry parseLine(String line) {
        String[] tokens = splitLine(line);
        Long id = parseId(tokens[0].trim());
        String name = parseName(tokens[1].trim());
        double weight = parseWeight(tokens[2].trim());
        return new ParsedEntry(id, name, weight);
    }

    private String[] splitLine(String line) {
        String[] tokens = line.split(";");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("each entity should have 3 fields");
        }
        return tokens;
    }

    private Long parseId(String id) {
        if (id.isBlank()) {
            throw new IllegalArgumentException("Id is missing");
        }
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id format");
        }
    }

    private String parseName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name is missing");
        }
        return name;
    }

    private double parseWeight(String weight) {
        if (weight.isBlank()) {
            throw new IllegalArgumentException("Weight is missing");
        }
        try {
            return Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid weight format");
        }
    }
}
