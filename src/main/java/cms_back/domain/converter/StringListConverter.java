package cms_back.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 문자열 리스트를 단일 TEXT 컬럼에 저장하기 위한 JPA Converter.
 * 항목 사이를 개행("\n")으로 구분한다. 각 항목은 단일 라인 텍스트(예: meta line,
 * highlight 한 문장, major 키워드)이므로 개행 충돌이 발생하지 않는다.
 *
 * {@code @ElementCollection} 보조 테이블 없이 List 컬럼을 다루기 위해 사용한다.
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = "\n";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return String.join(DELIMITER, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(dbData.split(DELIMITER, -1)));
    }
}
