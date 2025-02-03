
//import logger from './logger.mjs';
import fs from 'node:fs';
const data = fs.readFileSync('./index.mjs', 'utf8', (err, data) => {
    if(err) {
        loggers.error(err);
    } else {
        console.log(data);
    }
});
console.log(data);
fs.writeFileSync('./file.txt', ["kuku", "kukureku", "Hello World!"]
    .join('\n'), 'utf-16le', () => loggers.debug('file saved in utf-16le format'));
