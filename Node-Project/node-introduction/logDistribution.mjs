export default class DistributionMessageLevels {
    #distributionObject
    constructor(logger) {
        logger.on('message', ({level}) => this.#messageProcessing(level) );
        this.#distributionObject = {};
    }
    #messageProcessing(level){
        if (!this.#distributionObject[level]) {
            this.#distributionObject[level] = 0;
        }
        this.#distributionObject[level]++;
    }
    getDistribution() {
        return this.#distributionObject;
    }
}