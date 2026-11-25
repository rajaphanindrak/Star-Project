
(function($, Coral){
    "use strict"

    console.log("------authorbooks text validation js loaded-------");

    var registry=$(window).adaptTo("foundation-registry");
    registry.register("foundation.validation.validator",{
        selector:"[data-validation=authorName-text]",
        validate:function(element){
            var el = $(element);
            var pattern = /^[A-Z]+$/;
            let value = el.val();
            if(!pattern.test(value)){
                return "Please add only uppercase letters"
            }
        }
    })
})(jQuery, Coral);
