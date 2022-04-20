# Starting endpoints and GET all as list
```
 http://localhost:8080/student-management-system/api/v1/students

 http://localhost:8080/student-management-system/api/v1/teachers

 http://localhost:8080/student-management-system/api/v1/subjects
```

## Add student
```
POST
Endpoint: http://localhost:8080/student-management-system/api/v1/students
JSON:
{
	"firstName": "John",
	"lastName": "Doe",
	"email": "johndoe@examplefactory.com",
    "phoneNumber": "0737549484"
}
Phone number is optional
```
## Update student
```
PUT
Endpoint: http://localhost:8080/student-management-system/api/v1/students
JSON:
{
	"firstName": "Example",
	"lastName" : "Exampleson",
	"email": "exampleman@examplifier.com",
	"phoneNumber": "07244904043",
	"id": 12
}
Phone number is optional
```
## Remove student
```
DELETE
Endpoint: http://localhost:8080/student-management-system/api/v1/students/ID
Replace ID with the id of the student you want to remove.
```

## Find student by ID
```
GET
Endpoint: http://localhost:8080/student-management-system/api/v1/students/ID
Replace ID with the id of the student you want to find.
```
## Get all students by last name
```
GET
Endpoint: http://localhost:8080/student-management-system/api/v1/students/find
Example: http://localhost:8080/student-management-system/api/v1/students/find?lastname=Exampleson
replace exampleson with an existing last name in the database.
```
## Add student to a subject
```
PATCH
Endpoint: http://localhost:8080/student-management-system/api/v1/students/addsubjects/
Example: http://localhost:8080/student-management-system/api/v1/students/addsubjects/11?title=Svenska
11 is the ID of the student and title=Svenska is the title of an existing subject in the database.
```
## Add teacher
```
POST
Endpoint: http://localhost:8080/student-management-system/api/v1/teachers
JSON:
{
	"firstName": "Teacher",
	"lastName": "Teacherson"
}
```
## Update teacher
```
PUT
Endpoint: http://localhost:8080/student-management-system/api/v1/teachers
JSON:
{
	"firstName": "Teacher",
	"lastName": "Teacherson",
    "id": ID
}
replace ID with the ID number of the existing teacher
```
## Remove teacher
```
DELETE
Endpoint: http://localhost:8080/student-management-system/api/v1/teachers/ID
Replace ID with the id of the teacher you want to remove.
```
## Find teacher by ID
```
GET
Endpoint: http://localhost:8080/student-management-system/api/v1/teachers/ID
Replace ID with the id of the teacher you want to find.
```
## Add teacher to a subject
```
PATCH
Endpoint: http://localhost:8080/student-management-system/api/v1/teachers/addsubjects/
Example: http://localhost:8080/student-management-system/api/v1/teachers/addsubjects/11?title=Svenska
11 is the ID of the student and title=Svenska is the title of an existing subject in the database.
```

## Add subject 
```
POST
Endpoint: http://localhost:8080/student-management-system/api/v1/subjects/
JSON: 
{
	"title": "Example Science"
} 
```

## Remove subject
```
DELTE
Endpoint: http://localhost:8080/student-management-system/api/v1/subjects/ID
Replace ID with the id number of an existing subject in the database. 
```

## Find subject by ID
```
GET
Endpoint: http://localhost:8080/student-management-system/api/v1/subjects/ID
Replace ID with the id number of an existing subject in the database.
```
