package com.adobe.star.core.models.bylineImpl;

import com.adobe.star.core.models.Byline;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.commons.lang3.StringUtils;
import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.models.factory.ModelFactory;

import javax.annotation.PostConstruct;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = Byline.class,
        resourceType = BylineImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name="XMLExporter", selector="custom", extensions="xml")
@XmlRootElement(name="Byline-Details")
public class BylineImpl implements Byline {

    protected static final String RESOURCE_TYPE = "star/components/Byline";

    @OSGiService
    private ModelFactory modelFactory;

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String name;

    @ValueMapValue
    private List<String> occupations;

    private Image image;

    @PostConstruct
    protected void init() {
        image = modelFactory.getModelFromWrappedRequest(request,
                request.getResource(),
                Image.class);
    }

    @XmlElement(name="Author-Name")
    @Override
    public String getName() {
        return name;
    }

    @XmlElementWrapper(name="Author-Occupations")
    @Override
    public List<String> getOccupations() {
        if(occupations != null) {
            Collections.sort(occupations);
            return new ArrayList<String>(occupations);
        }else{
            return Collections.emptyList();
        }
    }


    @Override
    public boolean isEmpty()
    {
        final Image componentImage = getImage();
        if(StringUtils.isBlank(name))
        {
            return true;
        }
        if((occupations == null || occupations.isEmpty()))
        {
            return true;
        }
        if(componentImage == null || StringUtils.isBlank(componentImage.getSrc()))
        {
            return true;
        }
        return false;
    }

    private Image getImage(){
        return image;
    }
}
//test
