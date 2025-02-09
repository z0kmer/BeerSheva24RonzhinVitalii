const protocolObj= {
    "/addPoint": addPoint,
    "/getPoint": getPoint
}
function createResponse(code, response) {
 return {code, response};
}
function addPoint (data, server, service) {
     
 try {
     service.addPoint(JSON.parse(data));
     server.emit("response", createResponse(204, ""));
 } catch (error) {
     server.emit("response", createResponse(400, error.message))
 }
}
function getPoint (data, server, service) {
     try {
         const point = service.getPoint(data);
         server.emit("response", createResponse(200,JSON.stringify(point)));
     } catch(error) {
         server.emit("response", createResponse(404, error.message))
     }
}
 export default protocolObj;