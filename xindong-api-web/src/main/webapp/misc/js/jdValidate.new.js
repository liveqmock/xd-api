function verc() {
    $("#JD_Verification1").click();
}
var validateRegExp = {
    intege:"^-?[1-9]\\d*$", //整数
    intege1:"^[1-9]\\d*$", //正整数
    intege2:"^-[1-9]\\d*$", //负整数
    num:"^([+-]?)\\d*\\.?\\d+$", //数字
    num1:"^[1-9]\\d*|0$", //正数（正整数 + 0）
    num2:"^-[1-9]\\d*|0$", //负数（负整数 + 0）
    ascii:"^[\\x00-\\xFF]+$", //仅ACSII字符
    chinese:"^[\\u4e00-\\u9fa5]+$", //仅中文
    date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$", //日期
    email:"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", //邮件
    letter:"^[A-Za-z]+$", //字母
    letter_l:"^[a-z]+$", //小写字母
    letter_u:"^[A-Z]+$", //大写字母
    mobile:"^0?(13|15|18|14)[0-9]{9}$", //手机
    notempty:"^\\S+$", //非空
    password:"^.*[A-Za-z0-9\\w_-]+.*$", //密码
    fullNumber:"^[0-9]+$", //数字
    tel:"^[0-9\-()（）]{7,18}$", //电话号码的函数(包括验证国内区号,国际区号,分机号)
    url:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$", //url
    username:"^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$" //用户名
};
//主函数
(function ($) {
    $.fn.jdValidate = function (option, callback, def) {
        var ele = this;
        var id = ele.attr("id");
        var type = ele.attr("type");
        var rel = ele.attr("rel");
        var _onFocus = $("#" + id + validateSettings.onFocus.container);
        var _succeed = $("#" + id + validateSettings.succeed.container);
        var _isNull = $("#" + id + validateSettings.isNull.container);
        var _error = $("#" + id + validateSettings.error.container);
        if (def == true) {
            var str = ele.val();
            var tag = ele.attr("sta");
            if (str == "" || str == "-1") {
                validateSettings.isNull.run({
                    prompts:option,
                    element:ele,
                    isNullEle:_isNull,
                    succeedEle:_succeed
                }, option.isNull);
            } else if (tag == 1 || tag == 2) {
                return;
            } else {
                callback({
                    prompts:option,
                    element:ele,
                    value:str,
                    errorEle:_error,
                    succeedEle:_succeed
                });
            }
        } else {
            if (typeof def == "string") {
                ele.val(def);
            }
            if (type == "checkbox" || type == "radio") {
                if (ele.attr("checked") == true) {
                    ele.attr("sta", validateSettings.succeed.state);
                }
            }
            switch (type) {
                case "text":
                case "password":
                    ele.bind("focus", function () {
                        var str = ele.val();
                        if (str == def) {
                            ele.val("");
                        }
                        if (id == "pwd") {
                            $("#pwdstrength").hide();
                        }
                        validateSettings.onFocus.run({
                            prompts:option,
                            element:ele,
                            value:str,
                            onFocusEle:_onFocus,
                            succeedEle:_succeed
                        }, option.onFocus);
                    })
                        .bind("blur", function () {
                            var str = ele.val();
                            if (str == "") {
                                ele.val(def);
                            }
                            if (validateRules.isNull(str)) {
                                validateSettings.isNull.run({
                                    prompts:option,
                                    element:ele,
                                    value:str,
                                    isNullEle:_isNull,
                                    succeedEle:_succeed
                                }, "");
                            } else {
                                callback({
                                    prompts:option,
                                    element:ele,
                                    value:str,
                                    errorEle:_error,
                                    isNullEle:_isNull,
                                    succeedEle:_succeed
                                });
                            }
                        });
                    break;
                default:
                    if (rel && rel == "select") {
                        ele.bind("change", function () {
                            var str = ele.val();
                            callback({
                                prompts:option,
                                element:ele,
                                value:str,
                                errorEle:_error,
                                isNullEle:_isNull,
                                succeedEle:_succeed
                            });
                        })
                    } else {
                        ele.bind("click", function () {
                            callback({
                                prompts:option,
                                element:ele,
                                errorEle:_error,
                                isNullEle:_isNull,
                                succeedEle:_succeed
                            });
                        })
                    }
                    break;
            }
        }
    }
})(jQuery);

