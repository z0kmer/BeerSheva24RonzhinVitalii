export class FilterMessages {
    constructor(logger) {
        this.messages = [];

        logger.on('message', ({ level, message }) => {
            this.messages.push({ level, message });
        });
    }

    getMessagesWithLevelAndWords(level, words) {
        words = words.map(word => word.toLowerCase());
        return this.messages
            .filter(msg => msg.level === level)
            .filter(msg => words.every(word => msg.message.toLowerCase().includes(word)));
    }
}
