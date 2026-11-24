package com.adobe.star.core.models;

import com.adobe.star.core.models.helper.MultifieldBooksHelperBean;
import com.adobe.star.core.models.helper.PublishDetailsHelperBean;

import java.util.List;
import java.util.Map;

public interface AuthorBooks {
    String getAuthorName();
    List<String> getBooks();
    List<Map<String, String>> getBookDetails();
    List<PublishDetailsHelperBean> getPublishingDetails();
    List<MultifieldBooksHelperBean> getBookHistory();
}
