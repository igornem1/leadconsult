package eu.leadconsult.interview.task;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import eu.leadconsult.interview.task.data.model.Course;
import eu.leadconsult.interview.task.data.model.CourseType;
import eu.leadconsult.interview.task.data.model.Group;
import eu.leadconsult.interview.task.data.model.Student;
import eu.leadconsult.interview.task.data.model.Teacher;
import eu.leadconsult.interview.task.data.model.base.BaseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class TaskApplicationIntegrationTest {
    private static final String GROUPS_API = "//groups";
    private static final String TEACHERS_API = "//teachers";
    private static final String COURSES_API = "//courses";
    private static final String STUDENTS_API = "//students";
    private static final HttpHeaders HEADERS_URI_LIST = requestHeaderUriList();
    private static Teacher[] teachers;
    private static Course[] mainCourses;
    private static Course[] secondaryCourses;
    private static Group[] groups;
    private static Student[] students;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(0)
    public void createEntities() throws Exception {
        teachers = new Teacher[] { createTeacher("Teacher 1", 35), createTeacher("Teacher 2", 46) };
        mainCourses = new Course[] { createCourse("Course 1 main", CourseType.Main), createCourse("Course 2 main", CourseType.Main) };
        secondaryCourses = new Course[] { createCourse("Course 1 secondary", CourseType.Secondary), createCourse("Course 2 secondary", CourseType.Secondary) };
        groups = new Group[] { createGroup("Group 1"), createGroup("Group 2") };
        students = new Student[4];
        for (int i = 0; i < students.length; i++) {
            students[i] = createStudent("Student " + i, i % 2 == 0 ? 22 : 23);
        }

        // Assign courses to teachers
        for (int i = 0; i < teachers.length; i++) {
            assignManyToOne(TEACHERS_API, COURSES_API, "teacher", teachers[i], new Course[] { mainCourses[i], secondaryCourses[i] });
        }

        // Assign students to groups, first half to first group, second half to second
        // group
        assignManyToOne(GROUPS_API, STUDENTS_API, "group", groups[0], Arrays.copyOfRange(students, 0, students.length / 2));
        assignManyToOne(GROUPS_API, STUDENTS_API, "group", groups[1], Arrays.copyOfRange(students, students.length / 2, students.length));

        // Assign courses to students, even student ids to first courses, odd to second
        // courses
        for (int i = 0; i < students.length; i++) {
            int idx = students[i].getId() % 2 == 0 ? 0 : 1;
            assignManyToMany(STUDENTS_API, COURSES_API, "courses", students[i],
                    new Course[] { mainCourses[idx], secondaryCourses[idx] });
        }
    }

    @Test
    @Order(1)
    public void coursesOfTeachersCorrect() throws JSONException {
        List<Course> coursesOfTeacher1 = getMany(TEACHERS_API, teachers[0].getId(), "courses", Course.class);
        List<Course> coursesOfTeacher2 = getMany(TEACHERS_API, teachers[1].getId(), "courses", Course.class);

        Assertions.assertTrue(equals(coursesOfTeacher1.get(0), mainCourses[0]));
        Assertions.assertTrue(equals(coursesOfTeacher1.get(1), secondaryCourses[0]));

        Assertions.assertTrue(equals(coursesOfTeacher2.get(0), mainCourses[1]));
        Assertions.assertTrue(equals(coursesOfTeacher2.get(1), secondaryCourses[1]));
    }

    @Test
    @Order(2)
    public void studentsOfCourseCorrect() throws JSONException {
        List<Student> studentsOfCourse1 = getMany(COURSES_API, mainCourses[0].getId(), "students", Student.class);

        Assertions.assertTrue(equals(studentsOfCourse1.get(0), students[1]));
        Assertions.assertTrue(equals(studentsOfCourse1.get(1), students[3]));
    }

    private boolean equals(Student student, Student student2) {
        return student.getId().equals(student2.getId()) &&
                student.getName().equals(student2.getName()) &&
                student.getAge() == student2.getAge();
    }

    @Test
    @Order(3)
    public void studentsOfGroupCorrect() throws JSONException {
        List<Student> studentsOfGroup1 = getMany(GROUPS_API, groups[0].getId(), "students", Student.class);

        Assertions.assertTrue(equals(studentsOfGroup1.get(0), students[0]));
        Assertions.assertTrue(equals(studentsOfGroup1.get(1), students[1]));

    }

    private boolean equals(Course course, Course course2) {
        return course.getId().equals(course2.getId()) &&
                course.getName().equals(course2.getName());
    }

    private <T extends BaseEntity> List<T> getMany(String ownerApi, Long ownerId, String manyPropertyName, Class<T> clazz)
            throws JSONException {
        String jsonResponse = restTemplate.getForObject(ownerApi + "/" + ownerId + "/" + manyPropertyName, String.class);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray(manyPropertyName);
        return JsonUtils.toList(jsonArray, clazz);
    }

    private static HttpEntity<String> toHttpEntity(String apiUrl, BaseEntity entity) {
        HttpEntity<String> teacherHttpEntity1 = new HttpEntity<>(apiUrl + "/" + entity.getId(), HEADERS_URI_LIST);
        return teacherHttpEntity1;
    }

    private static HttpEntity<String> toHttpEntity(String apiUrl, BaseEntity[] entities) {
        StringBuffer bufMany = new StringBuffer();
        int i = 0;
        for (BaseEntity entity : entities) {
            bufMany.append(apiUrl).append("/").append(entity.getId());
            if (++i < entities.length) {
                bufMany.append("\n");
            }
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(bufMany.toString(), HEADERS_URI_LIST);
        return httpEntity;
    }

    private static HttpHeaders requestHeaderUriList() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");
        return requestHeaders;
    }

    private void assignManyToMany(String ownerApi, String manyApi, String manyPropertName, BaseEntity owner, BaseEntity[] many) {
        HttpEntity<String> httpEntityMany = toHttpEntity(manyApi, many);
        restTemplate.exchange(ownerApi + "/" + owner.getId() + "/" + manyPropertName, HttpMethod.PUT, httpEntityMany, String.class);
    }

    private void assignManyToOne(String ownerApi, String manyApi, String mappedBy, BaseEntity owner, BaseEntity... many) {
        HttpEntity<String> httpEntity = toHttpEntity(ownerApi, owner);
        for (BaseEntity entity : many) {
            restTemplate.exchange(manyApi + "/" + entity.getId() + "/" + mappedBy, HttpMethod.PUT, httpEntity, String.class);
        }
    }

    private Course createCourse(String courseName, CourseType type) {
        return restTemplate.postForObject(COURSES_API, new Course(courseName, type), Course.class);
    }

    private Teacher createTeacher(String teacherName, int age) {
        return restTemplate.postForObject(TEACHERS_API, new Teacher(teacherName, age), Teacher.class);
    }

    private Group createGroup(String groupName) {
        return restTemplate.postForObject(GROUPS_API, new Group(groupName), Group.class);
    }

    private Student createStudent(String studentName, int age) {
        return restTemplate.postForObject(STUDENTS_API, new Student(studentName, age), Student.class);
    }

}
