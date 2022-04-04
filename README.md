# Student-Management-JAX-RS

Starting endpoint: http://localhost:8080/student-management-system/api/v1/students

POST:
Add new student: http://localhost:8080/student-management-system/api/v1/students
Howto: Json format, REQUIRED: 
{
	"firstName": "Example",
	"lastName": "Exampleson",
	"email": "Example@ExampleMaster.com"
}
--- OPTIONAL PHONE NUMBER (AT LEAST 5 DIGITS) ---:
  "phoneNumber": "00000"

GET: 
Find all students http://localhost:8080/student-management-system/api/v1/students
Find student by id: http://localhost:8080/student-management-system/api/v1/students/{id here}
Find students by last name: http://localhost:8080/student-management-system/api/v1/students/find Query param: lastname {lastname}

PUT: 

Update student: http://localhost:8080/student-management-system/api/v1/students
Howto: Json format, REQUIRED: 
{
	"firstName": "Example",
	"lastName": "Exampleson",
	"email": "Example@ExampleMaster.com"
}
--- OPTIONAL PHONE NUMBER (AT LEAST 5 DIGITS) ---:
  "phoneNumber": "00000"

PATCH:
Update/Add phone number: http://localhost:8080/student-management-system/api/v1/students/{id here}
Howto: Json format
{
 "phoneNumber": "{at least 5 digit number here}"
}

DELETE:
Remove student by Id: http://localhost:8080/student-management-system/api/v1/students/{id here}
