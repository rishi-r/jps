$(function() {
    "use strict";

    //app_init();
    //commonfn.delete_rows();
    //commonfn.menu_loads();

});


function Custombox()
{
    var _this = this;
    var varTime;
    this.init = function()
    {
        this.info_icon = '<i class="fa fa-info-sign"></i>';
        this.remove_icon = '<i class="fa fa-times"></i>';
    }
    this.alert =function(mess, error_success,time)
    {
        this.init();
        $("#cust_alert").removeClass("cust_alert_success").removeClass("cust_alert_error").html('').hide();
        if(typeof error_success === "undefined" || error_success == null)
        {
            error_success = 1;
        }
        if(typeof time === "undefined")
        {
            time = 0;
        }

        var class_set = 'cust_alert_success';
        if(error_success == 0)
        {
            class_set = 'cust_alert_error';
        }

        $("#cust_alert").addClass(class_set).html(this.info_icon+mess+this.remove_icon).slideDown(500);
        clearTimeout(varTime);
        if(time!=0)
        {
            varTime = setTimeout(function(){
                _this.removealert();
            },time)
        }
        $("#cust_alert .icon-remove-sign").bind("click",function(){
            $("#cust_alert").slideUp(500);
        });
        
    }
    this.defalert =function(mess)
    {
        $("#cust_loader_def").html(mess).slideDown(500);
    }
    
    this.removedefalert = function()
    {
        setTimeout(function(){
            $("#cust_loader_def").slideUp(1000);
        },1000);
    }
    this.removealert =function()
    {
        $("#cust_alert").removeClass("cust_alert_success").removeClass("cust_alert_success").html('').slideUp(500);
    }
}

var custombox = new Custombox();

/*
 * 
 * common functions library file
 * 
 */

