/**
 * 登录
 */
function login() {
    // 构造请求参数
    var reqJson = {};
    reqJson.username = $("#username").val();
    reqJson.password = $("#password").val();
    reqJson.client_id = $("#client_id").val();
    reqJson.response_type = $("#response_type").val();
    reqJson.redirect_uri = $("#redirect_uri").val();
    reqJson.state = $("#state").val();
    reqJson.scope = $("#scope").val();
    reqJson.flag = $("#flag").val();

    $.ajax({
        async: false,
        type: "POST",
        url: pageContext.request.contextPath + "/authorize",
        data: reqJson,
        dataType: 'json',
        success: function (data) {
        }
    });
}
