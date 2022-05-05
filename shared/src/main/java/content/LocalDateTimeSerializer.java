package content;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * a class which help to parse LocalDateTime class
 */
public class LocalDateTimeSerializer extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) {
        return v.toString();
    }
}
