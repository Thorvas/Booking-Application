const http = require('http');
const jwt = require('jsonwebtoken');
const socketIo = require('socket.io');
const {Kafka, Partitioners} = require('kafkajs');

const kafka = new Kafka({
    brokers: ['localhost:9092'],
});

const server = http.createServer();
const io = socketIo(server, {
    path: '/ws',
    cors: false,
});

const JWT_SECRET = 'SECRETKEY64ASDASDSADASDSADSADASDASDSADASSDASDASDASDASDASDAD';

const userConnections = new Map();

const createProducer = async () => {
    const producer = kafka.producer({
        createPartitioner: Partitioners.LegacyPartitioner
    });

    await producer.connect();

    return producer;
}

const createConsumer = async (topic, groupId) => {
    const consumer = kafka.consumer({
        groupId: groupId,
        allowAutoTopicCreation: true
    });

    await consumer.connect();
    await consumer.subscribe({
        topic: topic,
        fromBeginning: true
    });

    return consumer;
}

const saveNotification = async (producer, message) => {
    const propagatedMessage = {
        userId: message.userId,
        message: message.message,
        timestamp: new Date()
    }

    const sentMessage = JSON.stringify(propagatedMessage);

    await producer.send({
        topic: 'notification_saved',
        messages: [
            {
                value: sentMessage
            }
        ]
    })
}

const consumeAndSaveInDatabase = async (consumer, producer) => {
    await consumer.run({
        eachMessage: async ({topic, partition, message}) => {

            const jsonMessage = JSON.parse(JSON.parse(message.value.toString()));
            const userId = jsonMessage.userId;
            await saveNotification(producer, jsonMessage);

            const socket = userConnections.get(parseInt(userId));
            if (socket) {
                socket.emit('notification',
                    jsonMessage.message
                );
            } else {
                console.log(`No such connection related to userId ${jsonMessage.userId}`);
            }
        }
    })
}

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

    userConnections.set(parseInt(socket.userId), socket);

    console.log(`User connected. ${socket.userId}`);

});

server.listen(8083, async () => {
    const kafkaProducer = await createProducer();
    const kafkaConsumer = await createConsumer('notification_created', 'notification_listeners');
    await consumeAndSaveInDatabase(kafkaConsumer, kafkaProducer);
});
