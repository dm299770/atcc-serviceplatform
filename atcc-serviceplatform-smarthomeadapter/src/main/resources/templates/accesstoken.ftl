<#assign base=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <base id="base" href="${base}">
    <title>OAuth2 Server</title>

</head>
<body>
<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
            </ul>
        </nav>
        <h3 class="text-muted">OAuth2 Server</h3>
    </div>


    <div class="row marketing">
        <input type="text" value="${base}" />
        <input type="text" value="${request.contextPath}" />
        <div class="col-lg-10">
            <form class="form-horizontal" method="post" action="/ACV-UA/accessToken">
                <div class="form-group">
                    <label for="client_id" class="col-sm-4 control-label">应用id</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="client_id" name="client_id"
                               value="oauth_clientid_aligenie">
                    </div>
                </div>
                <div class="form-group">
                    <label for="client_secret" class="col-sm-4 control-label">应用secret</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="client_secret" name="client_secret"
                               value="oauth_clientid_aligenie">
                    </div>
                </div>
                <div class="form-group">
                    <label for="grant_type" class="col-sm-4 control-label">grant_type</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="grant_type" name="grant_type"
                               placeholder="grant_type" value="authorization_code">
                    </div>
                </div>
                <div class="form-group">
                    <label for="code" class="col-sm-4 control-label">授权码</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="code" name="code" placeholder="授权码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="redirect_uri" class="col-sm-4 control-label">回调地址</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="redirect_uri" name="redirect_uri" value="">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-8">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row marketing">
        <div class="col-lg-10">
            <form class="form-horizontal" method="post" action="/accessToken">
                <div class="form-group">
                    <label for="client_id" class="col-sm-4 control-label">应用id</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="client_id" name="client_id"
                               value="oauth_clientid_Aligen">
                    </div>
                </div>
                <div class="form-group">
                    <label for="client_secret" class="col-sm-4 control-label">应用secret</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="client_secret" name="client_secret"
                               value="oauth_clientid_Aligen">
                    </div>
                </div>
                <div class="form-group">
                    <label for="grant_type" class="col-sm-4 control-label">grant_type</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="grant_type" name="grant_type"
                               placeholder="grant_type" value="client_credentials">
                    </div>
                </div>

                <div class="form-group">
                    <label for="app_id" class="col-sm-4 control-label">app_id</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="app_id" name="app_id" value="map_application">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-8">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row marketing">
        <div class="col-lg-10">
            <form class="form-horizontal" method="post" action="/accessToken">
                <div class="form-group">
                    <label for="client_id" class="col-sm-4 control-label">应用id</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="client_id" name="client_id"
                               value="oauth_clientid_Aligen">
                    </div>
                </div>
                <div class="form-group">
                    <label for="client_secret" class="col-sm-4 control-label">应用secret</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="client_secret" name="client_secret"
                               value="oauth_clientid_Aligen">
                    </div>
                </div>
                <div class="form-group">
                    <label for="grant_type" class="col-sm-4 control-label">grant_type</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="grant_type" name="grant_type"
                               placeholder="grant_type" value="refresh_token">
                    </div>
                </div>
                <div class="form-group">
                    <label for="refresh_token" class="col-sm-4 control-label">刷新码</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="refresh_token" name="refresh_token"
                               placeholder="刷新码">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-8">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
                <p style="text-align:center;"> 地图应用:map_application &nbsp;&nbsp;&nbsp;&nbsp;
                    语音和语义应用:voice_application</p>
            </form>
        </div>
    </div>
</div>
</html>