var socket;
var connected = false;
var date = new Date();

/* 打开websocket连接*/
$(document).ready(initUser());
//window.onload=openSocket();

/* 初始化用户 */
function initUser(){
    if ($.cookie('username') == "" || $.cookie('username') == "undefined"){
        var randomStr = date.getMinutes() + "" + parseInt(Math.random() * 10000) + "" + date.getMilliseconds();
        $.cookie('username', "游客"+randomStr, { path: '/' });
    }
}

/* 切换左侧无关内容，显示聊天框 */
function chat(){
    // toggle() 如果在显示就隐藏，如果在隐藏就显示
	$("#left-title").toggle();
	$("#breaf-introd").toggle();
	$("#social-list").toggle();
	$("#menu-list").toggle();
	$("#left-bottom").toggle();
	$("#left-chat").toggle();
	if (connected == false){
		openSocket();
		connected = true;
	} else{
	    socket.close();
	    connected = false;
	}
}

/* 打开socket连接 */
function openSocket() {
    console.log("当前用户是:" + $.cookie('username'));
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else{
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        var socketUrl = "http://localhost:1234/im/" + $.cookie('username');
        socketUrl = socketUrl.replace("https","ws").replace("http","ws");
        console.log("准备连接：" + socketUrl)
        socket = new WebSocket(socketUrl);
        // 打开事件
        socket.onopen = function() {
            console.log("websocket已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        // 获得消息事件
        socket.onmessage = function(msg) {
            console.log("收到了来自服务器的消息：" + msg.data);
           // 发现消息进入    开始处理前端触发逻辑
           addMessage(msg.data);
        };
        // 关闭事件
        socket.onclose = function() {
            console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function() {
            console.log("websocket发生了错误");
        }
    }
}

/* 给服务器发送信息，服务器会转发给所有人 */
function sendMessage() {
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else {
        // 如果文本框为空不做处理
        var message = $("#chat-input").val();
        if (message == null || message == ""){
            return;
        }
        console.log("要发送的内容是：" + message);
        socket.send(message);
        // 清空文本框
    	$("#chat-input").val("");
    }
}

/* 在页面上添加收到的消息 */
function addMessage(msgText){
    var con = $("#chat-box").html();
    var temp = con + "<div class=\"message-box\"><p class=\"message-cont\">" + msgText + "</p></div>";
    $("#chat-box").html(temp);
}

/* 评论 */
function commentSend(){
    // 获取当前博客id
    var currentUrl = window.location.href;
    var index = currentUrl.lastIndexOf("/") + 1;
    var blogId = currentUrl.substr(index);
    // 获取评论内容
    var comment = $("#comment-edit-box").val();
    var reqStr = {blogId:blogId, content:comment};
    console.log("评论：" + comment);
	$.post("/comment/send",
	    {"data":JSON.stringify(reqStr)},
  		function(resp){
    		console.log(resp);
    		if ("ok" == resp){
    		    console.log("返回的是ok");
    		    var con = $("#comment-list").html();
                var temp = con + "<div class=\"comment-item\"><p class=\"comment-userName\">"+ $.cookie('username') +"</p>" +
                "<p class=\"comment-content\">" + comment + "</p> </div>";
                $("#comment-list").html(temp);
    		}
    		$("#comment-edit-box").val("");
  		}
  	);
}

/* 打开登录框 */
function openLogin(){
	$("#loginWindow").show();
}

/* 关闭登录框 */
function closeLoginWindow(){
	$("#loginWindow").hide();
}

/* 登录 */
function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	if (username == "" || username == undefined){
		alert("用户名不能为空");
	} else if(password == "" || password == undefined){
		alert("密码不能为空");
	}
	var reqStr = {username:username, password:password};
	console.log("发送的报文是：" + JSON.stringify(reqStr))
	$.post("/user/login",
 		{"data":JSON.stringify(reqStr)},
		function(resp){
    		console.log(resp);
    		if ("登录成功" == resp){
    			console.log("返回的是登录成功");
    		    alert("登录成功");
    		    $("#loginWindow").hide();
   				// .val()用来修改文本框中的内容，.html用来改变元素内部的值  .append()内部追加  .perpare()向内部预置内容
      			// .after() 向备选元素之后追加  .before()在被选元素之前添加
    		    $("#user").html(username);
                $.cookie('username', username, { path: '/' });
    		}
  		}
  	);
}