 /* 
 here should be actual service code
 */
 class PointService {
   #points
   constructor() {
       this.#points = {}
   }
   addPoint({id,x}) {
       if (this.#points[id]) {
           throw Error(`point with id ${id} already exists`)
       }
       this.#points[id] = {id, x}
       
   }
   getPoint(id) {
       if (!this.#points[id]) {
           throw Error(`point with id ${id} doesn't exist`)
       }
       return this.#points[id];
   }
}
const service = new PointService();
export default service;