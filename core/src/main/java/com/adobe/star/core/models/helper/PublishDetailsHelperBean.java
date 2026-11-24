package com.adobe.star.core.models.helper;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PublishDetailsHelperBean {

    private static final Logger LOG = LoggerFactory.getLogger(PublishDetailsHelperBean.class);

    private Calendar publishDate;
    private Long copies;

    public PublishDetailsHelperBean(Resource resource){
        try{
            ValueMap properties = resource.getValueMap();

            Object dateValue = properties.get("publishDate");

            LOG.info("Child resource: {}", resource.getPath());
            LOG.info("Date raw value: {}", dateValue);
            LOG.info("Date raw type: {}",
                    dateValue != null ? dateValue.getClass().getName() : "null");

            Calendar publishDate =
                    properties.get("publishDate", Calendar.class);

            LOG.info("Calendar date value: {}", publishDate);

            Long copies = properties.get("copies", Long.class);

            LOG.info("Copies value: {}", copies);

            this.publishDate = publishDate;
            this.copies = copies;
        }catch(Exception e){
            LOG.info(" \n Error in Bean Class: {}", e.getMessage());
        }
    }

    public Calendar getPublishDate() {
        return publishDate;
    }

    public String getFormattedPublishDate() {
        if (publishDate == null) {
            return "";
        }
        SimpleDateFormat formatter =
                new SimpleDateFormat("MMM dd, yyyy");

        return formatter.format(publishDate.getTime());
    }

    public Long getCopies() {
        return copies;
    }
}
