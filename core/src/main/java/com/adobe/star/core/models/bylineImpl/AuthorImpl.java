package com.adobe.star.core.models.bylineImpl;

import com.adobe.star.core.models.Author;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;


@Model(adaptables ={SlingHttpServletRequest.class},
        adapters = Author.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AuthorImpl implements Author {

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
