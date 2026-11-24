package com.adobe.star.core.models.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NestedCopiesHelperBean {

    private final static Logger LOG = LoggerFactory.getLogger(NestedCopiesHelperBean.class);

    private String year;
    private Long copies;

    public NestedCopiesHelperBean(Resource resource){
        try{
            if(StringUtils.isNotBlank(resource.getValueMap().get("year", String.class))){
                this.year = resource.getValueMap().get("year", String.class);
            }
            if((resource.getValueMap().get("copies", Long.class)) != null){
                this.copies=resource.getValueMap().get("copies", Long.class);
            }
            LOG.info("Nested multifield injected {} ", resource.getPath());
        }catch(Exception e){
            LOG.info("\n Error while assigning nested multifield: {}",e.getMessage());
        }

    }

    public String getYear() {
        return year;
    }

    public Long getCopies() {
        return copies;
    }
}
