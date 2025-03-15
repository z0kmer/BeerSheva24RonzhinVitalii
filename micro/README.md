# Tasks definition

## Write synchronous micro-service range-data-provider
### Introducing record Range inside API project
### Update template.yaml file
### Getting data from AWS RDS service with predefined Postgres DB (DB will contain three tables)
patients - data about patients with group_id, notification group_id<br>
groups - data about groups with different minimal and maximal pulse values
notification_groups -data about notification destinations (id, topic name, sms, email)
## Update abnormal-values-recognizer 
### Introduce HttpClient of RangeProviderClient interface
### introduce functionality of sending logs to CloudWatch with some log group and streams


