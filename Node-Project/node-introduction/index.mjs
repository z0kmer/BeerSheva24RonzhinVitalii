
// import { readFile, writeFile } from 'node:fs/promises';
// import logger from './logger.mjs';
// const data = await readFile('./file.txt','utf-16le')
// console.log(data)
// writeFile('./file.txt', ["kuku", "kukureku", "שלום"].join('\n'), 'utf-16le')
// logger.info("function finished");


import FilterLogMessages from './filterMessages.mjs';
import DistributionMessageLevels from './logDistribution.mjs';
import logger from './logger.mjs';
const filter1 = new FilterLogMessages(logger,["hello"], "debug");
const filter2 = new FilterLogMessages(logger,["hello", "kuku"], "debug");
const distribution = new DistributionMessageLevels(logger);
logger.log("debug", "Hello world");
logger.log("debug", "kuku");
logger.log('info', "kukureku");
console.log(distribution.getDistribution());
console.log(filter2.getMessage());