window.commonfn = window.commonfn || (function init($, undefined) {
    "use strict";
    var exports = {loaded:[]};
    exports.callAjax = function (url,dataString)
    {
        return $.ajax({     
                    type: "POST",
                    url: url,
                    cache: false,
                    data: dataString,
                    dataType: "json"
            });
    }

    exports.doAjax = function(options)
    {
        var s = {message:"wait..",load:false};
        s = $.extend({}, {
            ajaxSuccess:function(){}
        }, options);
        exports.disableButton(s.elem, s.message);
        if(s.load)
        {
            exports.block_body();
        }
        var ajax = $.ajax({     
                    type: "POST",
                    url: s.url,
                    cache: false,
                    data: s.dataString,
                    dataType: "json"
        });

        ajax.complete(function(){
            if(s.load)
                exports.unblock_body();
            exports.enableButton(s.elem);
        });
        ajax.success(function(data){
            if(data.error == 'logged_out')
            {
                exports.left_col();
                exports.h_user_settings();
                exports.openloginalert();
            }
            else
            if(data.success == 'logout')
            {
                s.url = HTTP_PATH+'login';
                exports.doAjax(s);
                exports.left_col();
                exports.h_user_settings();
            }
            else
            if(data.success == 'logged_in')
            {
                exports.left_col();
                exports.h_user_settings();
                s.url = HTTP_PATH;
                exports.doAjax(s);
            }
            else
            if(data.success == 1)
            {
                if(typeof s.container != 'undefined' && s.container != false)
                {
                    $(s.container).html(data.html);
                    if(typeof data.script != "undefined")
                    //if(typeof data.script != "undefined" && typeof data.script_name!= "undefined" && !exports.inArray(data.script_name,exports.loaded))
                    {
                        data.script = $(data.script);
                        $("body").append(data.script);
                        exports.loaded.push(data.script_name);
                    }
                }
                if(typeof s.load != 'undefined' && s.load != false)
                {
                    $(".sidebar-menu li").removeClass("active");
                    $('.sidebar-menu a[href^="'+s.url+'"]').closest("li").addClass("active");
                    document.title = data.pageTitle+" | "+SITE_NAME;
                    window.history.pushState({"html":data.html,"pageTitle":data.pageTitle+" | "+SITE_NAME},"", s.url);
                    app_init();
                }
            }
            if($.isFunction(s.ajaxSuccess))
                s.ajaxSuccess.call(this,data);
        });
    }

    exports.openloginalert = function(msg,path)
    {
        if(typeof path =="undefined")
            path = HTTP_PATH;
        bootbox.alert("Your are logged out",function(){
                window.location = path;
                return false;
        });
    }

    exports.timeConverter = function(UNIX_timestamp , h_i_s){
        if(h_i_s == undefined)
            h_i_s = false;
        var a = new Date(UNIX_timestamp*1000);
        var months = ['January','Feburary','March','April','May','June','July','August','September','October','November','December'];
        var year = a.getFullYear();
        var month = months[a.getMonth()];
        var date = a.getDate();

        var hour = a.getHours();
        var min = a.getMinutes();
        var sec = a.getSeconds();
        var time = month+' '+date+', '+year ;
        if(h_i_s)
        {
            time += ' '+hour+':'+min+':'+sec;
        }
        return time;
    }


    exports.disableButton = function(elem,message)
    {
        if(typeof message == "undefined")
            message = "Please wait...";
        var id = $(elem).attr("id");
        var class_es = $(elem).attr("class");
        var thistag = $(elem).clone();
        $(elem).addClass("displaynonehard");
        if ($(elem).is( "input")) 
        {
            var button = $("<button></button>");
            $(button).attr("class",class_es).attr("disabled","disabled").attr("id",id+"_dummybtn").html('<img src="'+ICONS_PATH+'ajax-loader.gif" width=20 /> '+message);
            
            $(elem).after(button);
        }
        else
        {
            $(thistag).removeAttr("id").attr("disabled","disabled").attr("id",id+"_dummybtn").html('<img src="'+ICONS_PATH+'ajax-loader.gif" width=20 /> '+message);
            $(elem).after(thistag);
        }
        var date =  new Date();
        exports.elem = date.getTime();

    }

    exports.enableButton = function (elem)
    {
        var date =  new Date();
        var elem_now = date.getTime();
        var timeDiff = elem_now - exports.elem;
        if(timeDiff < 100)
        {
            timeDiff = 1000;
        }
        else
            timeDiff = 0;
        
        var id = $(elem).attr("id");
        $(elem).fadeIn("slow",function(){
            $(this).removeAttr("disabled").removeClass("displaynonehard");
            $("#"+id+"_dummybtn").remove();
        });
            
        
        
        
    }
    exports.check_all_handle = function()
    {
        if(!$.isFunction($().iCheck))
            return false;
        $("#checkallitem, input.actionsid").iCheck({
            checkboxClass: 'icheckbox_square-blue'
        }).attr("autocomplete",'off');
        
        $("#checkallitem").on('ifChecked',function(e) {
            $("input.actionsid").each(function() {
                    $(this).iCheck('check');
            });
            check_sel_opt();
        });
        $("#checkallitem").on('ifUnchecked',function(e) {
            $("input.actionsid").each(function() {
                    $(this).iCheck('uncheck');
            });
            check_sel_opt();
        });
        
        $("input.actionsid").on('ifChecked',function(e) {
            check_sel_opt();
        });
        $("input.actionsid").on('ifUnchecked',function(e) {
            $("input.actionsid").each(function() {
                if(!(this.checked))
                {
                    $("#checkallitem").removeAttr("checked");
                }
            });
            check_sel_opt();
        });
     }
     
     exports.delete_rows = function()
     {
         $("body").on("click","#delete_rows",function(e){
                e.preventDefault();
                var checkval = [];
                $('.actionsid:checkbox:checked').each(function(i){
                    checkval[i] = $(this).val();
                });
                if(checkval.length==0)
                {
                    return false;
                }
                var _this = this;
                bootbox.confirm("Are you sure to delete the selected data?",function(result){
                        if (result == true)
                        {
                            var tab_d = $(_this).attr('tab_d');

                            var url = HTTP_PATH+"commonclass/deleterows/";
                            var dataString="tab_d="+tab_d+"&checkval="+checkval;
                            exports.doAjax({'url':url,'dataString':dataString,'elem':"#delete_rows",
                            ajaxSuccess:function(data){
                                $('.actionsid:checkbox:checked').each(function(i){
                                    $(this).closest('tr').remove();
                                });
                                $("#checkallitem").iCheck('uncheck');
                                exports.doAjax({'url':window.location,'dataString':'',load:true,'container':'.right-side'});
                            }});
                            
                        }
                });
                
        });
     }
     
     exports.menu_loads = function()
     {
         $("body").on("click",".left-side .sidebar-menu a, .add_btn_ajax, .dataTables_paginate a",function(e){
             e.preventDefault();
             var path = $(this).attr('href');
             if(path == "#")
                 return false;
             exports.doAjax({'url':path,dataString:'',container:'.right-side',load:true,
                ajaxSuccess:function(data){
                    if($(document).width() < 1000)
                    {
                        $(".row-offcanvas-left").removeClass("active relative");
                    }
            }});
         });
         
         exports.doAjax({url:HTTP_PATH+THIS_CONTENT,dataString:"",container:".right-side",load:true});
     }
     exports.left_col = function()
     {
        exports.doAjax({'url':HTTP_PATH+'commonclass/left_col',dataString:'',container:'.left-side',
            ajaxSuccess:function(data){

            }
        });
     }
     exports.h_user_settings = function()
     {
        exports.doAjax({'url':HTTP_PATH+'commonclass/h_user_settings',dataString:'',container:'.h-navbar-dis',
            ajaxSuccess:function(data){

            }
        });
     }
     
     exports.block_body= function()
     {
         var h = $(window).height();
         $("body").addClass("body_relative").css("height",h);
         
         $(".body_loader").fadeIn().css("height",h);
     }
     exports.unblock_body= function()
     {
         $("body").removeClass("body_relative").css("height","auto");
         $(".body_loader").fadeOut();
     }
     
     exports.inArray = function(e,t)
     {
         var n=t.length;
         for(var c=0;c<n;c++)
         {
             if(e == t[c])
             {
                 return 1
             }
         }
         return 0;
     }
    exports.init = function(_$) {
        window.commonfn = init(_$ || $);
    };

    return exports;
        
   
}(window.jQuery));

