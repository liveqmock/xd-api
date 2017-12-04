$.extend(validatePrompt, {
    username:{
        onFocus:"",
        succeed:"",
        isNull:"请输入用户名/邮箱/已验证手机",
        error:"不存在此用户名"
    }
});
$.extend(validateFunction, {
    username:function (option) {
        validateSettings.succeed.run(option);
    },
    pwd:function (option) {
        validateSettings.succeed.run(option);
    },

    FORM_validate:function () {
        $("#loginname").jdValidate(validatePrompt.username, validateFunction.username, true);
        $("#loginpwd").jdValidate(validatePrompt.pwd, validateFunction.pwd, true);
        return validateFunction.FORM_submit(["#loginname", "#loginpwd"]);
    }
});
setTimeout(function () {
    if (!$("#loginname").val()) {
        $("#loginname").get(0).focus();
    }
}, 0);

$("#loginname").jdValidate(validatePrompt.username, validateFunction.username);
$("#loginpwd").jdValidate(validatePrompt.empty, validateFunction.pwd);
$("#loginname,#loginpwd").bind('keyup', function (event) {
    if (event.keyCode == 13) {
        $("#loginsubmit").click();
    }
});
$("#loginsubmit").click(function () {
    var loginUrl = "/oauth2/loginService";
    var flag = loginNameOk() && validateFunction.FORM_validate();
    if (flag) {
        $(this).attr({ "disabled":"disabled" });
        $.ajax({
            type:"POST",
            url:loginUrl + location.search + "&r=" + Math.random(),
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            data:$("#formlogin").serialize(),
            dataType:"json",
            error:function () {
                $("#loginpwd").attr({ "class":"text highlight2" });
                $("#loginpwd_error").html("网络超时，请稍后再试").show().attr({ "class":"error" });
                $("#loginsubmit").removeAttr("disabled");
                $(this).removeAttr("disabled");
            },
            success:function (result) {
                if (result) {
                    if (result.success == true) {
                    	window.location.href = result.url;
                        return;
                    }
                    $("#loginsubmit").removeAttr("disabled");
                    $(this).removeAttr("disabled");
                    
                    $("#loginname").attr({ "class":"text highlight2" });
                    $("#loginname_error").html(result.result).show().attr({ "class":"error" });
                }else{
                	$("#loginsubmit").removeAttr("disabled");
                	$(this).removeAttr("disabled");
                }
            }
        });
    }
});

function loginNameOk() {
    var loginName = $("#loginname").val();
    if (validateRules.isNull(loginName) || loginName == '用户名/邮箱/已验证手机') {
        $("#loginname").attr({ "class":"text highlight2" });
        $("#loginname_error").html("请输入用户名/邮箱/已验证手机").show().attr({ "class":"error" });
        return false;
    }

    return true;
}
