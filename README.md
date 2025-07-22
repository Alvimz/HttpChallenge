
# HTTP Listener in Java

HttpServer listen a port and uses annotations to create customs contexts!
Can be flexible to add more links and behaviors!





## Paths POSTMAN
- GET
```bash
http://localhost:8989/customer/all
```
Returns all the customes we've in cache!

- POST

```bash
http://localhost:8989/customer/100
```
``` bash
{
  "name": "Alvimzin"
}
```
Creates a new Customer named "Alvimzin".

- POST
```bash
http://localhost:8989/customer/create
```
``` bash
{
  "name": "Alvimzin"
}
```
Does the same behavior than previous!



## UML


![App Screenshot](https://i.imgur.com/OEenvMF.png)


## TODO

#### Fixing:

- Body with/without names writed wrong. ("nam": "Alvimzin")
- Don't accept requests which don't need body! (GET, for example)
- Use dynamics links to supply methods



