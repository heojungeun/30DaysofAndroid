const express = require('express')
const path = require('path')
var app = express()
var http = require('http').Server(app)
var io = require('socket.io')(http)

app.set('port',(process.env.PORT || 5000))

app.use(express.static(__dirname + '/public'))

app.set('views',__dirname+'/views')
app.set('view engine','ejs')

console.log('outside io')

io.on('connection', function(socket){

    // 로그인했을 때
    console.log('User Connection - log in')

    // 클라이언트에서 'connect user'로 request 보내면
    // 함께 보내온 데이터를 가지고 로그를 찍거나, 다시 emit 을 통해 클라이언트로 응답을 보냄
    socket.on('connect user',function(user){
        console.log("Connected user")
        socket.join(user['roomName'])
        console.log("roomName : ", user['roomName'])
        console.log("state : ", socket.adapter.rooms)
        io.emit('connect user', user) // 'connect user'로 client 에 데이터를 담아서 response 한다.
    });

    // 로그인했을 때 -- end

    // 메세지 입력했을때 서버로그 처리 -- start
    socket.on('chat message', function(msg){
        console.log("Message" + msg['message'])
        console.log("sender id : ", msg['roomName'])
        io.to(msg['roomName']).emit('chat message',msg)
    });
});

// 처음에 서버 연결했을 때, 연결 포트 번호 알림
http.listen(app.get('port'),function(){
    console.log('Node app is running on port', app.get('port'));
});