const coursesPathes = {
    POST: {
        authentication: "jwt",
        autorization: req => req.role === "ADMIN"
    },
    PUT: {
        authentication: "jwt",
        autorization: req => req.role === "ADMIN"
    },
    GET: {
        authentication: "jwt",
        autorization: req => true
    },
    DELETE: {
        authentication: "jwt",
        autorization: req => req.role === "ADMIN"
    }
}