function app_init()
{
    
}

function check_sel_opt()
{
    if($("input.actionsid:checked").length == 0)
    {
        $("#delete_rows").attr("disabled",'disabled');
    }
    else
    {
        $("#delete_rows").removeAttr("disabled");
    }
}

SelectSearch = function(options)
{
    // Default Values
    this.defaults = {
            
    },  this.defaults  = $.extend(this.defaults, options);
    var _this = this;
    this.MultiAjaxAutoComplete = function(element,not_element, url,placeholder,minimumInputLength,callback) {
        if(typeof minimumInputLength === "undefined")
            minimumInputLength = 1;
        if(typeof placeholder === "undefined")
            placeholder = "search";
        $(element).not(not_element).select2({
            allowClear: true,
            placeholder: placeholder,
            minimumInputLength: minimumInputLength,
            ajax: {
                url: url,
                dataType: 'json',
                type : 'POST',
                data: function(term, page) {
                    return {
                        q: term,
                        page_limit: 10
                    };
                },
                results: function(data, page) {
                    return {
                        results: data.list
                    };
                }
            },
            formatResult: _this.formatResult,
            formatSelection: _this.formatSelection
        });
        callback();
    };

    this.formatResult = function (data) {
        return '<div>' + data.title + '</div>';
    };

    this.formatSelection = function(data) {
        return data.title;
    };
}

var searchObj = new SelectSearch();