//配置
var validateSettings = {
    onFocus:{
        state:null,
        container:"_error",
        style:"focus",
        run:function (option, str) {
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style2).addClass(validateSettings.INPUT_style1);
            }
            option.onFocusEle.removeClass().addClass(validateSettings.onFocus.style).html(str);
        }
    },
    isNull:{
        state:0,
        container:"_error",
        style:"null",
        run:function (option, str) {
            option.element.attr("sta", 0);
            if (!validateRules.checkType(option.element)) {
                if (str != "") {
                    option.element.removeClass(validateSettings.INPUT_style1).addClass(validateSettings.INPUT_style2);
                } else {
                    option.element.removeClass(validateSettings.INPUT_style2).removeClass(validateSettings.INPUT_style1);
                }
            }
            option.succeedEle.removeClass(validateSettings.succeed.style);
            option.isNullEle.removeClass().addClass(validateSettings.isNull.style).html(str);
        }
    },
    error:{
        state:1,
        container:"_error",
        style:"error",
        run:function (option, str) {
            option.element.attr("sta", 1);
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style1).addClass(validateSettings.INPUT_style2);
            }
            option.succeedEle.removeClass(validateSettings.succeed.style);
            option.errorEle.removeClass().addClass(validateSettings.error.style).html(str);
        }
    },
    succeed:{
        state:2,
        container:"_succeed",
        style:"succeed",
        run:function (option) {
            option.element.attr("sta", 2);
            option.errorEle.empty();
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style1).removeClass(validateSettings.INPUT_style2);
            }
            if (option.element.attr("id") == "schoolinput" && $("#schoolid").val() == "") {
                return;
            }
            option.succeedEle.addClass(validateSettings.succeed.style);
        }
    },
    INPUT_style1:"highlight1",
    INPUT_style2:"highlight2"
};

//验证规则
var validateRules = {
    isNull:function (str) {
        return (str == "" || typeof str != "string");
    },
    betweenLength:function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    isUid:function (str) {
        return new RegExp(validateRegExp.username).test(str);
    },
    fullNumberName:function (str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    isEmail:function (str) {
        return new RegExp(validateRegExp.email).test(str);
    },
    isTel:function (str) {
        return new RegExp(validateRegExp.tel).test(str);
    },
    isMobile:function (str) {
        return new RegExp(validateRegExp.mobile).test(str);
    },
    checkType:function (element) {
        return (element.attr("type") == "checkbox" || element.attr("type") == "radio" || element.attr("rel") == "select");
    }
};
//验证文本
var validatePrompt = {
    username:{
        onFocus:"4-20位字符，可由中文、英文、数字及“_”、“-”组成",
        succeed:"",
        isNull:"请输入用户名",
        error:{
            beUsed:"该用户名已被使用，请使用其它用户名注册，如果您是&quot;{1}&quot;，请<a href='https://passport.360buy.com/new/login.aspx' class='flk13'>登录</a>",
            badLength:"用户名长度只能在4-20位字符之间",
            badFormat:"用户名只能由中文、英文、数字及“_”、“-”组成",
            fullNumberName:"用户名不能全为数字"
        }
    },
    pwd:{
        onFocus:"6-20位字符，可使用字母、数字或符号的组合",
        succeed:"",
        isNull:"请输入密码",
        error:{
            badLength:"密码长度只能在6-20位字符之间",
            badFormat:"密码只能由英文、数字及标点符号组成",
            simplePwd:"密码太弱，有被盗风险，建议设置多种字符组成的复杂密码"
        }
    },
    authcode:{
        onFocus:"请输入图片中的字符，不区分大小写",
        succeed:"",
        isNull:"请输入验证码",
        error:"验证码错误"
    },
    empty:{
        onFocus:"",
        succeed:"",
        isNull:"",
        error:""
    }
};

var nameold, emailold, authcodeold;
var namestate = false, emailstate = false, authcodestate = false;
//回调函数
var validateFunction = {
    authcode:function (option) {
        validateSettings.succeed.run(option);
        authcodestate = true;
    },
    FORM_submit:function (elements) {
        var bool = true;
        for (var i = 0; i < elements.length; i++) {
            if ($(elements[i]).attr("sta") == 2) {
                bool = true;
            } else {
                bool = false;
                break;
            }
        }
        return bool;
    }
};
function strTrim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}