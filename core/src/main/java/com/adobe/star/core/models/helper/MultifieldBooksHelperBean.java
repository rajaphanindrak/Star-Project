package com.adobe.star.core.models.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MultifieldBooksHelperBean {

    private static final Logger LOG = LoggerFactory.getLogger(MultifieldBooksHelperBean.class);

    private String bookName;
    private Calendar datePublished;
    private List<NestedCopiesHelperBean> nestedCopiesHelperBeanList;

    public MultifieldBooksHelperBean(Resource resource){
        try{
            if(StringUtils.isNotBlank(resource.getValueMap().get("bookName", String.class))){
                this.bookName=resource.getValueMap().get("bookName", String.class);
            }
            if(resource.getValueMap().get("datePublished", Calendar.class) != null){
                this.datePublished = resource.getValueMap().get("datePublished", Calendar.class);
            }
            LOG.info("\n Multifield Books constructor instantiated with node : {}", resource.getPath());
        }catch(Exception e){
            LOG.info("\n Error while instantiating the books multifield {}", e.getMessage());
        }
    }

    public void setBookEditions(List<NestedCopiesHelperBean> nestedCopiesHelperBeanList) {
        this.nestedCopiesHelperBeanList = nestedCopiesHelperBeanList;
    }

    public List<NestedCopiesHelperBean> getBookEditions() {
        return nestedCopiesHelperBeanList;
    }

    public String getBookName() {
        return bookName;
    }

    public Calendar getDatePublished() {
        return datePublished;
    }

    public String getFormatedDatePublished(){
        if(datePublished == null)
        {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        return formatter.format(datePublished.getTime());
    }
}
