
import fs from 'node:fs';
// const data = fs.readFileSync('./index.mjs');
// logger.info(data);
fs.writeFileSync('./file.txt', ["kuku", "kukureku", "Hello World!"].join('\n'))
