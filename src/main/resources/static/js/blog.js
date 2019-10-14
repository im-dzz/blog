var socket;
var date = new Date();
var userId = date.getMinutes() + "" + parseInt(Math.random() * 10000) + "" + date.getMilliseconds();;

/* 打开websocket连接*/
$(document).ready(openSocket());
//window.onload=openSocket();

/* 切换左侧无关内容，显示聊天框 */
function chat(){
	$("#left-title").toggle();
	$("#breaf-introd").toggle();
	$("#social-list").toggle();
	$("#menu-list").toggle();
	$("#left-bottom").toggle();
	$("#left-chat").toggle();
}

function openSocket() {
    console.log("当前用户的id是:" + userId);
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else{
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        var socketUrl = "http://localhost:1234/im/"+userId;
        socketUrl = socketUrl.replace("https","ws").replace("http","ws");
        console.log(socketUrl)
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = function() {
            console.log("websocket已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        socket.onmessage = function(msg) {
            console.log("收到了来自服务器的消息：" + msg.data);
           //发现消息进入    开始处理前端触发逻辑
           addMessage(msg.data);
        };
        //关闭事件
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
        console.log("要发送的内容是：" + $("#chat-input").val());
        socket.send($("#chat-input").val());
    	$("#chat-input").val("");
    }
}

function addMessage(msgText){
    var con = $("#chat-box").html();
    var temp = con + "<div class=\"message-box\"><p class=\"message-cont\">" + msgText + "</p></div>";
    $("#chat-box").html(temp);
}

/* 评论 */
function commentSend(){
    var comment = $("#comment-edit-box").val();
    var reqStr = {userId:userId, blogId:"111", content:comment};
    console.log(comment);
	$.post("/comment/send",
	    {"data":JSON.stringify(reqStr)},
  		function(resp){
    		console.log(resp);
    		if ("ok" == resp){
    		    console.log("返回的是ok");
    		    var con = $("#comment-list").html();
    		    console.log(con);
                var temp = con + "<div class=\"comment-item\"><p class=\"comment-userName\">"+ userId +"</p>" +
                "<p class=\"comment-content\">" + comment + "</p> </div>";
                console.log(temp);
                $("#comment-list").html(temp);
                console.log("返回处理了");
    		}
    		$("#comment-edit-box").val("");
  		}
  	);
}