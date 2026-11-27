package com.adobe.star.core.models.bylineImpl;

import com.adobe.star.core.models.AuthorBooks;
import com.adobe.star.core.models.helper.MultifieldBooksHelperBean;
import com.adobe.star.core.models.helper.NestedCopiesHelperBean;
import com.adobe.star.core.models.helper.PublishDetailsHelperBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.sling.api.SlingHttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = AuthorBooks.class,
        resourceType = "star/components/authorBooks",
        defaultInjectionStrategy= DefaultInjectionStrategy.OPTIONAL)

@Exporter(name="jackson", extensions="json", selector="custom",
            options={@ExporterOption(name= "SerializationFeature.WRAP_ROOT_VALUE", value="true")})
@JsonRootName("Author Books")
public class AuthorBooksImpl implements AuthorBooks {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorBooksImpl.class);

    @SlingObject
    Resource componentResource;

    @Inject
    @Via("resource")
    private String authorName;

    @ValueMapValue
    private List<String> books;

    @Override
    public String getAuthorName() {
        return authorName;
    }

    @Override
    public List<String> getBooks() {
        return books != null ? new ArrayList<String>(books) : Collections.emptyList();
    }

    @Override
    @JsonProperty(value="Book Details")
    public List<Map<String, String>> getBookDetails() {
        List<Map<String, String>> bookDetailsListMap = new ArrayList<>();
        try{
            Resource bookDetails = componentResource.getChild("bookDetailsList");
            if(bookDetails != null){
                for(Resource book : bookDetails.getChildren()){
                    Map<String, String> bookMap = new HashMap<>();
                    bookMap.put("bookName", book.getValueMap().get("bookName", String.class));
                    bookMap.put("bookSubject", book.getValueMap().get("bookSubject", String.class));
                    bookMap.put("publishedYear", book.getValueMap().get("publishedYear", String.class));
                    bookDetailsListMap.add(bookMap);
                }
            }
        }catch(Exception e){
            LOG.info("Error while getting the details {} ",e.getMessage());
        }
        LOG.info("\n Size : {}",bookDetailsListMap.size());
        return bookDetailsListMap;
    }

    @Override
    @JsonProperty(value="Publishing Details")
    public List<PublishDetailsHelperBean> getPublishingDetails(){
        List<PublishDetailsHelperBean> publishDetailsList = new ArrayList<>();
        try{
            Resource publishDetailsMap = componentResource.getChild("publishingDetails");
            if(publishDetailsMap != null){
                for(Resource bookPublishDetails : publishDetailsMap.getChildren()){
                    PublishDetailsHelperBean publishDetailsHelperBean = new PublishDetailsHelperBean(bookPublishDetails);
                    publishDetailsList.add(publishDetailsHelperBean);
                    LOG.info("\n Bean path : {}", bookPublishDetails.getPath());
                    LOG.info("\n Bean name : {}", bookPublishDetails.getName());
                }
            }
        }catch(Exception e){
            LOG.info("\n Error while getting publishing details : {}", e.getMessage());
        }
        return publishDetailsList;
    }

    @Override
    @JsonProperty(value="Book_History")
    public List<MultifieldBooksHelperBean> getBookHistory() {
        List<MultifieldBooksHelperBean> bookHistory = new ArrayList<>();
        try{
            Resource bookHistoryResources = componentResource.getChild("booklist");
            if(bookHistoryResources != null){
                for(Resource bookHistoryResource : bookHistoryResources.getChildren())
                {
                    MultifieldBooksHelperBean multifieldBooksHelperBean = new MultifieldBooksHelperBean(bookHistoryResource);
                    Resource nestedResources = bookHistoryResource.getChild("yearlycopies");
                    if(nestedResources != null)
                    {
                        List<NestedCopiesHelperBean> nestedCopiesHelperBeansList = new ArrayList<>();
                        for(Resource eachNestedResource: nestedResources.getChildren()){
                            NestedCopiesHelperBean nestedCopiesHelperBean = new NestedCopiesHelperBean(eachNestedResource);
                            nestedCopiesHelperBeansList.add(nestedCopiesHelperBean);
                        }
                        multifieldBooksHelperBean.setBookEditions(nestedCopiesHelperBeansList);
                    }
                    bookHistory.add(multifieldBooksHelperBean);
                }
            }
        }catch(Exception e){
            LOG.info("Error getting the book history with the Nested multifield {}", e.getMessage());
        }
        LOG.info("\n Size multifield {}", bookHistory.size());
        return bookHistory;
    }
}
