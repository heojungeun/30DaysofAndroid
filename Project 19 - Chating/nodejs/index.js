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

    // when log in -- start
    console.log('User Connection')

    socket.on('connect user',function(user){
        console.log("Connected user")
        socket.join(user['roomName'])
        console.log("roomName : ", user['roomName'])
        console.log("state : ", socket.adapter.rooms)
        io.emit('connect user', user)
    });
    // when log in -- end

    // when input msg -- start
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