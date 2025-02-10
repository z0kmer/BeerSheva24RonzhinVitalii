import express from 'express';
const courses = {
    J101: {id: "J101", "name": "Front-End", lecturer: "Vasya", hours: 200},
    J102: {id: "J102", "name": "JAVA", lecturer: "Vasya", hours: 300},
    J103: {id: "J103", "name": "back-End", lecturer: "Olya", hours: 350},
    J104: {id: "J104", "name": "Node", lecturer: "Olya", hours: 150},
    J105: {id: "J105", "name": "AWS", lecturer: "Vova", hours: 200},
    J106: {id: "J106", "name": "C++", lecturer: "Vova", hours: 500}
};
const app = express();
const port = process.env.PORT || 3500;
app.use(express.json());
app.post('/api/v1/courses', (req, res) => {
    const id = request.body.id
    courses[id] = req.body();
    console.log(courses);
    res.status(201).send(courses[id]);
});
app.get('/api/v1/courses/:id', (req, res) => {
    res.send(courses[id]);
});
app.delete('/api/v1/courses/:id', (req, res) => {
    delete courses[req.params.id];
    console.log(courses);
    res.send("deleted");
});
app.put('/api/v1/courses/:id', (req, res) => {
    const id = req.params.id;
    courses[id] = {...courses[id], ...req.body};
    res.send(courses[id]);
});
app.get('/api/v1/courses', (req, res) => {
    const {lecturer, hours, name} = req.query;
    res.send(Object.values(courses).filter(c =>
         (lecturer ? c.lecturer == lecturer : true)
         && (hours ? c.hours == hours : true)
          && (name ? c.name == name : true)))
});
app.listen(port, () => console.log(`server is listening on port ${port}`));