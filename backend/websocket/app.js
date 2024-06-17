const http = require('http');
const jwt = require('jsonwebtoken');
const socketIo = require('socket.io');
const {Kafka} = require('kafkajs');

const kafka = new Kafka({
    clientId: 'my-app',
    brokers: ['localhost:9092'],
});

const producer = kafka.producer();

producer.connect();
await producer.send( {
    topic: 'test-topic',
    messages: [
        {
            value: 'Hello Kafka!'
        },
    ],
});

await producer.disconnect();

const consumer = kafka.consumer({
    groupId: 'notification_listeners'
});

await consumer.connect();
await consumer.subscribe(
    {
        topic: 'test-topic',
        fromBeginning: true
    }
);

await consumer.run({
    eachMessage: async ({topic, partition, message}) => {
        console.log("AA");
    }
})

const server = http.createServer();
const io = socketIo(server, {
    path: '/ws',
    cors: false
});

const JWT_SECRET = 'SECRETKEY64ASDASDSADASDSADSADASDASDSADASSDASDASDASDASDASDAD';

const userConnections = new Map();

io.use((socket, next) => {
    const header = socket.handshake.headers['authorization'];
    if (!header) {
        return next(new Error(`Authorization header missing`));
    }

    const token = header.split(' ')[1];
    if (!token) {
        return next(new Error(`Token missing`));
    }

    try {
        const decoded = jwt.verify(token, JWT_SECRET);
        socket.userId = decoded.sub;
        next();
    } catch (err) {
        next(new Error(`Invalid token`));
    }
})

io.on('connection', (socket) => {

    console.log(`User connected. ${socket.userId}`);

});

server.listen(8083, () => {
    console.log(`Server listening on port 8083`);
});