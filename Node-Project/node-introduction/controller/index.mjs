import http from 'node:http';
import PointService from "../service/PointService.mjs";
import PrototypeProtocol from "./prototype-protocol.mjs";

const server = http.createServer();
const service = new PointService()
const protocol = new PrototypeProtocol(service, server);

server.listen(3500);
server.on('request', async (req, res) => {
   let data = '';
   for await (const part of req) {
      data += part;
   }
   server.emit(req.url, data, res); 
  
})