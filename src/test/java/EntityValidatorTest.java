import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.util.dto.ErrorInfo;
import org.util.dto.ParsedEntry;
import org.util.validator.EntityValidator;

public class EntityValidatorTest {
    private EntityValidator entityValidator;

    @BeforeEach
    void setUp() throws Exception {
        entityValidator = new EntityValidator();

        Field idsField = EntityValidator.class.getDeclaredField("ids");
        idsField.setAccessible(true);
        Set<Long> ids = (Set<Long>) idsField.get(null);
        ids.clear();
    }

    @Test
    void validateParsedEntryWithValidEntryShouldReturnEmptyErrors() {
        ParsedEntry validEntry = new ParsedEntry(1L, "John Doe", 75.5);
        int lineNumber = 5;

        List<ErrorInfo> errors = new EntityValidator().validateParsedEntry(validEntry, lineNumber);

        assertTrue(errors.isEmpty());
    }

    @Test
    void validateIdWithIdLessThanOneReturnsError() {
        List<ErrorInfo> errors = entityValidator.validateParsedEntry(new ParsedEntry(0L, "John", 70.0), 2);

        assertEquals(1, errors.size());
        assertEquals(2, errors.getFirst().lineNumber());
        assertEquals("Id must be greater than 0", errors.getFirst().reason());
    }

    @Test
    void validateIdWithDuplicateIdReturnsDuplicateError() {
        entityValidator = new EntityValidator();
        entityValidator.validateParsedEntry(new ParsedEntry(5L, "John", 70.0), 3);

        List<ErrorInfo> errors = entityValidator.validateParsedEntry(new ParsedEntry(5L, "Jane", 65.0), 4);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(4, errors.getFirst().lineNumber());
        Assertions.assertEquals("Duplicate id", errors.getFirst().reason());
    }

    @ParameterizedTest
    @ValueSource(strings = {"John123", "Anna!", "John@Doe", "Mary_Ann", "123", "!@#$"})
    void validateNameWithInvalidCharactersReturnsError(String invalidName) {
        List<ErrorInfo> errors = entityValidator.validateParsedEntry(new ParsedEntry(1L, invalidName, 70.0), 10);

        assertEquals(1, errors.size());
        assertEquals(10, errors.getFirst().lineNumber());
        assertEquals("Name contains invalid characters", errors.getFirst().reason());
    }

    @Test
    void validateWeightWithNegativeWeightReturnsError() {
        List<ErrorInfo> errors = entityValidator.validateParsedEntry(new ParsedEntry(1L, "John", -10.5), 13);

        assertEquals(1, errors.size());
        assertEquals(13, errors.getFirst().lineNumber());
        assertEquals("Weight must be positive", errors.getFirst().reason());
    }
}
