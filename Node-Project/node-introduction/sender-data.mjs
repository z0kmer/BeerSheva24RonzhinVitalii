import { Stream } from 'node:stream';

const stream = new Stream.Readable();
stream.push("Hello ");
stream.push("World");
stream.push(null);
export default stream;