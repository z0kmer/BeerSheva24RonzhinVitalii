
// import { readFile, writeFile } from 'node:fs/promises';
// import logger from './logger.mjs';
// const data = await readFile('./file.txt','utf-16le')
// console.log(data)
// writeFile('./file.txt', ["kuku", "kukureku", "שלום"].join('\n'), 'utf-16le')
// logger.info("function finished");

import { FilterMessages } from './filterMessages.mjs';
import { LogDistribution } from './logDistribution.mjs';
import logger from './logger.mjs';

const logDistribution = new LogDistribution(logger);
const filterMessages = new FilterMessages(logger);

logger.log('info', 'Hello World!');
logger.log('debug', 'Need more attention!');
logger.log('info', 'Another RND-message');

setTimeout(() => {
    console.log(logDistribution.getLogDistribution());

    console.log(filterMessages.getMessagesWithLevelAndWords('info', ['hello']));
    console.log(filterMessages.getMessagesWithLevelAndWords('debug', ['hello']));
}, 400);
