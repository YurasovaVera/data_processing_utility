package org.util.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.util.dto.ErrorInfo;
import org.util.dto.ParsedEntry;

public class EntityValidator {
    private static final Set<Long> ids = new HashSet<>();
    public List<ErrorInfo> validateParsedEntry(ParsedEntry parsedEntry, int lineNumber) {
        List<ErrorInfo> errors = new ArrayList<>();
        errors.addAll(validateId(parsedEntry.id(), lineNumber));
        errors.addAll(validateName(parsedEntry.name(), lineNumber));
        errors.addAll(validateWeight(parsedEntry.weight(), lineNumber));
        return errors;
    }

    private static List<ErrorInfo> validateId(Long id, int lineNumber) {
        List<ErrorInfo> errors = new ArrayList<>();
        if (id < 1) {
            errors.add(new ErrorInfo(lineNumber, "Id must be greater than 0"));
        }
        if (!ids.add(id)) {
            errors.add(new ErrorInfo(lineNumber, "Duplicate id"));
        }
        return errors;
    }

    private static List<ErrorInfo> validateName(String name, int lineNumber) {
        List<ErrorInfo> errors = new ArrayList<>();
        if (name == null || name.isBlank()) {
            errors.add(new ErrorInfo(lineNumber, "Name is empty"));
        } else if (!name.matches("[a-zа-яA-ZА-Я ]+")) {
            errors.add(new ErrorInfo(lineNumber, "Name contains invalid characters"));
        }
        return errors;
    }

    private static List<ErrorInfo> validateWeight(double weight, int lineNumber) {
        List<ErrorInfo> errors = new ArrayList<>();
        if (weight < 0) {
            errors.add(new ErrorInfo(lineNumber, "Weight must be positive"));
        }
        return errors;
    }
}
