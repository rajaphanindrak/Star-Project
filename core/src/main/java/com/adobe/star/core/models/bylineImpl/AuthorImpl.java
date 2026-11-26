package com.adobe.star.core.models.bylineImpl;

import com.adobe.star.core.models.Author;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;


@Model(adaptables ={SlingHttpServletRequest.class},
        adapters = Author.class,
        resourceType = AuthorImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

@Exporter(name="jackson", extensions="json", selector="geeks",
            options={ @ExporterOption(name="SerializationFeature.WRAP_ROOT_VALUE", value="true"),
                        @ExporterOption(name="MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value="true")
            })

@JsonRootName("Author Details")
public class AuthorImpl implements Author {

    public static final String RESOURCE_TYPE = "star/components/author";
    private final static Logger LOG = LoggerFactory.getLogger(AuthorImpl.class);

    @Inject
    @Via("resource")
    @Default(values="AEM")
    private String firstName;

    @ValueMapValue
    @Default(values="GEEKS")
    private String lastName;

    @ScriptVariable
    private Page currentPage;

    @Inject
    @Via("resource")
    @Named("jcr:lastModifiedBy")
    private String modifiedBy;

    @ValueMapValue
    private Boolean professor;

    @RequestAttribute(name = "rAttribute")
    private String reqAttribute;

    @ResourcePath(path="/content/star/us/en/Magazine/guide-la-skateparks")
    @Via("resource")
    Resource resource;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Boolean getIsProfessor() {
        return professor;
    }

    @Override
    public String getPageTitle(){
        return currentPage.getTitle();
    }

    @JsonIgnore
    @Override
    public String getRequestAttribute() {
        return reqAttribute;
    }

    @Override
    public String getPageName() {
        return resource.getName();
    }

    @Override
    public String getModifiedBy() {
        return modifiedBy;
    }

    @PostConstruct
    void init(){
        LOG.info("\n Inside INIT {}:{}", currentPage.getTitle(), resource.getPath());
    }

}
