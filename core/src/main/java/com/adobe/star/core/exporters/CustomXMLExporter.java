package com.adobe.star.core.exporters;

import org.apache.sling.models.export.spi.ModelExporter;
import org.apache.sling.models.factory.ExportException;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Map;

@Component(service=ModelExporter.class)
public class CustomXMLExporter implements ModelExporter {
    private final static Logger LOG = LoggerFactory.getLogger(CustomXMLExporter.class);

    @Override
    public boolean isSupported(Class<?> aClass) {
        return true;
    }

    @Override
    public <T> T export(Object model, Class<T> aClass, Map<String, String> map) throws ExportException {
        StringWriter stringWriter = new StringWriter();
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(model.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(model, stringWriter);
        }catch (Exception e){
            LOG.info("\n Error exporting model with Marshaller: {} ", e.getMessage());
        }
        return (T) stringWriter.toString();
    }

    @Override
    public String getName() {
        return "XMLExporter";
    }
}
