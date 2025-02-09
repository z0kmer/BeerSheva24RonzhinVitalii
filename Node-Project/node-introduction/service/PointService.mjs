export default class PointService {
    #points
    constructor() {
        this.#points = {}
    }
    addPoint({id,x}) {
        if (this.#points[id]) {
            throw Error('point exists')
        }
        this.#points[id] = {id, x}
        console.log(this.#points)
    }
    getPoint(id) {
        if (!this.#points[id]) {
            throw Error("point doesn't exist")
        }
        return this.#points[id];
    }
}