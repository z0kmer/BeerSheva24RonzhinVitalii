export class LogDistribution {
    constructor(logger) {
        this.messageCounts = {};
        
        logger.on('message', ({ level }) => {
            if (this.messageCounts[level]) {
                this.messageCounts[level] += 1;
            } else {
                this.messageCounts[level] = 1;
            }
        });
    }

    getLogDistribution() {
        return this.messageCounts;
    }
}
