import express from 'express';
import { auth, authenticate } from '../middleware/auth.js';
import { validator } from '../middleware/validation.js';
import accountingService from '../service/AccountsService.js';
import { schemaAccount, schemaGetAccount } from '../validation/schemas.js';
const accountsRoute = express.Router();
accountsRoute.use(authenticate());
accountsRoute.post("/admin", validator(schemaAccount), (req, res) => {
    accountingService.addAdminAccount(req.body);
    res.status(201).send("account added")
})
accountsRoute.post("/user", validator(schemaAccount), (req, res) => {
    accountingService.addUserAccount(req.body);
    res.status(201).send("account added")
})
accountsRoute.put("/", validator(schemaAccount), (req, res) => {
    accountingService.updateAccount(req.body);
    res.send("account updated")
})
accountsRoute.get("/", auth, validator(schemaGetAccount),(req, res) => {
   const account =  accountingService.getAccount(req.body.email); 
   res.send(account);
});
accountsRoute.post("/login",  (req, res) => {
    const token = accountingService.login(req.body);
    res.send(token);
})
accountsRoute.delete("/",validator(schemaGetAccount), (req,res) => {
    accountingService.delete(req.body.email);
    res.send("deleted")
} )
export default accountsRoute;