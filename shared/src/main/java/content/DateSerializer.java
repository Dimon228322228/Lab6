package content;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * a class used to correct pars the date class
 */
public class DateSerializer extends XmlAdapter<String, Date> {
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
        return format.parse(v) ;
    }

    @Override
    public String marshal(Date v) {
        return format.format(v);
    }
}
