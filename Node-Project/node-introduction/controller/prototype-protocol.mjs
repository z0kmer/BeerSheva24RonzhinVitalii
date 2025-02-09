export default class PrototypeProtocol  {
    #service
    constructor(service, server) {
        this.#service = service;
        server.on('/addPoint', (data, res) => {
                try {
                    this.#service.addPoint(JSON.parse(data));
                    res.statuseCode=204;
                    res.end();
                } catch (error) {
                    res.statuseCode=400;
                    res.end();
                }
        })
        server.on('/getPoint', (data, res) => {
            try {
                const point = this.#service.getPoint(data);
                res.write(JSON.stringify(point));
                res.end()
            } catch (error) {
                res.statuseCode=404
                res.end();
            };
        })
    }
}