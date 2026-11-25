/* global jQuery, Coral*/
(function($, Coral){
    "use strict";

    console.log("---------------CLIENTLIBS LOADED---------------");

    var registry = $(window).adaptTo("foundation-registry");
     //validator of required field for multifield max and min items
    registry.register("foundation.validation.validator",{
        selector:"[data-validation=authorBooks-singleMultifield]",
        validate: function(element){
            var el = $(element);
            let min = el.data("min-items");
            let max = el.data("max-items");
            let items = el.children("coral-multifield-item").length;
            let domitems = el.children("coral-multifield-item");
            console.log("max:",max,"min:",min,"items:",items);
            if(items > max){
                domitems.last().remove();
                return "You can add only "+max+" books. You are trying to add "+items+"th book."
            }
            if(items < min){
                return "You add atleast "+min+" books."
            }
        }
    });
})(jQuery,Coral);
